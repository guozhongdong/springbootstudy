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
        map.put("name","haha99");
        map.put("age",19);
        elasticSearchConfig.addDoc("test","test",map);
    }
}
