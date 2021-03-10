package com.jedreck.testdfs.controller;

import com.jedreck.testdfs.service.DownloadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/download")
public class DownloadController {
    @Autowired
    private DownloadService downloadService;

    @RequestMapping("")
    public void download(@RequestParam("path") String path, HttpServletResponse response) {
        try {
            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            String[] split = path.split("/");
            String fileName = split[split.length - 1];
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            downloadService.download(path, response.getOutputStream());
        } catch (Exception e) {
            log.error("下载出错:" + path, e);
        }
    }
}
