package com.jedreck.elasticsearch;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestHighLevelClient;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class ESAggTest {
    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient esClient;

    @Test
    public void base() {

        // 筛选查询 组合
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        // agg 组合
        String dateTimeAgg = "date_time_agg";

        DateHistogramAggregationBuilder dateHistogram = AggregationBuilders.dateHistogram("DATE_TIME_AGG")
                .field("COMMIT_TIME")
                .format(DatePattern.NORM_DATETIME_MINUTE_PATTERN)
                .timeZone(TimeZone.getDefault().toZoneId())
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
}
