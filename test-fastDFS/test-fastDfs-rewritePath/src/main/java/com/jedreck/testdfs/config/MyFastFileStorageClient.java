package com.jedreck.testdfs.config;

import com.github.tobato.fastdfs.FdfsClientConstants;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorageNode;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.domain.proto.storage.StorageSetMetadataCommand;
import com.github.tobato.fastdfs.domain.proto.storage.StorageUploadFileCommand;
import com.github.tobato.fastdfs.domain.proto.storage.StorageUploadSlaveFileCommand;
import com.github.tobato.fastdfs.domain.proto.storage.enums.StorageMetadataSetType;
import com.github.tobato.fastdfs.domain.upload.FastFile;
import com.github.tobato.fastdfs.domain.upload.FastFile.Builder;
import com.github.tobato.fastdfs.domain.upload.FastImageFile;
import com.github.tobato.fastdfs.domain.upload.ThumbImage;
import com.github.tobato.fastdfs.exception.FdfsUnsupportImageTypeException;
import com.github.tobato.fastdfs.exception.FdfsUploadImageException;
import com.github.tobato.fastdfs.service.DefaultFastFileStorageClient;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
public class MyFastFileStorageClient extends DefaultFastFileStorageClient {
    private static final List<String> SUPPORT_IMAGE_LIST;

    static {
        SUPPORT_IMAGE_LIST = Arrays.asList(FdfsClientConstants.SUPPORT_IMAGE_TYPE);
    }

    @Value("${public.thumb-image-suffix:_300x300}")
    private String thumbImageSuffix;
    @Autowired
    private ThumbImageConfig thumbImageConfig;

    @Override
    public StorePath uploadFile(InputStream inputStream, long fileSize, String fileExtName, Set<MetaData> metaDataSet) {
        FastFile fastFile;
        if (null == metaDataSet) {
            fastFile = (new Builder()).withFile(inputStream, fileSize, fileExtName).build();
        } else {
            fastFile = (new Builder()).withFile(inputStream, fileSize, fileExtName).withMetaData(metaDataSet).build();
        }

        return this.uploadFile(fastFile);
    }

    @Override
    public StorePath uploadImageAndCrtThumbImage(InputStream inputStream, long fileSize, String fileExtName, Set<MetaData> metaDataSet) {
        FastImageFile fastImageFile;
        if (null == metaDataSet) {
            fastImageFile = (new com.github.tobato.fastdfs.domain.upload.FastImageFile.Builder()).withFile(inputStream, fileSize, fileExtName).withThumbImage().build();
        } else {
            fastImageFile = (new com.github.tobato.fastdfs.domain.upload.FastImageFile.Builder()).withFile(inputStream, fileSize, fileExtName).withMetaData(metaDataSet).withThumbImage().build();
        }

        return this.uploadImage(fastImageFile);
    }

    @Override
    public StorePath uploadFile(FastFile fastFile) {
        Validate.notNull(fastFile.getInputStream(), "上传文件流不能为空", new Object[0]);
        Validate.notBlank(fastFile.getFileExtName(), "文件扩展名不能为空", new Object[0]);
        StorageNode client = this.getStorageNode(fastFile.getGroupName());
        return this.uploadFileAndMetaData(client, fastFile.getInputStream(), fastFile.getFileSize(), fastFile.getFileExtName(), fastFile.getMetaDataSet());
    }

    @Override
    public StorePath uploadImage(FastImageFile fastImageFile) {
        String fileExtName = fastImageFile.getFileExtName();
        Validate.notNull(fastImageFile.getInputStream(), "上传文件流不能为空", new Object[0]);
        Validate.notBlank(fileExtName, "文件扩展名不能为空", new Object[0]);
        if (!this.isSupportImage(fileExtName)) {
            throw new FdfsUnsupportImageTypeException("不支持的图片格式" + fileExtName);
        } else {
            StorageNode client = this.getStorageNode(fastImageFile.getGroupName());
            byte[] bytes = this.inputStreamToByte(fastImageFile.getInputStream());
            StorePath path = this.uploadFileAndMetaData(client, new ByteArrayInputStream(bytes), fastImageFile.getFileSize(), fileExtName, fastImageFile.getMetaDataSet());
            if (null != fastImageFile.getThumbImage()) {
                this.uploadThumbImage(client, new ByteArrayInputStream(bytes), path.getPath(), fastImageFile);
            }

            bytes = null;
            return path;
        }
    }

