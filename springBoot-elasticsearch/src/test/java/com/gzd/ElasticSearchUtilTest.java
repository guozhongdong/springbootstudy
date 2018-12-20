package com.gzd;

import com.gzd.util.ElasticSearchUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gzd
 * @create 2018-12-20 23:25
 * @desc
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ElasticSearchUtilTest {

    @Test
    public void addDocforUtil(){
        Map<String,Object> map = new HashMap<>();
        map.put("clusterName","elasticsearch");
        map.put("url","127.0.0.1:9300");

        try {
            ElasticSearchUtil elasticSearchUtil = new ElasticSearchUtil(map);
            Map<String,Object> param = new HashMap<>();
            param.put("name","cehsi33");
            param.put("age","18");

            // 新增一条数据
            elasticSearchUtil.addDoc("test2","test2",param);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }
}
