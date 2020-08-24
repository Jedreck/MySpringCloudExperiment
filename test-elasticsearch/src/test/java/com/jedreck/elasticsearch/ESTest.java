package com.jedreck.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.jedreck.elasticsearch.bean.Man;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ESTest {
    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient esClient;

    /**
     * 创建索引
     */
    @Test
    public void test01() throws IOException {
        //1.创建索引请求
        CreateIndexRequest request = new CreateIndexRequest("jedreck_index");
        //2.客户端执行请求 IndicesClient,请求后获得响应
        CreateIndexResponse createIndexResponse = esClient.indices().create(request, RequestOptions.DEFAULT);
        //3.打印
        System.out.println(createIndexResponse.index());
    }

    /**
     * 获取索引
     */
    @Test
    public void test02() throws IOException {
        GetIndexRequest request = new GetIndexRequest("jedreck_index");
        boolean exists = esClient.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists ? "存在" : "不存在");
    }

    /**
     * 删除索引
     */
    @Test
    public void test03() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("jedreck_index");
        AcknowledgedResponse delete = esClient.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged() ? "删除成功" : "删除失败");
    }

    /**
     * 添加文档 PUT XXX/_doc/1
     */
    @Test
    public void test04() throws IOException {
        //方式-1
//        //新建对象
//        Man man = Man.builder().id(111).name("name 1").des("111 name1 name 1, 下一个是2").tag(Arrays.asList("111", "first", "第一个", "下一个是二")).build();
//        //构建请求
//        IndexRequest request = new IndexRequest("jedreck_index")
//                .id("1")
//                .timeout(TimeValue.timeValueSeconds(1))
//                .source(JSON.toJSONString(man), XContentType.JSON);

        //方式-2
//        IndexRequest request = new IndexRequest("jedreck_index").source("id",111,"name","name 1","des","...","tag","...");

        //方式-3
        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();
        xContentBuilder.startObject();
        xContentBuilder.field("id", 1001);
        xContentBuilder.field("name", "name 1001");
        xContentBuilder.field("des").value("aaa");
        xContentBuilder.endObject();
        IndexRequest request = new IndexRequest("jedreck_index").id("1001").timeout("1s").source(xContentBuilder);


        //发送请求
        IndexResponse response = esClient.index(request, RequestOptions.DEFAULT);

        System.out.println(response.toString());

    }

    /**
     * 获取文档
     */
    @Test
    public void test05() throws IOException {
        GetRequest request = new GetRequest("jedreck_index", "1001");

        //不获取上下文  不返回"_source"1
//        request.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
        //不获取上下文  不返回"_source"2
//        request.fetchSourceContext(new FetchSourceContext(false));
//        request.storedFields("_none_");

        //判断文档是否存在
        boolean exists = esClient.exists(request, RequestOptions.DEFAULT);
        if (!exists) {
            return;
        }

        //获取文档信息
        GetResponse response = esClient.get(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());
    }

    /**
     * 更新文档
     */
    @Test
    public void test06() throws IOException {
        Man man = Man.builder().name("name 111").des("111 name1 name 1, 下一个是二").build();

        UpdateRequest request = new UpdateRequest("jedreck_index", "1")
                .timeout("1s")
                .doc(JSON.toJSONString(man), XContentType.JSON);

        UpdateResponse update = esClient.update(request, RequestOptions.DEFAULT);

        System.out.println(update.toString());
    }

    /**
     * 删除文档
     */
    @Test
    public void test07() throws IOException {
        DeleteRequest request = new DeleteRequest("jedreck_index", "1")
                .timeout("1s");

        DeleteResponse delete = esClient.delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.toString());
    }

    /**
     * 大批量
     */
    @Test
    public void test08() throws IOException {
        BulkRequest bulkRequest = new BulkRequest()
                .timeout("10s");
        for (int i = 0; i < 10; i++) {
            int ii = i + i * 10 + i * 100;
            Man man = Man.builder()
                    .id(ii)
                    .name("name " + i)
                    .des(ii + " name" + i + " name " + i + ", 下一个是" + (i + 1))
                    .tag(Arrays.asList(String.valueOf(ii), ii + "th", "第" + i + "个"))
                    .build();
            bulkRequest.add(
                    new IndexRequest("jedreck_index")
                            .id(String.valueOf(i))
                            .source(JSON.toJSONString(man), XContentType.JSON)
            );
        }

        BulkResponse bulk = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulk.hasFailures());
    }

    /**
     * 查询
     */
    @Test
    public void test09() throws IOException {
        SearchRequest request = new SearchRequest("jedreck_index");
        //创建搜索条件
        SearchSourceBuilder searchSource = new SearchSourceBuilder()
                .timeout(TimeValue.timeValueSeconds(5));
        //条件构造
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", "name");

        //放入
        searchSource.query(matchQueryBuilder);
        request.source(searchSource);
        //查询
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());
    }

    private static final String KEY = "jedreck_index";

    /**
     * 搜索全部
     */
    @Test
    public void test10() throws IOException {
        // 1
        SearchRequest request = new SearchRequest();
        // 2
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        // 默认会搜索出 top 10,所以需要添加偏移量和页大小
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(9999);
        // 3
        request.source(searchSourceBuilder);
        // 4
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());
    }

    /**
     * 搜索全部
     * 指定索引
     */
    @Test
    public void test11() throws IOException {
        // 1
        SearchRequest request = new SearchRequest(KEY);
        // 2
        request.routing("routing");
        request.indicesOptions(IndicesOptions.lenientExpandOpen());
        // 4
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());
    }
}