    private StorageNode getStorageNode(String groupName) {
        return null == groupName ? this.trackerClient.getStoreStorage() : this.trackerClient.getStoreStorage(groupName);
    }

    private byte[] inputStreamToByte(InputStream inputStream) {
        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException var3) {
            LOGGER.error("image inputStream to byte error", var3);
            throw new FdfsUploadImageException("upload ThumbImage error", var3.getCause());
        }
    }

    private boolean hasMetaData(Set<MetaData> metaDataSet) {
        return null != metaDataSet && !metaDataSet.isEmpty();
    }

    private boolean isSupportImage(String fileExtName) {
        return SUPPORT_IMAGE_LIST.contains(fileExtName.toUpperCase());
    }

    private StorePath uploadFileAndMetaData(StorageNode client, InputStream inputStream, long fileSize, String fileExtName, Set<MetaData> metaDataSet) {
        StorageUploadFileCommand command = new StorageUploadFileCommand(client.getStoreIndex(), inputStream, fileExtName, fileSize, false);
        StorePath path = this.fdfsConnectionManager.executeFdfsCmd(client.getInetSocketAddress(), command);
        if (this.hasMetaData(metaDataSet)) {
            StorageSetMetadataCommand setMDCommand = new StorageSetMetadataCommand(path.getGroup(), path.getPath(), metaDataSet, StorageMetadataSetType.STORAGE_SET_METADATA_FLAG_OVERWRITE);
            this.fdfsConnectionManager.executeFdfsCmd(client.getInetSocketAddress(), setMDCommand);
        }

        return path;
    }

    private void uploadThumbImage(StorageNode client, InputStream inputStream, String masterFilename, FastImageFile fastImageFile) {
        ByteArrayInputStream thumbImageStream = null;
        ThumbImage thumbImage = fastImageFile.getThumbImage();

        try {
            thumbImageStream = this.generateThumbImageStream(inputStream, thumbImage);
            long fileSize = thumbImageStream.available();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("获取到缩略图前缀{}", thumbImageSuffix);
            }

            StorageUploadSlaveFileCommand command = new StorageUploadSlaveFileCommand(thumbImageStream, fileSize, masterFilename, thumbImageSuffix, fastImageFile.getFileExtName());
            this.fdfsConnectionManager.executeFdfsCmd(client.getInetSocketAddress(), command);
        } catch (IOException var14) {
            LOGGER.error("upload ThumbImage error", var14);
            throw new FdfsUploadImageException("upload ThumbImage error", var14.getCause());
        } finally {
            IOUtils.closeQuietly(thumbImageStream);
        }

    }

    private ByteArrayInputStream generateThumbImageStream(InputStream inputStream, ThumbImage thumbImage) throws IOException {
        if (thumbImage.isDefaultConfig()) {
            thumbImage.setDefaultSize(this.thumbImageConfig.getWidth(), this.thumbImageConfig.getHeight());
            return this.generateThumbImageByDefault(inputStream);
        } else {
            return thumbImage.getPercent() != 0.0D ? this.generateThumbImageByPercent(inputStream, thumbImage) : this.generateThumbImageBySize(inputStream, thumbImage);
        }
    }

    private ByteArrayInputStream generateThumbImageByPercent(InputStream inputStream, ThumbImage thumbImage) throws IOException {
        LOGGER.debug("根据传入比例生成缩略图");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Thumbnails.of(new InputStream[]{inputStream}).scale(thumbImage.getPercent()).imageType(2).toOutputStream(out);
        return new ByteArrayInputStream(out.toByteArray());
    }

    private ByteArrayInputStream generateThumbImageBySize(InputStream inputStream, ThumbImage thumbImage) throws IOException {
        LOGGER.debug("根据传入尺寸生成缩略图");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Thumbnails.of(new InputStream[]{inputStream}).size(thumbImage.getWidth(), thumbImage.getHeight()).imageType(2).toOutputStream(out);
        return new ByteArrayInputStream(out.toByteArray());
    }

    private ByteArrayInputStream generateThumbImageByDefault(InputStream inputStream) throws IOException {
        LOGGER.debug("根据默认配置生成缩略图");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Thumbnails.of(new InputStream[]{inputStream}).size(this.thumbImageConfig.getWidth(), this.thumbImageConfig.getHeight()).imageType(2).toOutputStream(out);
        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public void deleteFile(String filePath) {
        StorePath storePath = StorePath.parseFromUrl(filePath);
        super.deleteFile(storePath.getGroup(), storePath.getPath());
    }
}
