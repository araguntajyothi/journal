package com.springboot.rest.api.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

//    set key serialization
//    get key de-serialization
    @Disabled
    @Test
    void testSenEmail(){

        redisTemplate.opsForValue().set("email", "akshayajyothi17@gmail.com");

      Object email =   redisTemplate.opsForValue().get("email");
        System.out.println(email);

    }
}
