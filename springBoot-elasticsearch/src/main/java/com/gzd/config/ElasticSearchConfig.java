package com.gzd.config;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.supercsv.io.CsvListWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.List;
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

    @Autowired
    private org.apache.hadoop.conf.Configuration configuration;

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

    /**
     * elasticsearch 数据到文件
     * @param indexName     索引名称
     * @param typeName      type名称
     * @param filePath      生成的文件路径
     */
    public  void outToFile(String indexName,String typeName,String filePath) throws InterruptedException {


        SearchResponse response = transportClient.prepareSearch(indexName).setTypes(typeName)
                .setQuery(QueryBuilders.matchAllQuery()).
                        setSize(10000).setScroll(new TimeValue(600000))
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .execute().actionGet();
        //setSearchType(SearchType.Scan) 告诉ES不需要排序只要结果返回即可 setScroll(new TimeValue(600000)) 设置滚动的时间
        try {
            //把导出的结果以JSON的格式写到文件里
            String[] headers = {"name","age"};
            CsvListWriter writer = new CsvListWriter(new FileWriter(filePath),CsvPreference.EXCEL_PREFERENCE);
            writer.writeHeader(headers);
            //BufferedWriter out = new BufferedWriter(new FileWriter(filePath, true));

            long count = 0;
            while (true) {
                for(SearchHit hit : response.getHits().getHits()){
                    Map<String, Object> map = hit.getSource();
                    String[] strs = new String[2];
                    strs[0] = String.valueOf(map.get("name"));
                    strs[1] = String.valueOf(map.get("age"));
                    writer.write(strs);
                    count++;

                }
                response = transportClient.prepareSearchScroll(response.getScrollId())
                        .setScroll(new TimeValue(6000)).execute().actionGet();
                if(response.getHits().getHits().length == 0){
                    break;
                }
            }
            FileSystem fs = FileSystem.get(URI.create("hdfs://192.168.247.131:9000"), configuration,"hadoop");
            File hh = new File(filePath);
            Path src = new Path(hh.getPath());
            // 要上传到hdfs的目标路径
            Path dst = new Path("hdfs://192.168.247.131:9000"+"/test1.csv");
            //fs.create(dst);
            fs.copyFromLocalFile(src, dst);
            System.out.println("总共写入数据:"+count);
            writer.close();
            fs.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    /**
     *   * 写入csv文件
     *   *
     *   * @param file
     *   *            File
     *   * @param content
     *   *            内容
     *   * @throws IOException
     *  
     */
    public void writeContentToCsv(File file, List<String[]> content)
            throws IOException {
        CsvListWriter writer = new CsvListWriter(new FileWriter(file),

                CsvPreference.EXCEL_PREFERENCE);
        for (String[] str : content) {
            writer.write(str);
        }
        writer.close();
    }

/**
   * 写入csv文件(头部)
   *
   * @param file
   *            File
   * @param content
   *            内容
   * @throws IOException
   *//*
         public void writeHeaderToCsv(File file, String[] header) throws IOException {
  CsvListWriter writer = new CsvListWriter(new FileWriter(file),

                  CsvPreference.EXCEL_PREFERENCE);
  writer.writeHeader(header);
  writer.close();
 }*/

}
