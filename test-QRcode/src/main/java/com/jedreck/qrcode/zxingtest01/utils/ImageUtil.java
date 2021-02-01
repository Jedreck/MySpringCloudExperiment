package com.jedreck.qrcode.zxingtest01.utils;

import com.jedreck.qrcode.zxingtest01.constants.MediaType;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class ImageUtil {
    private ImageUtil() {
    }

    /**
     * 压缩图片储存大小
     *
     * @param image    原始图片
     * @param maxSize  最大图片储存大小，KB
     * @param accuracy 精度，KB
     * @param type     图片类型，后缀
     * @return 压缩后的图片, 生成的图片只能是jpg的
     */
    public static BufferedImage resizeByte(@NotNull BufferedImage image, int maxSize, int accuracy, String type) throws IOException {
        BufferedImage tmp = image;
        double maxR = Math.max(tmp.getWidth(), tmp.getHeight());
        double maxL = 0;
        double min = maxR;
        while (true) {
            // 验证大小
            long length = getStorageSizeJPG(tmp);
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            ImageIO.write(tmp, type, out);
//            long length = out.toByteArray().length;
            length /= 1024;
            // 符合要求 返回
            if (length >= (maxSize - accuracy) && length <= maxSize) {
                break;
            }
            if (length > maxSize) {
                // 如果大了 取一半
                maxR = min;
            } else {
                // bytes.length < (maxSize - accuracy)
                // 如果小了 取min和maxR中间
                maxL = min;
            }
            if (maxL == maxR) {
                break;
            }
            min = (maxL + maxR) / 2.0;
            tmp = resizeMax(image, (int) min);
        }
        return tmp;
    }

    /**
     * 缩放图片
     *
     * @param image 原始图片
     * @param min   设置最短边的长，将图片最短边缩放到这个值，另一边的值等比缩放
     * @return 缩放后的图片
     */
    public static BufferedImage resizeMin(@NotNull BufferedImage image, int min) throws IOException {
        int w;
        int h;
        if (image.getWidth() == image.getHeight()) {
            w = min;
            h = min;
        } else if (image.getWidth() > image.getHeight()) {
            h = min;
            w = (int) (image.getWidth() * ((min + 0.0) / image.getHeight()));
        } else {
            w = min;
            h = (int) (image.getHeight() * ((min + 0.0) / image.getWidth()));
        }
        return resize(image, w, h, 1);
    }

    /**
     * 缩放图片
     *
     * @param image 原始图片
     * @param max   设置最长边的长，将图片最长边缩放到这个值，另一边的值等比缩放
     * @return 缩放后的图片
     */
    public static BufferedImage resizeMax(@NotNull BufferedImage image, int max) throws IOException {
        int w;
        int h;
        if (image.getWidth() == image.getHeight()) {
            w = max;
            h = max;
        } else if (image.getWidth() > image.getHeight()) {
            w = max;
            h = (int) (image.getHeight() * ((max + 0.0) / image.getWidth()));
        } else {
            h = max;
            w = (int) (image.getWidth() * ((max + 0.0) / image.getHeight()));
        }
        return resize(image, w, h, 1);
    }

    /**
     * 缩放图片
     *
     * @param image 原始图片
     * @param w     宽
     * @param h     高
     * @return 缩放后的图片
     */
    public static BufferedImage resize(@NotNull BufferedImage image, int w, int h, double quality) throws IOException {
        return Thumbnails.of(image).size(w, h).outputQuality(quality).asBufferedImage();
    }

    /**
     * 获取图片存储大小
     *
     * @param image 图片
     * @return 大小，bytes
     */
    public static long getStorageSizeJPG(BufferedImage image) throws IOException {
        File tmpFile = File.createTempFile("tmp_file", MediaType.ImageJpg.getExt());
        FileImageOutputStream out = new FileImageOutputStream(tmpFile);
        getStreamJPG(image, null, out);
        long length = out.length();
        out.close();
//        tmpFile.delete();
        Files.delete(Paths.get(tmpFile.getPath()));
        return length;
    }

    /**
     * 微小失真存储图片
     *
     * @param image 图片
     * @param out   输出流
     */
    public static <T extends ImageOutputStream> void getStreamJPG(BufferedImage image, Float accuracy, T out)
            throws IOException {
        Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName(MediaType.ImageJpg.getExt());
        if (iter.hasNext()) {
            ImageWriter writer = iter.next();

            ImageWriteParam param = writer.getDefaultWriteParam();
            // 设置可否压缩
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            // 最高质量 1.0f
            // 最接近真实 0.955f
            param.setCompressionQuality((accuracy == null || accuracy > 1 || accuracy <= 0) ? 0.955f : accuracy);
            writer.setOutput(out);

            writer.write(null, new IIOImage(image, null, null), param);

            writer.dispose();
        }
    }

    /**
     * 高保真保存图片
     *
     * @param image 图片
     * @param path  文件路径
     */
    public static void savePicJPG(BufferedImage image, String path) throws IOException {
        File file = new File(path);
        FileImageOutputStream out = new FileImageOutputStream(file);
        getStreamJPG(image, null, out);
        out.close();
    }
}
