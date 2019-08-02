package com.example.api.testRedis;

import com.example.api.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/redis", produces = "application/json; charset=UTF-8")
public class RedisController {

    @Autowired
    private RedisTemplate  redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping( "/set")
    public String setUserName( String name) {
        redisTemplate.opsForValue().set("1", name);
        return "succees";
    }

    @RequestMapping( "/get")
    public String getUserName( String key) {
        return  redisTemplate.opsForValue().get(key).toString();
    }

    @RequestMapping( "/delete")
    public Boolean deleteKey(String key) {
        return redisUtil.deleteKey(key);
    }

    @RequestMapping( "/newset")
    public Boolean newSetName(String name) {
        return redisUtil.setString("2",name);
    }


}
