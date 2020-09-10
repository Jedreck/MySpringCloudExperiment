package com.jedreck.elasticsearch.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jedreck.elasticsearch.article.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {
    public static final String ARTICLE_NAME = "title";
    public static final String ARTICLE_CONTENT = "content";
    public static final String ARTICLE_INDEX = "article_index";
    public static final List<String> ACCEPT_TYPE = Arrays.asList(".txt", ".md");

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient esClient;

    @Override
    public String uploadFiles(MultipartFile[] files) {
        BulkRequest bulkRequest = new BulkRequest();
        for (MultipartFile file : files) {
            //判断文件类型
            String fileFullName = file.getOriginalFilename();
            if (fileFullName == null || fileFullName.length() <= 0) {
                return "error";
            }
            int lastDot = fileFullName.lastIndexOf(".");
            String fileName = fileFullName.substring(0, lastDot);
            String fileType = fileFullName.substring(lastDot);
            if (!ACCEPT_TYPE.contains(fileType)) {
                return "error";
            }
            if (fileName.length() <= 0) {
                return "error";
            }
            JSONObject article = new JSONObject();
            article.put(ARTICLE_NAME, fileName);
            article.put(ARTICLE_CONTENT, stream2String(file));
            bulkRequest.add(new IndexRequest(ARTICLE_INDEX)
                    .timeout(TimeValue.timeValueSeconds(1))
                    .source(article, XContentType.JSON));
        }

        BulkResponse bulkResponse;
        try {
            bulkResponse = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            if (bulkResponse.hasFailures()) {
                return "error";
            }
        } catch (IOException e) {
            return "error";
        }

        return JSON.toJSONString(bulkResponse);
    }

    @Override
    public String search(int pageNum, int pageSize, String queryText) {
        SearchRequest request = new SearchRequest(ARTICLE_INDEX);

        SearchSourceBuilder searchSource = new SearchSourceBuilder()
                .timeout(TimeValue.timeValueSeconds(5))
                .from(pageSize * (pageNum - 1))
                .size(pageSize);

        HighlightBuilder highlightBuilder = new HighlightBuilder().preTags("  <span style='color: red'>")
                .postTags("</span>   ")
                .field(ARTICLE_NAME)
                .field(ARTICLE_CONTENT);
        searchSource.highlighter(highlightBuilder);

        searchSource.query(
                QueryBuilders.boolQuery()
                        .should(QueryBuilders.matchQuery(ARTICLE_CONTENT, queryText))
                        .should(QueryBuilders.matchQuery(ARTICLE_NAME, queryText))
        )
                .fetchSource(new String[]{ARTICLE_NAME}, null);

        request.source(searchSource);
        //查询
        SearchResponse response;
        try {
            response = esClient.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            return null;
        }

        SearchHit[] hits = response.getHits().getHits();
        Map<String, String> result = new HashMap<>(hits.length);
        for (SearchHit hit : hits) {
            StringBuilder stringBuilder = new StringBuilder();
            if (hit.getHighlightFields().get(ARTICLE_NAME) != null) {
                stringBuilder.append("<p>---title begin<p>");
                for (Text h : hit.getHighlightFields().get(ARTICLE_NAME).getFragments()) {
                    stringBuilder.append(h).append("<p>");
                }
                stringBuilder.append("<p>---title end<p>");
            }
            if (hit.getHighlightFields().get(ARTICLE_CONTENT) != null) {
                for (Text h : hit.getHighlightFields().get(ARTICLE_CONTENT).getFragments()) {
                    stringBuilder.append(h).append("<p>");
                }
            }
            result.put(hit.getSourceAsString(), stringBuilder.toString());
        }

        return JSON.toJSONString(result);
    }

    public static String stream2String(MultipartFile file) {
        String line;
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("读取文件错误");
        }
        return sb.toString();
    }
}
