package com.jedreck.elasticsearch.article.controller;

import com.jedreck.elasticsearch.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @PostMapping("upload")
    public String upload(@RequestParam("files") MultipartFile[] files) {
        return articleService.uploadFiles(files);
    }

    @GetMapping("get/{pageNum}/{pageSize}/{queryText}")
    public String get(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize, @PathVariable String queryText) {
        return articleService.search(pageNum, pageSize, queryText);
    }

}
