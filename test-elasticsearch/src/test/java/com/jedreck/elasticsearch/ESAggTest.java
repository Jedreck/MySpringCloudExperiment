package com.jedreck.elasticsearch;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.ExtendedBounds;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.TimeZone;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ESAggTest {

    /**
     * ik分词模式
     */
    public static final String IK_SMART = "ik_smart";
    public static final String IK_MAX_WORD = "ik_max_word";
    public static final String RECORD_INDEX = "RECORD_INDEX";

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient esClient;
    private static final String KEYWORD = "keyword";

    /**
     * 建索引
     */
    @Test
    public void createFormDataCommitRecord() {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            builder.startObject("properties");
            builder.startObject("ID")
                    .field("type", "long")
                    .endObject();
            builder.startObject("FORM_ID")
                    .field("type", "long")
                    .endObject();
            builder.startObject("DOC_ID")
                    .field("type", KEYWORD)
                    .endObject();
            builder.startObject("VALUE")
                    .field("type", "text")
                    .field("analyzer", IK_MAX_WORD)
                    .endObject();
            builder.startObject("COMMIT_TIME")
                    .field("type", "date")
                    .endObject();
            builder.startObject("IS_DELETE")
                    .field("type", "integer")
                    .endObject();
            builder.endObject();
            builder.endObject();

            CreateIndexRequest request = new CreateIndexRequest(RECORD_INDEX);
            CreateIndexResponse createIndexResponse = esClient.indices().create(request, RequestOptions.DEFAULT);
            if (createIndexResponse.isAcknowledged()) {
                log.info("ES索引 {} 创建成功", RECORD_INDEX);
            }
        } catch (Exception e) {
            log.error("ES index [{}] 创建失败！", RECORD_INDEX, e);
            throw new RuntimeException("验证ES索引出错：index [" + RECORD_INDEX + "] 创建失败");
        }
    }

    /**
     * 日期统计直方图
     */
    @Test
    public void base() {

        // 筛选查询 组合
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        // agg 组合

        DateHistogramAggregationBuilder dateHistogram = AggregationBuilders
                // 聚合的名称
                .dateHistogram("DATE_TIME_AGG")
                // 表示日期的字段
                .field("COMMIT_TIME")
                // 格式化，结果的key将是这个格式
                .format(DatePattern.NORM_DATETIME_MINUTE_PATTERN)
                // 设置时区
                .timeZone(TimeZone.getDefault().toZoneId())
                // 设置补充时间点，统计将包含这些时间点
                .extendedBounds(new ExtendedBounds((new Date()).getTime(), (new Date()).getTime()));

        // 判断是否同一天,同一天按小时来间隔
        if (DateUtil.betweenDay(new Date(), new Date(), true) == 0) {
            dateHistogram.fixedInterval(DateHistogramInterval.HOUR);
        } else {
            dateHistogram.fixedInterval(DateHistogramInterval.DAY);
        }

        // 查询
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .size(0)
                .query(boolQuery)
                .aggregation(dateHistogram);

        SearchRequest request = new SearchRequest()
                .indices("ESIndexConfig.FORM_DATA_COMMIT_RECORD_INDEX")
                .source(sourceBuilder);
    }


    /**
     * 子聚合
     * 分类后再统计
     *
     * GET COMMIT_RECORD/_search
     * {
     *   "size": 0,
     *   "query": {
     *     "bool": {
     *       "should": [
     *         {
     *           "match": {
     *             "formId": "99"
     *           }
     *         }
     *         ,{
     *           "match": {
     *             "formId": "53"
     *           }
     *         }
     *         ,{
     *           "match": {
     *             "formId": "101"
     *           }
     *         }
     *       ],
     *       "must": [
     *         {
     *           "match": {
     *             "isDelete": "0"
     *           }
     *         }
     *       ]
     *     }
     *   },
     *   "aggregations": {
     *     "All_count":{
     *       "terms": {
     *         "field": "formId"
     *       },
     *       "aggs": {
     *         "preparer_count": {
     *           "cardinality": {
     *             "field": "mongoDocId"
     *           }
     *         },
     *         "doc_count":{
     *           "cardinality": {
     *             "field": "openId"
     *           }
     *         }
     *       }
     *     }
     *   }
     * }
     */
}
