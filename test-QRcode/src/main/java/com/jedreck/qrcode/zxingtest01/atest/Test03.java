package com.jedreck.qrcode.zxingtest01.atest;

import com.jedreck.qrcode.zxingtest01.constants.MediaType;
import com.jedreck.qrcode.zxingtest01.utils.ImageLoadUtil;
import com.jedreck.qrcode.zxingtest01.utils.ImageUtil;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Test03 {
    public static final String P = "/tmp/bbb.png";

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
}
