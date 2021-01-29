package com.jedreck.qrcode.zxingtest01.atest;

import com.google.zxing.WriterException;
import com.jedreck.qrcode.zxingtest01.constants.MediaType;
import com.jedreck.qrcode.zxingtest01.utils.ColorUtil;
import com.jedreck.qrcode.zxingtest01.utils.ImageLoadUtil;
import com.jedreck.qrcode.zxingtest01.utils.ImageUtil;
import com.jedreck.qrcode.zxingtest01.wrapper.QrCodeGenWrapper;
import com.jedreck.qrcode.zxingtest01.wrapper.QrCodeOptions;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Test02 {
    public static final String P = "/tmp/bbb.png";

    public static void main(String[] args) throws Exception {
        test01(ImageLoadUtil.getImageByPath("sky.jpg"), 5, 5, Color.GRAY, .5f);
    }

    /**
     * 存储图片
     */
    @Test
    public void savePic() throws IOException {
        BufferedImage image = ImageLoadUtil.getImageByPath("D:\\Desktop\\999.png");
        File file = new File("/tmp/7777.jpg");

        // 存储图片方式一 严重失真
//        FileWriteUtil.mkDir(file);
//        ImageIO.write(image, MediaType.ImageJpg.getExt(), file);

        // 存储图片方式二 微小失真
//        Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");
//        if (iter.hasNext()) {
//            ImageWriter writer = iter.next();
//
//            ImageWriteParam param = writer.getDefaultWriteParam();
//            // 设置可否压缩
//            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//            // 最高质量
////            param.setCompressionQuality(1.0f);
//            // 最接近真实
//            param.setCompressionQuality(0.955f);
//
//            file = new File("/tmp/8881.jpg");
//            FileImageOutputStream out = new FileImageOutputStream(file);
//            writer.setOutput(out);
//
//            writer.write(null, new IIOImage(image, null, null), param);
//
//            out.close();
//            writer.dispose();
//        }

        // 工具类
        file = new File("/tmp/8881.jpg");
        FileImageOutputStream out2 = new FileImageOutputStream(file);
        ImageUtil.getStream(image, MediaType.ImageJpg.getExt(), null, out2);
        out2.close();
//        file = new File("/tmp/8882.png");
//        FileImageOutputStream out3 = new FileImageOutputStream(file);
//        ImageUtil.getStream(image, MediaType.ImagePng.getExt(), 1f, out3);
//        out3.close();

        ImageIO.write(image, MediaType.ImageJpg.getExt(), new File("/tmp/8883.jpg"));
        ImageIO.write(image, MediaType.ImagePng.getExt(), new File("/tmp/8884.png"));

        System.out.println("success");
    }


    /**
     * 图片缩放
     */
    @Test
    public void zoomPic() throws IOException {
        BufferedImage bufferedImage = ImageLoadUtil.getImageByPath("D:\\Desktop\\999.png");
        BufferedImage image2 = ImageLoadUtil.getImageByPath("sky.jpg");
//        BufferedImage resize = ImageUtil.resizeMin(image, 120);
//        BufferedImage resize2 = ImageUtil.resizeMax(image, 120);
//        BufferedImage resize3 = ImageUtil.resizeMin(image2, 120);
//        BufferedImage resize4 = ImageUtil.resizeMax(image2, 120);
//        BufferedImage bufferedImage = ImageUtil.resizeByte(image, 2000, 500, "jpg");

        // 存储图片
        ImageUtil.savePic(bufferedImage, "jpg", "/tmp/bbb.jpg");
        ImageIO.write(bufferedImage, MediaType.ImagePng.getExt(), new File("/tmp/bbb2.png"));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, MediaType.ImagePng.getExt(), out);
        byte[] bytes = out.toByteArray();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        BufferedImage read = ImageIO.read(in);
        ImageIO.write(read, MediaType.ImageJpg.getExt(), new File("/tmp/bbb3.jpg"));


        System.out.println("success");
    }

    @Test
    public void simpleTest() throws IOException, WriterException {
        QrCodeGenWrapper
//                .of("https://qr.encdata.cn/h5/sFBkjKYHA")
                .of("https://qr.encdata.cn/mini?m=scan&e=EcXw5fFxm")
                .setDetectPatterning(QrCodeOptions.DetectPatterning.RECT)
                .setPadding(2)
                .asFile(P);
    }

    /**
     * 矩形 左上 右下圆角
     */
    @Test
    public void test08() throws IOException {
        double rotate = 90;
        Color inColor = Color.YELLOW;
        Color outColor = Color.BLACK;
        Color bgColor = Color.PINK;

        int W = 1000;
        double s = W / 7.0;
        double s2 = s * 2;
        double s3 = s * 3;
        double s4 = s * 4;
        double s5 = s * 5;

        BufferedImage img = new BufferedImage(W, W, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.Src);
        g2.setColor(bgColor);
        g2.fillRect(0, 0, W, W);
        g2.rotate(Math.toRadians(rotate), W >> 1, W >> 1);

        g2.setColor(outColor);
        g2.fillRect(0, 0, W, W);
        g2.setColor(bgColor);
        g2.fillRect((int) s, (int) s, (int) s5, (int) s5);
        g2.setColor(inColor);
        g2.fillRect((int) s2, (int) s2, (int) s3, (int) s3);

        //限制区域
        int i = W >> 1;
        int[][] area = {{0, i}, {i, i}};
        for (int[] a : area) {
            g2.setClip(a[0], a[0], a[1], a[1]);
            g2.setColor(bgColor);
            g2.fillRect(0, 0, W, W);

            g2.setColor(outColor);
            g2.fillRoundRect(0, 0, W, W, (int) s5, (int) s5);
            g2.setColor(bgColor);
            g2.fillRoundRect((int) s, (int) s, (int) s5, (int) s5, (int) s4, (int) s4);
            g2.setColor(inColor);
            g2.fillRoundRect((int) s2, (int) s2, (int) s3, (int) s3, (int) s2, (int) s2);
        }

        ImageIO.write(img, "PNG", new File(P));
    }

    /**
     * 矩形 右上直角
     */
    @Test
    public void test07() throws IOException {
        double rotate = 90;
        Color inColor = Color.YELLOW;
        Color outColor = Color.BLACK;
        Color bgColor = Color.PINK;

        int W = 1000;
        double s = W / 7.0;
        double s2 = s * 2;
        double s3 = s * 3;
        double s4 = s * 4;
        double s5 = s * 5;

        BufferedImage img = new BufferedImage(W, W, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.Src);
        g2.setColor(bgColor);
        g2.fillRect(0, 0, W, W);
        g2.rotate(Math.toRadians(rotate), W >> 1, W >> 1);

        g2.setColor(outColor);
        g2.fillRect(0, 0, W, W);
        g2.setColor(bgColor);
        g2.fillRect((int) s, (int) s, (int) s5, (int) s5);
        g2.setColor(inColor);
        g2.fillRect((int) s2, (int) s2, (int) s3, (int) s3);

        //限制区域
        g2.setClip(0, 0, W >> 1, W >> 1);
        g2.setColor(bgColor);
        g2.fillRect(0, 0, W, W);

        g2.setColor(outColor);
        g2.fillRoundRect(0, 0, W, W, (int) s5, (int) s5);
        g2.setColor(bgColor);
        g2.fillRoundRect((int) s, (int) s, (int) s5, (int) s5, (int) s4, (int) s4);
        g2.setColor(inColor);
        g2.fillRoundRect((int) s2, (int) s2, (int) s3, (int) s3, (int) s2, (int) s2);


        ImageIO.write(img, "PNG", new File(P));
    }

    /**
     * 圆角矩形 右上直角
     */
    @Test
    public void test06() throws IOException {
        int W = 1000;
        double s = W / 7.0;
        double s2 = s * 2;
        double s3 = s * 3;
        double s4 = s * 4;
        double s5 = s * 5;

        BufferedImage img = new BufferedImage(W, W, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.Src);
        g2.setColor(Color.PINK);
        g2.fillRect(0, 0, W, W);

        //旋转
        g2.rotate(Math.toRadians(90), W >> 1, W >> 1);

        g2.setColor(Color.BLACK);
        g2.fillRoundRect(0, 0, W, W, (int) s5, (int) s5);

        g2.setColor(Color.PINK);
        g2.fillRoundRect((int) s, (int) s, (int) s5, (int) s5, (int) s4, (int) s4);

        g2.setColor(Color.YELLOW);
        g2.fillRoundRect((int) s2, (int) s2, (int) s3, (int) s3, (int) s2, (int) s2);

        //限制区域
        g2.setClip(0, 0, W >> 1, W >> 1);

        g2.setColor(Color.PINK);
        g2.fillRect(0, 0, W, W);

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, W, W);

        g2.setColor(Color.PINK);
        g2.fillRect((int) s, (int) s, (int) s5, (int) s5);

        g2.setColor(Color.YELLOW);
        g2.fillRect((int) s2, (int) s2, (int) s3, (int) s3);

        ImageIO.write(img, "PNG", new File(P));
    }

    /**
     * 钱 图形
     */
    @Test
    public void test05() throws IOException {
        int W = 1000;
        double s = W / 7.0;
        double sw = s * 2;
        double sww = s * 3;

        BufferedImage img = new BufferedImage(W, W, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setColor(Color.PINK);
        g2.fillRect(0, 0, W, W);

        g2.setColor(Color.BLACK);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillOval(0, 0, W, W);

        g2.setColor(Color.PINK);
        g2.fillOval((int) s, (int) s, (int) (sw + sww), (int) (sw + sww));

        //限制范围
        g2.setClip((int) sw, (int) sw, (int) sww, (int) sww);
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect((int) sw, (int) sw, (int) sww, (int) sww);
        // 切割
        g2.setColor(Color.PINK);
        int circleStartA = (int) ((-(2 + 3 * Math.sqrt(3)) / 2) * s);
        int circleStartB = (int) (s / 2);
        int circleStartC = (int) (((4 + 3 * Math.sqrt(3)) / 2) * s);
        int diameter = (int) s * 6;
        // 左
        g2.fillOval(circleStartA, circleStartB, diameter, diameter);
        // 上
        g2.fillOval(circleStartB, circleStartA, diameter, diameter);
        // 下
        g2.fillOval(circleStartB, circleStartC, diameter, diameter);
        // 右
        g2.fillOval(circleStartC, circleStartB, diameter, diameter);

        ImageIO.write(img, "PNG", new File(P));
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
