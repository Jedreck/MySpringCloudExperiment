package com.jedreck.qrcode.zxingtest01.utils;

import javax.validation.constraints.NotNull;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtil {

    /**
     * 缩放图片
     *
     * @param image 原始图片
     * @param min   设置最短边的长，将图片最短边缩放到这个值，另一边的值等比缩放
     * @return
     */
    public static BufferedImage resizeMin(@NotNull BufferedImage image, int min) {
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
        return resize(image, w, h);
    }

    /**
     * 缩放图片
     *
     * @param image 原始图片
     * @param max   设置最长边的长，将图片最长边缩放到这个值，另一边的值等比缩放
     * @return 缩放后的图片
     */
    public static BufferedImage resizeMax(@NotNull BufferedImage image, int max) {
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
        return resize(image, w, h);
    }

    /**
     * 缩放图片
     *
     * @param image 原始图片
     * @param w     宽
     * @param h     高
     * @return 缩放后的图片
     */
    public static BufferedImage resize(@NotNull BufferedImage image, int w, int h) {
        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = GraphicUtil.getG2d(bufferedImage);
        g2d.drawImage(image.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, null);
        return bufferedImage;
    }
}
