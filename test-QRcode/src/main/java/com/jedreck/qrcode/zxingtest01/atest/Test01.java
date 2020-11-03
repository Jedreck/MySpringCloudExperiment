package com.jedreck.qrcode.zxingtest01.atest;

import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.jedreck.qrcode.zxingtest01.wrapper.QrCodeGenWrapper;
import com.jedreck.qrcode.zxingtest01.wrapper.QrCodeOptions;

import java.io.IOException;

public class Test01 {

    public static final String T = "真是太难了";

    public static void main(String[] args) throws IOException, WriterException {
        test02();
    }

    public static void test01() throws IOException, WriterException {
        QrCodeGenWrapper.of(T).asFile("/tmp/aaa.png");
    }

    public static void test02() throws IOException, WriterException {
        QrCodeGenWrapper.of(T)
                .setW(800)
                .setH(800)
                .setErrorCorrection(ErrorCorrectionLevel.Q)
                .setDrawStyle(QrCodeOptions.DrawStyle.RECT)
                .setLogo("jing.png")
                .asFile("/tmp/aaa.png");
    }

    public static void test03(){

    }
}
