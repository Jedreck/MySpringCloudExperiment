package com.jedreck.qrcode.zxingtest01.atest;

import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.jedreck.qrcode.zxingtest01.constants.MediaType;
import com.jedreck.qrcode.zxingtest01.entity.DotSize;
import com.jedreck.qrcode.zxingtest01.helper.QrCodeRenderHelper;
import com.jedreck.qrcode.zxingtest01.utils.FileReadUtil;
import com.jedreck.qrcode.zxingtest01.utils.FileWriteUtil;
import com.jedreck.qrcode.zxingtest01.utils.ImageLoadUtil;
import com.jedreck.qrcode.zxingtest01.wrapper.QrCodeGenWrapper;
import com.jedreck.qrcode.zxingtest01.wrapper.QrCodeOptions;
import com.jedreck.qrcode.zxingtest01.wrapper.StringPicture;
import net.coobird.thumbnailator.Thumbnails;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Test01 {

    public static final String T = "真jahiusdnfiaoiermvoaueroijamklsdvuqweiopamvioASUdrfomiaskodvicuawopefmas";
    public static final String P = "/tmp/aaa.png";

    public static void main(String[] args) throws IOException, WriterException {
        test01();
    }

    @Test
    public void test16() throws IOException {
        BufferedImage img = ImageLoadUtil.getImageByPath("D:/Desktop/generate.png");
        for (int i = 2; i <= 6; i++) {
            Thumbnails.of(img)
                    .size(i * 100, i * 100)
                    .outputQuality(0.9)
                    .outputFormat(MediaType.ImagePng.getExt())
                    .toFile(new File("D:/Desktop/th" + i * 100));
        }
    }

    @Test
    public void test15() throws IOException, WriterException {
        for (int a = 0; a < 5; a++) {
            long start = System.currentTimeMillis();
            QrCodeGenWrapper.Builder builder =
                    QrCodeGenWrapper.of(a + "https://").setH(635).setW(635);
            System.out.println("##########################");
            System.out.println("耗时0-->" + (System.currentTimeMillis() - start));
            builder
                    .setBgImg("https://404.png")
//                    .setBgOutImg("https://404.png")
                    .setDrawBgColor(Color.WHITE)
                    .setBgOutImgW(1000)
                    .setBgOutImgH(1000)
                    .setBgStartX(183)
                    .setBgStartY(146)
                    .setBgStyle(QrCodeOptions.BgImgStyle.FILL);
            System.out.println("耗时1-->" + (System.currentTimeMillis() - start));
            builder
                    .setDetectOutColor(Color.blue)
                    .setDetectPatterning(QrCodeOptions.DetectPatterning.ROUND_RECT_ONE_RIGHT_ANGLE)
                    .setDetectSpecial();

            builder.setDrawStyle(QrCodeOptions.DrawStyle.CIRCLE);

            builder.setDrawBgColor(Color.gray);
            System.out.println("耗时2-->" + (System.currentTimeMillis() - start));
            builder
                    .setLogo("https://QY090.png")
                    .setLogoRate(30)
                    .setLogoPosition(QrCodeOptions.LogoPosition.MIDDLE)
                    .setLogoStyle(QrCodeOptions.LogoStyle.NORMAL);
            System.out.println("耗时3-->" + (System.currentTimeMillis() - start));
            builder
                    .setNotes(Arrays.asList("还哦水电费", "pqygjzzz"))
                    .setNoteFont(new Font("思源黑体", Font.PLAIN, 120))
                    .setNoteFontColor(new Color(Integer.parseUnsignedInt("FF000000", 16), true))
                    //                .setNoteOutlineColor(Color.WHITE)
                    .setNotePosition(QrCodeOptions.NotePosition.DOWN);

            builder.setPicType("png");
            System.out.println("耗时4-->" + (System.currentTimeMillis() - start));
            builder.asFile(P);
            System.out.println("耗时5-->" + (System.currentTimeMillis() - start));
            System.out.println("##########################");
        }
    }

    @Test
    public void test14() throws IOException, WriterException {
        QrCodeGenWrapper.Builder builder = QrCodeGenWrapper.of(T)
                .setH(635)
                .setW(635);

        builder
                .setBgImg("https://404.png")
//                .setBgOutImg("https://404.png")
                .setDrawBgColor(Color.WHITE)
                .setBgOutImgW(1000)
                .setBgOutImgH(1000)
                .setBgStartX(183)
                .setBgStartY(146)
                .setBgStyle(QrCodeOptions.BgImgStyle.FILL);

        builder.setDetectOutColor(Color.blue)
                .setDetectPatterning(QrCodeOptions.DetectPatterning.ROUND_RECT_ONE_RIGHT_ANGLE)
                .setDetectSpecial();

        builder.setDrawStyle(QrCodeOptions.DrawStyle.CIRCLE);

        builder.setDrawBgColor(Color.gray);

        builder
                .setLogo("https://QY090.png")
                .setLogoRate(30)
                .setLogoPosition(QrCodeOptions.LogoPosition.MIDDLE)
                .setLogoStyle(QrCodeOptions.LogoStyle.NORMAL);

        builder.setNotes(Arrays.asList("爱上对方你欧", "pqygjzzz"))
                .setNoteFont(new Font("思源黑体", Font.PLAIN, 120))
                .setNoteFontColor(new Color(Integer.parseUnsignedInt("FF000000", 16), true))
//                .setNoteOutlineColor(Color.WHITE)
                .setNotePosition(QrCodeOptions.NotePosition.DOWN);

        builder.setPicType("png");

        long s = System.currentTimeMillis();
        builder.asFile(P);
        long e = System.currentTimeMillis();
        System.out.println(e - s);
    }

    @Test
    public void test13() throws IOException, WriterException {
        QrCodeGenWrapper.Builder builder = QrCodeGenWrapper.of(T)
                .setH(500)
                .setW(500);
        builder.setDetectPatterning(QrCodeOptions.DetectPatterning.RECT).asFile("/tmp/200.png");
        builder.setDetectPatterning(QrCodeOptions.DetectPatterning.ROUND_RECT).asFile("/tmp/201.png");
        builder.setDetectPatterning(QrCodeOptions.DetectPatterning.ROUND_RECT_CIRCLE).asFile("/tmp/202.png");
        builder.setDetectPatterning(QrCodeOptions.DetectPatterning.CIRCLE).asFile("/tmp/203.png");
        builder.setDetectPatterning(QrCodeOptions.DetectPatterning.MONEY).asFile("/tmp/204.png");
        builder.setDetectPatterning(QrCodeOptions.DetectPatterning.ROUND_RECT_ONE_RIGHT_ANGLE).asFile("/tmp/205.png");
        builder.setDetectPatterning(QrCodeOptions.DetectPatterning.RECT_ONE_ROUND_ANGLE).asFile("/tmp/206.png");
        builder.setDetectPatterning(QrCodeOptions.DetectPatterning.RECT_TWO_ROUND_ANGLE_LT).asFile("/tmp/207.png");
        builder.setDetectPatterning(QrCodeOptions.DetectPatterning.RECT_TWO_ROUND_ANGLE_LD).asFile("/tmp/208.png");
    }

    @Test
    public void test12() throws IOException, WriterException {
        QrCodeGenWrapper.Builder builder = QrCodeGenWrapper.of(T)
                .setH(500)
                .setW(500);
        builder.setDrawStyle(QrCodeOptions.DrawStyle.RECT)
                .asFile("/tmp/100.png");
        builder.setDrawStyle(QrCodeOptions.DrawStyle.CIRCLE)
                .setDrawEnableScale(false)
                .asFile("/tmp/101.png");
        builder.setDrawStyle(QrCodeOptions.DrawStyle.CIRCLE)
                .setDrawEnableScale(true)
                .asFile("/tmp/102.png");
        builder.setDrawStyle(QrCodeOptions.DrawStyle.DIAMOND)
                .setDrawEnableScale(false)
                .asFile("/tmp/103.png");
        builder.setDrawStyle(QrCodeOptions.DrawStyle.TRIANGLE)
                .setDrawEnableScale(false)
                .asFile("/tmp/104.png");
        builder.setDrawStyle(QrCodeOptions.DrawStyle.SEXANGLE)
                .setDrawEnableScale(false)
                .asFile("/tmp/105.png");
        builder.setDrawStyle(QrCodeOptions.DrawStyle.OCTAGON)
                .setDrawEnableScale(false)
                .asFile("/tmp/106.png");
    }

    @Test
    public void test11() throws IOException, WriterException {
        QrCodeGenWrapper.of(T)
                .setH(1000)
                .setW(1000)
                .setLogoRate(50)
                .setLogo("sky.jpg")
                .setNotes(Arrays.asList("AAA", "aiosh后发哦你说地扶8q973"))
                .setNoteFont(new Font("幼圆", Font.BOLD, 60))
                .setNoteFontColor(new Color(Integer.parseInt("0000FFE6", 16), true))
                .setNoteOutlineColor(Color.WHITE)
                .setNotePosition(QrCodeOptions.NotePosition.MIDDLE)
                .asFile(P);
    }

    @Test
    public void test10() throws IOException {
        List<String> s = Arrays.asList("123", "123456", "456168165asdfgkjashdfklhaui");

        String[] st = new String[s.size()];
//        BufferedImage image = StringPicture.createImage(s.toArray(st), null, Color.pink, null);
        BufferedImage image1 = StringPicture.createImageOutline(s.toArray(st), new Font("幼圆", Font.PLAIN, 50), Color.pink, Color.black, null);
//        System.out.println(image.getWidth());
        ImageIO.write(image1, "png", new File(P));
    }

    private static void test09() throws IOException, WriterException {
        QrCodeGenWrapper.of(T)
                .setDetectPatterning(QrCodeOptions.DetectPatterning.RECT_TWO_ROUND_ANGLE_LD)
                .setDetectOutColor(Color.BLUE)
                .setDetectInColor(Color.red)
                .asFile(P);
    }

    public static void test08() throws IOException, WriterException {
        QrCodeGenWrapper.of(T)
                .setH(1000)
                .setW(1000)
                .setLogo("sky.jpg")
                .setLogoRate(30)
                .setLogoStyle(QrCodeOptions.LogoStyle.CIRCLE)
                .setLogoBgColor(Color.black)
//                .setLogoBorderBgColor(0x33000000)
                .setLogoOpacity(1)
                .setDrawPreColor(Color.RED)
                .setDrawStyle(QrCodeOptions.DrawStyle.TXT)
                .asFile(P);
    }

    public static void test07() throws IOException, WriterException {
        String[] s = {"456789", "123123"};
        QrCodeGenWrapper.of(T)
                .setH(1000)
                .setLogoStr(s, new Font("宋体", Font.BOLD, 300), Color.RED, Color.WHITE)
                .setLogoRate(50)
                .asFile(P);
    }

    public static void test06() throws IOException, WriterException {
        // 图绘制点 + 图为背景框
        BufferedImage qrImg = QrCodeGenWrapper.of(T)
                .setPadding(0)
                .setBgImg("sky.jpg")
                .setBgStyle(QrCodeOptions.BgImgStyle.PENETRATE)
                .setDetectPatterning(QrCodeOptions.DetectPatterning.ROUND_RECT)
                .asBufferedImage();

        QrCodeOptions.BgImgOptions bgImgOptions = new QrCodeOptions.BgImgOptions();
        bgImgOptions.setBgImg(ImageIO.read(FileReadUtil.getStreamByFileName("sky.jpg")));
        bgImgOptions.setBgImgStyle(QrCodeOptions.BgImgStyle.FILL);
        bgImgOptions.setStartX(500);
        bgImgOptions.setStartY(100);
        QrCodeOptions qrCodeConfig = new QrCodeOptions();
        qrCodeConfig.setBgImgOptions(bgImgOptions);
        BufferedImage bufferedImage1 = QrCodeRenderHelper.drawBackground(qrImg, qrCodeConfig);

        File file = new File(P);
        FileWriteUtil.mkDir(file.getParentFile());
        ImageIO.write(bufferedImage1, MediaType.ImagePng.getExt(), file);

    }

    public static void test05Draw() throws IOException, WriterException {
        QrCodeGenWrapper.of(T)
                .setDrawPreColor(0xff0096ff)
//                .setDrawBgImg("sky.jpg")
                .setDrawBgColor(0x00000000)
                .asFile(P);
    }

    public static void test04() throws IOException, WriterException {
        QrCodeGenWrapper.of(T)
                .setW(500)
                .setH(500)
                .setDrawStyle(QrCodeOptions.DrawStyle.CIRCLE)
                .setDrawEnableScale(true)
                //1
                .setBgImg("sky.jpg")
                //1.1
//                .setBgStyle(QrCodeOptions.BgImgStyle.OVERRIDE)
//                .setBgOpacity(.3f)
                //1.2
//                .setBgStyle(QrCodeOptions.BgImgStyle.FILL)
//                .setBgStartX(300)
//                .setBgStartY(500)

                //1.3
                .setBgStyle(QrCodeOptions.BgImgStyle.PENETRATE)
                .asFile(P);
    }

    public static void test01() throws IOException, WriterException {
        QrCodeGenWrapper.of(T).setDetectPatterning(QrCodeOptions.DetectPatterning.RECT).asFile(P);
    }

    public static void test02() throws IOException, WriterException {
        QrCodeGenWrapper.of(T)
                .setW(30)
                .setH(30)
                .setErrorCorrection(ErrorCorrectionLevel.Q)
//                .setDrawStyle(QrCodeOptions.DrawStyle.DIAMOND)
                .setLogo("jing.png")
                .setLogoBgColor(0xff0096ff)
                .setLogoRate(10)
                .setLogoOpacity(0.1f)
                .asFile(P);
    }

    public static void test03() throws IOException, WriterException {
        QrCodeGenWrapper.of(T)
                .setH(500)
                .setW(500)
                .setDrawStyle(new QrCodeOptions.DrawStyle() {
                    @Override
                    public void draw(Graphics2D g2d, int x, int y, int size, int h, BufferedImage img, String txt) {
                        int add = size / 3;
                        int px[] = {x + add, x + size - add, x + size, x + size, x + size - add, x + add, x, x};
                        int py[] = {y, y, y + add, y + size - add, y + size, y + size, y + size - add, y + add};
                        g2d.fillPolygon(px, py, 8);
                    }

                    @Override
                    public boolean expand(DotSize dotSize) {
                        return dotSize.getRow() == dotSize.getCol();
                    }
                })
                .asFile(P);
    }
}
