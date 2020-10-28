package com.jedreck.qrcode.controller;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

import static cn.hutool.core.img.ImgUtil.IMAGE_TYPE_PNG;

@Slf4j
@RestController
@RequestMapping("/qrcode")
public class TestController {
    @GetMapping("/1")
    public void test1(@RequestParam("content") String content, HttpServletResponse response) throws IOException {
        QrCodeUtil.generate(content, 200, 200, "png", response.getOutputStream());
    }

    @GetMapping("/2")
    public void test2(@RequestParam("content") String content, HttpServletResponse response) throws IOException {
        File file = ResourceUtils.getFile("classpath:jing.png");

        QrConfig qrConfig = new QrConfig();
        qrConfig.setHeight(300);
        qrConfig.setWidth(300);
        qrConfig.setImg(file);
        qrConfig.setRatio(5);
        qrConfig.setMargin(1);
        qrConfig.setErrorCorrection(ErrorCorrectionLevel.Q);


        QrCodeUtil.generate(content, qrConfig, IMAGE_TYPE_PNG, response.getOutputStream());
    }
}
