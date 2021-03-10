package com.jedreck.testdfs.service;

import com.github.tobato.fastdfs.domain.fdfs.FileInfo;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.jedreck.testdfs.config.MyFastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;

@Service
@Slf4j
public class DownloadService {

    @Autowired
    private MyFastFileStorageClient storageClient;

    public void download(String filePath, OutputStream outputStream) throws IOException {
        try {
            StorePath storePath = StorePath.parseFromUrl(filePath);
            FileInfo fileInfo = storageClient.queryFileInfo(storePath.getGroup(), storePath.getPath());
            long fileSize = fileInfo.getFileSize();
            log.info("文件总大小: " + fileSize + " B");
            long slice = Math.floorDiv(fileSize, 100);
            long left = fileSize - slice * 99;

            for (int i = 0; i < 100; i++) {

                if (i != 99) {
                    storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), i * slice, slice, ins -> {
                        byte[] result = IOUtils.toByteArray(ins);
                        outputStream.write(result);
                        return result;
                    });


                } else {
                    storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), 99 * slice, left, ins -> {
                        byte[] result = IOUtils.toByteArray(ins);
                        outputStream.write(result);
                        return result;
                    });
                }
                // 进度
                log.info("下载量：" + (i + 1));
            }
        } finally {
            outputStream.flush();
            outputStream.close();
        }
    }
}
