package com.gzd;

import com.gzd.config.ElasticSearchConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gzd
 * @create 2018-12-20 23:17
 * @desc
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ElastiSearchConfigTest {

    @Autowired
    private ElasticSearchConfig elasticSearchConfig;


    /**
     * 通过springboot 配置客户端初始化
     * */
    @Test
    public void testAdd(){
        Map<String,Object> map = new HashMap<>();
        for (int i = 0; i <100; i++) {
            map.put("name","haha99"+i);
            map.put("age",19+i);
            elasticSearchConfig.addDoc("test3","test3",map);
        }

    }


    /**
     * 通过springboot 配置客户端初始化
     * */
    @Test
    public void testAdd11(){
        long start = System.currentTimeMillis();
        Map<String,Object> map = new HashMap<>();

        try {
            elasticSearchConfig.outToFile("test3","test3","d:\\test.csv");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("时间为："+(System.currentTimeMillis()-start));
    }
}
