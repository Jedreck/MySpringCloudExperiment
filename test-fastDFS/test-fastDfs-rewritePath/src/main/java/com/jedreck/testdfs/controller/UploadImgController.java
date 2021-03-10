package com.jedreck.testdfs.controller;

import com.jedreck.testdfs.service.UploadImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/img")
public class UploadImgController {
    @Autowired
    private UploadImageService uploadImageService;

    @PostMapping("/t1")
    public String t1(@RequestParam(name = "file") MultipartFile multipartFile) throws IOException {
        return uploadImageService.uploadImg(multipartFile);
    }
}
