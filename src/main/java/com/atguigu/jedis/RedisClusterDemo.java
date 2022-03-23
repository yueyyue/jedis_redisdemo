package com.atguigu.jedis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * @version 1.0
 * @auther sparklight
 * <p>
 * <p>
 * 演示: redis集群的操作
 */
public class RedisClusterDemo {

    public static void main(String[] args) {

        //创建对象
        HostAndPort hostAndPort = new HostAndPort("192.168.6.200", 6379); //可以是集群中的任意ip 和端口
        JedisCluster jedisCluster = new JedisCluster(hostAndPort);

        //进行操作
        jedisCluster.set("b1", "value1");
        String value = jedisCluster.get("b1");

        System.out.println("value:" + value);
        jedisCluster.close();
    }
}
