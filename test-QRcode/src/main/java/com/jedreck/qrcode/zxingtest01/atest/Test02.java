package com.jedreck.qrcode.zxingtest01.atest;

import com.jedreck.qrcode.zxingtest01.utils.ColorUtil;
import com.jedreck.qrcode.zxingtest01.utils.ImageLoadUtil;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Test02 {
    public static final String P = "/tmp/bbb.png";

    public static void main(String[] args) throws Exception {
        test01(ImageLoadUtil.getImageByPath("sky.jpg"), 5, 5, Color.GRAY, .5f);
    }

    /**
     * 草料第2个图形
     */
    @Test
    public void test03() throws IOException {
        int W = 1000;
        float s = (float) W / 6;
        float sw = s * 4;
        float sww = s * 2;
        BufferedImage img = new BufferedImage(W, W, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setColor(Color.BLACK);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fill(new RoundRectangle2D.Float(0, 0, W, W, 400, 400));
//        g2.dispose();
//
//        g2 = img.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setColor(ColorUtil.OPACITY);
        g2.fill(new RoundRectangle2D.Float(s, s, sw, sw, 300, 300));
//        g2.dispose();
//
//        g2 = img.createGraphics();
        g2.setColor(Color.BLACK);
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fill(new RoundRectangle2D.Float(sww, sww, sww, sww, 200, 200));
//        g2.dispose();
        // 目标文件必须是PNG文件
        ImageIO.write(img, "PNG", new File(P));

    }

    /**
     * 限制绘图区域
     *
     * @throws IOException
     */
    @Test
    public void test02() throws IOException {
        BufferedImage img = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        //先画个圆
        g2.setColor(Color.YELLOW);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fill(new RoundRectangle2D.Float(0, 0, 1000, 1000, 1000, 1000));
        g2.dispose();

        // 扣个圆
        g2 = img.createGraphics();
        g2.setColor(ColorUtil.OPACITY);
        g2.setComposite(AlphaComposite.Src);
        //限定绘画区域，超区无效
//        g2.clipRect(600,600,100,100);
        //同上语句功能
        g2.setClip(500, 500, 100, 100);
        g2.fillOval(400, 400, 500, 500);


        // 目标文件必须是PNG文件
        ImageIO.write(img, "PNG", new File(P));
    }

    /**
     * 测试 AlphaComposite.XX
     */
    @Test
    public void test04() throws IOException {
        BufferedImage img = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();

        g2.setColor(Color.YELLOW);
        //先画个矩形
        g2.fillRect(250, 0, 500, 1000);

        g2.setColor(Color.PINK);
        //设置覆盖规则
        // https://docs.oracle.com/javase/tutorial/2d/advanced/compositing.html

        //覆盖在原图片上
//        g2.setComposite(AlphaComposite.Src);
        //覆盖在原图片区域，原图片外的去除，alpha上层图片为透明度
//        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,.5f));
        //去除覆盖在原图片区域，上层图片只剩原图片区域，alpha上层图片为透明度
//        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN,.5f));
        //去除覆盖在原图片区域，上层图片只剩原非图片区域，alpha上层图片为透明度
//        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OUT,.5f));
        //去除覆盖在原图片区域，上层图片只剩原非图片区域
//        g2.setComposite(AlphaComposite.SrcOver);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));


        //覆盖一个椭圆
        g2.fillOval(0, 250, 1000, 500);


        // 目标文件必须是PNG文件
        ImageIO.write(img, "PNG", new File(P));
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
