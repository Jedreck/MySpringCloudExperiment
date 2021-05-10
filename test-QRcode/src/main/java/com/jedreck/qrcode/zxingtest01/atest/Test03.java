package com.jedreck.qrcode.zxingtest01.atest;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.imaging.png.PngMetadataReader;
import com.drew.imaging.png.PngProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifImageDirectory;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.jedreck.qrcode.zxingtest01.constants.MediaType;
import com.jedreck.qrcode.zxingtest01.utils.ImageLoadUtil;
import com.jedreck.qrcode.zxingtest01.utils.ImageRotateUtil;
import com.jedreck.qrcode.zxingtest01.utils.ImageUtil;
import com.jedreck.qrcode.zxingtest01.wrapper.QrCodeDeWrapper;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.resizers.configurations.Dithering;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class Test03 {
    public static final String P = "/tmp/bbb.png";

    @Test
    public void t5() throws IOException {
        int i = 1;
        for (; i <= 5; i++) {
            String f = "D:/Desktop/2" + i + ".jpg";
            FileInputStream fis = new FileInputStream(f);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            BufferedImage rotateImg = ImageRotateUtil.getRotateImg(bos.toByteArray(), null);
            Thumbnails.of(rotateImg).scale(1).toFile(new File("D:/Desktop/3" + i + ".jpg"));
        }
    }

    @Test
    public void t4() throws IOException, PngProcessingException, JpegProcessingException, MetadataException {
        //原始图片
//        String pt = "D:/Desktop/1.png";
        String pt = "D:/Desktop/333.jpg";
        InputStream inputStream = Files.newInputStream(Paths.get(pt));

        // 获取图片EXIF信息
        long startTime = System.currentTimeMillis();
        BufferedImage imgRow = ImageIO.read(inputStream);
        File file = new File(pt);
        Metadata metadata = JpegMetadataReader.readMetadata(file);
        ExifIFD0Directory firstDirectoryOfType = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
        int anInt = firstDirectoryOfType.getInt(ExifIFD0Directory.TAG_ORIENTATION);

        int angel = 90;

        int src_width = imgRow.getWidth();
        int src_height = imgRow.getHeight(null);

        int swidth = src_width;
        int sheight = src_height;

        if (angel == 90 || angel == 270) {
            swidth = src_height;
            sheight = src_width;
        }

        // 旋转图片
        Rectangle rectDes = new Rectangle(new Dimension(swidth, sheight));
        BufferedImage imgRotate = new BufferedImage(rectDes.width, rectDes.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = imgRotate.createGraphics();
        g2.translate((rectDes.width - src_width) / 2, (rectDes.height - src_height) / 2);
        g2.rotate(Math.toRadians(angel), src_width / 2.0, src_height / 2.0);
        g2.drawImage(imgRow, null, null);

        log.info("use: {} ms", System.currentTimeMillis() - startTime);
        Thumbnails.of(imgRow).size(500, 500).toFile(new File("D:/Desktop/11.png"));
        BufferedImage imgResize = ImageLoadUtil.getImageByPath("D:/Desktop/11.png");
        log.debug("debug");
    }

    @Test
    public void t3() throws IOException, FormatException, ChecksumException, NotFoundException {
        String decode = QrCodeDeWrapper.decode("D:/Desktop/999.png");
        System.out.println(decode);
    }

    @Test
    public void t1() throws IOException {
        BufferedImage imageByPath = ImageLoadUtil.getImageByPath("D:/Desktop/999.png");
        BufferedImage jpg = ImageUtil.resizeByte(imageByPath, 2000, 500, "jpg");
//        BufferedImage jpg = Thumbnails.of(imageByPath)
//                .size(imageByPath.getWidth(), imageByPath.getHeight())
//                .outputQuality(.1)
//                .asBufferedImage();
//        long png = ImageUtil.getStorageSize(imageByPath, "png");
        ImageUtil.savePicJPG(jpg, "D:/Desktop/9991.jpg");


        ImageIO.write(jpg, MediaType.ImagePng.getExt(), new File("D:/Desktop/9991.png"));
    }

    @Test
    public void t2() throws Exception {
        String decode2 = QrCodeDeWrapper.decode("https://");
        System.out.println(decode2);
    }
}
