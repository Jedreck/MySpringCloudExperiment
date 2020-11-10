package com.jedreck.qrcode.zxingtest01.atest;

import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.jedreck.qrcode.zxingtest01.constants.MediaType;
import com.jedreck.qrcode.zxingtest01.entity.DotSize;
import com.jedreck.qrcode.zxingtest01.helper.QrCodeRenderHelper;
import com.jedreck.qrcode.zxingtest01.utils.FileReadUtil;
import com.jedreck.qrcode.zxingtest01.utils.FileWriteUtil;
import com.jedreck.qrcode.zxingtest01.wrapper.QrCodeGenWrapper;
import com.jedreck.qrcode.zxingtest01.wrapper.QrCodeOptions;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Test01 {

    public static final String T = "真是";
    public static final String P = "/tmp/aaa.png";

    public static void main(String[] args) throws IOException, WriterException {
        test06();
    }

    private static void test09() throws IOException, WriterException {
        QrCodeGenWrapper.of(T)
                .setDetectImg("sky.jpg")
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
        QrCodeGenWrapper.of(T)
                .setH(1000)
                .setLogoStr("456789", "123123", new Font("宋体", Font.BOLD, 300), Color.RED, Color.WHITE)
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
        BufferedImage bufferedImage1 = QrCodeRenderHelper.drawBackground(qrImg, bgImgOptions);

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
        QrCodeGenWrapper.of(T).asFile(P);
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
