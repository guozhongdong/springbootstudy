package com.gzd.config;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author gzd
 * @create 2018-12-20 23:11
 * @desc
 **/
@Configuration
public class ElasticSearchConfig {

    @Autowired
    private TransportClient transportClient;

    /**
     * 添加文档
     *
     * @param indexName 索引类型
     * @param indexType 类型
     * @param data      数据
     * @return 文档id
     */
    public  String addDoc(String indexName, String indexType, Map<String, Object> data) {
        IndexResponse indexResponse = transportClient.prepareIndex(indexName, indexType)
                .setSource(data)
                .execute().actionGet();
        return indexResponse.getId();
    }
}
