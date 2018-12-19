package com.gzd;

import com.gzd.dao.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author gzd
 * @create 2018-12-19 23:22
 * @desc
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, User> redisTemplate;

    @Test
    public void test(){
        stringRedisTemplate.opsForValue().set("aaa","bbbb");
        Assert.assertEquals("bbbb",stringRedisTemplate.opsForValue().get("aaa"));
    }

    @Test
    public void test1(){
        User user = new User("超人",20);
        redisTemplate.opsForValue().set(user.getUsername(),user);

         user = new User("蝙蝠侠",30);
        redisTemplate.opsForValue().set(user.getUsername(),user)
        ;
         user = new User("钢铁侠",40);
        redisTemplate.opsForValue().set(user.getUsername(),user);

        Assert.assertEquals(20,redisTemplate.opsForValue().get("超人").getAge().longValue());
        Assert.assertEquals(30,redisTemplate.opsForValue().get("蝙蝠侠").getAge().longValue());
        Assert.assertEquals(40,redisTemplate.opsForValue().get("钢铁侠").getAge().longValue());

    }

}
