package com.jedreck.qrcode.zxingtest01.atest;

import com.jedreck.qrcode.zxingtest01.wrapper.QrCodeGenWrapper;
import com.jedreck.qrcode.zxingtest01.wrapper.QrCodeOptions;
import junit.framework.Assert;
import org.junit.Test;

import java.awt.image.BufferedImage;

/**
 * 带背景图的二维码示例demo
 */
public class BgImgQrCodeTest {

    /**
     * 带背景图的二维码, 全覆盖模式
     */
    @Test
    public void testGenFillBgQr() {
        String msg = "http://weixin.qq.com/r/FS9waAPEg178rUcL93oH";
        // 根据本地文件生成待logo的二维码，无边框， 重新着色位置探测图像， 设置背景
        try {
            String bg = "bg.png";
            BufferedImage img = QrCodeGenWrapper.of(msg)
                    .setW(300)
                    .setDrawPreColor(0xff0000ff)
                    .setDrawBgColor(0xffffffff)
                    .setPadding(1)
                    .setLogoRate(15)
                    .setLogoStyle(QrCodeOptions.LogoStyle.ROUND)
                    .setBgImg(bg)
                    .setBgOpacity(0.5f)
                    .asBufferedImage();
            System.out.println("----" + img);
        } catch (Exception e) {
            System.out.println("create qrcode error! e: " + e);
            Assert.assertTrue(false);
        }
    }
}
