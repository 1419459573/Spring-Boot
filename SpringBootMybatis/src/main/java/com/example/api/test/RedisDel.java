package com.example.api.test;

import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.Set;

public class RedisDel {
    public static void main(String[] args) {
        String str="";
//        Jedis jedis=new Jedis("0.0.0.0",6379);//测试环境
//        jedis.auth("password");//测试环境
        Jedis jedis=new Jedis("0.0.0.0",6379);//正式环境
        jedis.auth("password");//正式环境
        jedis.connect();
        Set<String> keys = jedis.keys("*"+str + "*");
        Iterator<String> it = keys.iterator();
        while(it.hasNext()){
            String keyStr = it.next();
            System.out.println(keyStr);
            jedis.del(keyStr);
        }
        jedis.disconnect();
    }
}
