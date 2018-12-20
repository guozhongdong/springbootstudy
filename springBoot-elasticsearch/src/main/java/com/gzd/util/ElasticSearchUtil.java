package com.gzd.util;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author gzd
 * @create 2018-12-20 23:25
 * @desc
 **/
public class ElasticSearchUtil {

    private TransportClient client;

    public ElasticSearchUtil(Map<String, Object> param) throws UnknownHostException {
        String clusterName = (String) param.get("clusterName");
        String urlString = (String) param.get("url");
        String[] urls = urlString.split(",");
        Settings settings = Settings.builder().put("cluster.name", clusterName).build();
        client = new PreBuiltTransportClient(settings);
        for (String url : urls) {
            String[] hosts = url.split(":");
            String ip = hosts[0];
            String port = hosts[1];
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), Integer.parseInt(port)));
        }

    }

    /**
     * 添加文档
     *
     * @param indexName 索引类型
     * @param indexType 类型
     * @param data      数据
     * @return 文档id
     */
    public  String addDoc(String indexName, String indexType, Map<String, Object> data) {
        IndexResponse indexResponse = client.prepareIndex(indexName, indexType)
                .setSource(data)
                .execute().actionGet();
        return indexResponse.getId();
    }
}
