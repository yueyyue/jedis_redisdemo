package com.atguigu.jedis;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @version 1.0
 * @auther sparklight
 * 注意: redis配置文件中需要改两个地方
 * 1.   protectmode = no
 * 2.   bind 注释掉
 * 3. 使用完记得关闭连接
 */
public class JedisDemo1 {
    public static void main(String[] args) {
        //创建Jedis 对象
        Jedis jedis = new Jedis("192.168.6.200",6379);

        //测试
        String ping = jedis.ping();
        System.out.println(ping);
        jedis.close();
    }

    //操作key,String
    @Test
    public void test01(){
        Jedis jedis = new Jedis("192.168.6.200", 6379);

        //添加数据
        jedis.set("name", "lucy");

        //获取
        String regStr = jedis.get("name");
        System.out.println(regStr);
        boolean name = jedis.exists("name");
        jedis.expire("name",40);
        System.out.println(name);

        //设置多个key-value
        jedis.mset("k1", "v1", "k2", "v2");
        List<String> mget = jedis.mget("k1", "k2");
        System.out.println(mget);

        System.out.println("-----------------------");
        Set<String> keys = jedis.keys("*");
        for(String key : keys) {
            System.out.println(key);
        }
    }

    //操作list
    @Test
    public void test02() {
        Jedis jedis = new Jedis("192.168.6.200", 6379);

        jedis.lpush("key1","lucy","mary","jack");
        List<String> key = jedis.lrange("key1", 0, -1);
        System.out.println(key);
    }

    //操作set
    @Test
    public void test03() {
        Jedis jedis = new Jedis("192.168.6.200", 6379);

        jedis.sadd("name", "lucy", "jack");
        Set<String> names = jedis.smembers("name");
        System.out.println(names);
    }

    //操作hash
    @Test
    public void test04() {
        Jedis jedis = new Jedis("192.168.6.200", 6379);
        jedis.hset("users","age","20");
        String hget = jedis.hget("users", "age");
        System.out.println(hget);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("address","beijing");
        map.put("age","32");
        map.put("id","007");
        jedis.hmset("users2",map);
        List<String> users2 = jedis.hmget("users2","age","id");
        System.out.println("user2" + users2);

    }

    //操作zset
    @Test
    public void test05() {
        Jedis jedis = new Jedis("192.168.6.200", 6379);
        jedis.zadd("china",100d,"shanghai");
        List<String> china = jedis.zrange("china", 0, -1);
        System.out.println(china);
    }


}
