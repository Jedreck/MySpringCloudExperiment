package com.jedreck.qrcode.zxingtest01.atest;

import com.jedreck.qrcode.zxingtest01.utils.ImageLoadUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Test02 {
    public static final String P = "/tmp/bbb.png";

    public static void main(String[] args) throws Exception {
        test01(ImageLoadUtil.getImageByPath("sky.jpg"), 5, 5, Color.GRAY, .5f);
    }

    private static void test01(BufferedImage srcImg, int x, int y, Color color, float alpha) throws Exception {
        // 在新建一个图像缓冲区，大小包括阴影部分
        BufferedImage bufImg = new BufferedImage(srcImg.getWidth() + x, srcImg.getHeight() + y, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufImg.createGraphics();
        // 创建一个支持有透明度的图像缓冲区
        bufImg = g.getDeviceConfiguration().createCompatibleImage(srcImg.getWidth() + x, srcImg.getHeight() + y, Transparency.TRANSLUCENT);
        g.dispose();
        // 画阴影部分
        g = bufImg.createGraphics();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.XOR, alpha));
        g.setColor(color);
        g.fillRect(x, y, srcImg.getWidth(), srcImg.getHeight());
        g.dispose();
        // 画源图像
        g = bufImg.createGraphics();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1));
        g.drawImage(srcImg, 0, 0, null);
        g.dispose();
        // 目标文件必须是PNG文件
        ImageIO.write(bufImg, "PNG", new File(P));
    }
}
