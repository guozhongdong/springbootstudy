package com.gzd.dao;

import java.io.Serializable;

/**
 * @author gzd
 * @create 2018-12-19 23:41
 * @desc 创建user对象 实现redis的读写
 **/
public class User implements Serializable {


    private String username;
    private Integer age;

    public User(String username, Integer age){
        this.username = username;
        this.age = age;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
