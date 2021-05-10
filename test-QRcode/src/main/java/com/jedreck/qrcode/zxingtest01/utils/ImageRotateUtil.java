package com.jedreck.qrcode.zxingtest01.utils;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.imaging.png.PngMetadataReader;
import com.drew.imaging.png.PngProcessingException;
import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.jedreck.qrcode.zxingtest01.constants.MediaType;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Slf4j
public class ImageRotateUtil {
    private ImageRotateUtil() {
    }

    /**
     * 获取角度
     *
     * @param imgByte       图片bytes
     * @param extensionName 扩展名
     * @return 图片旋转的角度，逆时针，只有 90,180,270
     */
    public static int rotateAngle(byte[] imgByte, String extensionName) throws JpegProcessingException, IOException, MetadataException, PngProcessingException {
        int angle = 0;
        Metadata metadata;
        if (extensionName == null) {
            MediaType extension = ImageExtensionUtil.getExtension(imgByte);
            extensionName = extension == null ? null : extension.getExt();
        }
        // 获取Metadata
        if (MediaType.ImageJpg.getExt().equalsIgnoreCase(extensionName) || "jpeg".equalsIgnoreCase(extensionName)) {
            metadata = JpegMetadataReader.readMetadata(new ByteArrayInputStream(imgByte));
        } else if (MediaType.ImagePng.getExt().equalsIgnoreCase(extensionName)) {
            metadata = PngMetadataReader.readMetadata(new ByteArrayInputStream(imgByte));
        } else {
            return angle;
        }

        // 获取旋转角度
        ExifIFD0Directory directoryOfType = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
        if (directoryOfType != null && directoryOfType.containsTag(ExifDirectoryBase.TAG_ORIENTATION)) {
            // Exif信息中方向　　
            int orientation = directoryOfType.getInt(ExifDirectoryBase.TAG_ORIENTATION);
            // 原图片的方向信息
            if (6 == orientation) {
                //6旋转90
                angle = 90;
            } else if (3 == orientation) {
                //3旋转180
                angle = 180;
            } else if (8 == orientation) {
                //8旋转270
                angle = 270;
            }
        }
        return angle;
    }

    /**
     * 旋转图片
     *
     * @param imgRow 图片
     * @param angle  角度，正数为顺时针，90°
     */
    public static BufferedImage rotateImg(@NotNull BufferedImage imgRow, int angle) {
        // 旋转图片
        int imgRowWidth = imgRow.getWidth();
        int imgRowHeight = imgRow.getHeight(null);

        int sWidth;
        int sHeight;

        if (angle == 90 || angle == 270) {
            sWidth = imgRowHeight;
            sHeight = imgRowWidth;
        } else {
            sWidth = imgRowWidth;
            sHeight = imgRowHeight;
        }
        Rectangle rectDes = new Rectangle(new Dimension(sWidth, sHeight));
        BufferedImage imgRotate = new BufferedImage(rectDes.width, rectDes.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = imgRotate.createGraphics();
        g2.translate((rectDes.width - imgRowWidth) / 2, (rectDes.height - imgRowHeight) / 2);
        g2.rotate(Math.toRadians(angle), imgRowWidth / 2.0, imgRowHeight / 2.0);
        g2.drawImage(imgRow, null, null);
        g2.dispose();

        return imgRotate;
    }

    /**
     * 获取旋转图片
     *
     * @param imgByte       图片bytes
     * @param extensionName 扩展名
     */
    public static BufferedImage getRotateImg(byte[] imgByte, String extensionName) {
        // 获取角度
        int angle;
        BufferedImage imgRow;
        try {
            imgRow = ImageIO.read(new ByteArrayInputStream(imgByte));
            angle = rotateAngle(imgByte, extensionName);
        } catch (JpegProcessingException | PngProcessingException e) {
            log.error("图片格式获取失败", e);
            throw new RuntimeException("图片格式获取失败");
        } catch (IOException e) {
            log.error("图片读取失败", e);
            throw new RuntimeException("图片读取失败");
        } catch (MetadataException e) {
            log.error("图片信息获取失败", e);
            throw new RuntimeException("图片信息获取失败");
        }
        return angle == 0 ? imgRow : rotateImg(imgRow, angle);
    }
}
