package com.itrip.utils;

import redis.clients.jedis.Jedis;

public class RedisUtil {

    public static String get(String key) {
        Jedis jedis = RedisPool.getJedis();
        String value = jedis.get(key);
        RedisPool.returnResource(jedis);
        return value;
    }

    public static String set(String key, String value) {
        Jedis jedis = RedisPool.getJedis();
        String result = jedis.set(key, value);
        RedisPool.returnResource(jedis);
        return result;
    }

    public static String set(String key, int seconds, String value) {
        Jedis jedis = RedisPool.getJedis();
        String result = jedis.setex(key, seconds, value);
        RedisPool.returnResource(jedis);
        return result;
    }

    public static boolean exists(String key) {
        Jedis jedis = RedisPool.getJedis();
        Boolean result = jedis.exists(key);
        RedisPool.returnResource(jedis);
        return result;
    }

    public static long ttl(String key) {
        Jedis jedis = RedisPool.getJedis();
        Long result = jedis.ttl(key);
        RedisPool.returnResource(jedis);
        return result;
    }

    public static long del(String key) {
        Jedis jedis = RedisPool.getJedis();
        Long result = jedis.del(key);
        RedisPool.returnResource(jedis);
        return result;
    }

    public static void main(String[] args) {
        String result = RedisUtil.set("name",60, "marry");
        System.out.println(result);

        result = RedisUtil.set("name",90,"peter");
        System.out.println(result);

        String name = RedisUtil.get("name");
        System.out.println(name);

        long flag = RedisUtil.del("name");
        System.out.println(flag);

        name = RedisUtil.get("name");
        System.out.println(name);
    }
}
