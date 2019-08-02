package com.example.api.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * CreateTime: 2019-08-01 18:14
 * ClassName: RedisUtil
 * Package: com.example.api.utils
 * Describe:Redis工具类
 *
 * @author XieZhiXin
 */
@Component
public class RedisUtil {

    private static final Long SUCCESS = 1L;


    @Autowired
    private RedisTemplate  redisTemplate;

    //---------------------- common --------------------------

    /**
     * 指定缓存失效时间
     *
     * @param key  key值
     * @param time 缓存时间
     */
    public void expire(String key, long time) {
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        } else {
            throw MyExceptionUtils.mxe("设置的时间不能为0或者小于0！！");
        }
    }

    /**
     * 判断key是否存在
     *
     * @param key 传入ke值
     * @return true 存在  false  不存在
     */
    public Boolean existsKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 判断key存储的值类型
     *
     * @param key key值
     * @return DataType[string、list、set、zset、hash]
     */
    public DataType typeKey(String key) {
        return redisTemplate.type(key);
    }

    /**
     * 删除指定的一个数据
     *
     * @param key key值
     * @return true 删除成功，否则返回异常信息
     */
    public Boolean  deleteKey(String key) {
        try {
             redisTemplate.delete(key);
             return true;
        } catch (Exception ex) {
            throw MyExceptionUtils.mxe("删除失败！", ex);
        }
    }

    /**
     * 删除多个数据
     *
     * @param keys key的集合
     * @return true删除成功，false删除失败
     */
    public Boolean deleteKey(Collection<String> keys) {
        try {
            redisTemplate.delete(keys);
            return true;
        } catch (Exception ex) {
            throw MyExceptionUtils.mxe("删除失败！", ex);
        }
    }

    //-------------------- String ----------------------------

    /**
     * 普通缓存放入
     *
     * @param key   键值
     * @param value 值
     * @return true成功 要么异常
     */
    public Boolean setString(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception ex) {
            throw MyExceptionUtils.mxe("插入缓存失败！", ex);
        }
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object getString(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置缓存存在时间
     *
     * @param key   key值
     * @param value value值
     * @param time  时间 秒为单位
     * @return 成功返回true，失败返回异常信息
     */
    public boolean setString(String key, Object value, long time) {
        try {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            return true;
        } catch (Exception ex) {
            throw MyExceptionUtils.mxe("插入缓存失败！", ex);
        }
    }



    //-----------------------------hash----------------------------------

    /**
     * 设置hash值,并设置过期时间
     *
     * @param key
     * @param hk
     * @param hv
     * @param time
     * @return
     */
    public Boolean setHash(String key, Object hk, Object hv, long time) {
        redisTemplate.opsForHash().put(key, hk, hv);
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
        return true;
    }

    public Boolean setHash(String key, Map map, long time) {
        redisTemplate.opsForHash().putAll(key, map);
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
        return true;
    }

    /**
     * 获取hash的值
     *
     * @param key
     * @param hk
     * @return
     */
    public Object getHash(String key, String hk) {
        return key == null ? null : (hk == null ? null : redisTemplate.opsForHash().get(key, hk));
    }

    /**
     * hash累加
     */
    public Long hincrease(String key, String hk, long l) {
        return redisTemplate.opsForHash().increment(key, hk, l);
    }

    //----------------------------- list ------------------------------

    /**
     * 将list放入缓存
     *
     * @param key   key的值
     * @param value 放入缓存的数据
     * @return true 代表成功，否则返回异常信息
     */
    public Boolean setList(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception ex) {
            throw MyExceptionUtils.mxe("插入List缓存失败！", ex);
        }
    }

    /**
     * 将Object数据放入List缓存，并设置时间
     *
     * @param key   key值
     * @param value 数据的值
     * @param time  缓存的时间
     * @return true插入成功，否则返回异常信息
     */
    public Boolean setList(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForList().rightPush(key, value);
                expire(key, time);
                return true;
            }
            return false;
        } catch (Exception ex) {
            throw MyExceptionUtils.mxe("插入List缓存失败！", ex);
        }
    }

    /**
     * 将list集合放入List缓存，并设置时间
     *
     * @param key   key值
     * @param value 数据的值
     * @param time  缓存的时间
     * @return true插入成功，否则返回异常信息
     */
    public Boolean setListAll(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForList().rightPushAll(key, value);
                this.expire(key, time);
                return true;
            }
            return false;
        } catch (Exception ex) {
            throw MyExceptionUtils.mxe("插入List缓存失败！", ex);
        }
    }

    /**
     * 根据索引获取缓存List中的内容
     *
     * @param key   key的值
     * @param start 索引开始
     * @param end   索引结束 0 到 -1代表所有值
     * @return 返回数据
     */
    public List<Object> getList(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception ex) {
            throw MyExceptionUtils.mxe("获取缓存List中的内容失败了！", ex);
        }
    }

    /**
     * 删除List缓存中多个list数据
     *
     * @param key   key值
     * @param count 移除多少个
     * @param value 可以传null  或者传入存入的Value的值
     * @return 返回删除了多少个
     */
    public long deleteListIndex(String key, long count, Object value) {
        try {
            return redisTemplate.opsForList().remove(key, count, value);
        } catch (Exception ex) {
            throw MyExceptionUtils.mxe("删除List中的内容失败了！", ex);
        }

    }

    /**
     * 获取List缓存的数据
     *
     * @param key key值
     * @return 返回长度
     */
    public long getListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception ex) {
            throw MyExceptionUtils.mxe("获取List长度失败", ex);
        }
    }


    //----------------------set-------------------

    /**
     * 判断是否包含在Set中
     *
     * @param key
     * @param o
     */
    public void isContainsKey(String key, HashSet o) {
        redisTemplate.opsForSet().isMember(key, o);
    }

    //-----------------------lock----------------------
    /**
     * 获取分布式锁
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 单位秒
     * @param waitTimeout 单位毫秒
     * @return 是否获取成功
     */
    public boolean tryLock(String lockKey, String requestId, int expireTime,long waitTimeout) {
        // 当前时间
        long nanoTime = System.nanoTime();
        try{
            String script = "if redis.call('setNx',KEYS[1],ARGV[1]) then if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('expire',KEYS[1],ARGV[2]) else return 0 end end";
            int count = 0;
            do{
                RedisScript<String> redisScript = new DefaultRedisScript<>(script, String.class);

                Object result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey),requestId,expireTime);

                if(SUCCESS.equals(result)) {
                    return true;
                }
                //休眠500毫秒
                Thread.sleep(500L);
                count++;
            }while ((System.nanoTime() - nanoTime) < TimeUnit.MILLISECONDS.toNanos(waitTimeout));

        }catch(Exception e){
            System.out.println("尝试获取分布式锁-key[{}]异常"+lockKey);
        }

        return false;
    }


    /**
     * 释放锁
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public boolean releaseLock(String lockKey, String requestId) {

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

        RedisScript<String> redisScript = new DefaultRedisScript<>(script, String.class);

        Object result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), requestId);
        if (SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }

}
