package com.jedreck.elasticsearch.article.service;

import org.springframework.web.multipart.MultipartFile;

public interface ArticleService {
    /**
     * 上传文章
     * @return
     */
    String uploadFiles(MultipartFile[] files);

    /**
     * 搜索
     */
    String search(int pageNum, int pageSize, String queryText);
}
