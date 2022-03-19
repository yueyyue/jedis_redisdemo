package com.atguigu.jedis;

import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 * @version 1.0
 * @auther sparklight
 */
public class PhoneCode {
    public static void main(String[] args) {
       //模拟验证码发送
        verifyCode("123215424");
        //getRedisCode("123215424","13");
    }

    //1. 生成6位数字验证码
    public static String getCode() {
        // 方法1:
        Random random = new Random();
        String code = "";
        for (int i = 0; i < 6; i++) {
            int rand = random.nextInt(10);
            code += rand;
        }
        return code;
        /* 方法2:
        int random = (int)Math.floor(1000000 * Math.random());
        return random + "";*/
    }

    //2.每个手机每天只能发送三次,验证码放到redis中,设置过期时间为120s
    public static void verifyCode(String phone) {
        //连接redis
        Jedis jedis = new Jedis("192.168.6.200", 6379);

        //拼接key规则
        //手机发送次数key
        String countKey = "VerifyCode" + phone + ":count";
        //验证码key
        String codeKey = "VerifyCode" + phone + ":code";

        //每个手机每天只能发送三次
        String count = jedis.get(countKey);
        if (count == null) {
            //没有发送次数, 第一次发送设置发送次数为1
            jedis.setex(countKey, 24 * 60 * 60, "1");
        } else if (Integer.parseInt(count) < 3) {
            //发送测试+1
            jedis.incr(countKey);
        } else if (Integer.parseInt(count) >= 3) {
            //发送三次,不能再发送了
            System.out.println("超过每天发送次数");
            jedis.close();
            //超过次数之后,验证码就不再执行了
            return;
        }

        //发送验证码到redis里面
        String vcode = getCode();
        jedis.setex(codeKey, 120, vcode);

        jedis.close();
    }



    /**
     * 3 验证码校验
     * @param phone 用户的手机号
     * @param code 用户输入的验证码
     */
    public static void getRedisCode(String phone,String code) {
        //连接redis
        Jedis jedis = new Jedis("192.168.6.200", 6379);
        //验证码key
        String codeKey = "VerifyCode" + phone + ":code";
        String redisCode = jedis.get(codeKey); //存到redis中的验证码

        //判断
        if (redisCode.equals(code)) {
            System.out.println("成功");
        } else {
            System.out.println("失败");
        }

    }
}
