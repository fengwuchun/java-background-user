package com.demo.backend.user.captcha;

import org.springframework.stereotype.Service;

import com.demo.backend.notification.sms.SmsService;
import com.demo.backend.user.enums.CaptchaType;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import java.util.Random;


@Service
public class CaptchaServiceImpl implements CaptchaService {

    private final RedisTemplate<String, String> redisTemplate;

    private final SmsService smsService;

    public CaptchaServiceImpl(
            RedisTemplate<String, String> redisTemplate,
            SmsService smsService) {
        this.redisTemplate = redisTemplate;
        this.smsService = smsService;
    }

    @Override
    public void sendCode(
            CaptchaType type,
            String phone) {

        // 1.生成验证码
       String code = String.format("%06d", new Random().nextInt(1_000_000));

        String key = "captcha:"
                + type.getPrefix()
                + phone;

        // 3.保存Redis

        redisTemplate.opsForValue()
                .set(
                        key,
                        code,
                        5,
                        TimeUnit.MINUTES);
      System.out.println("保存的email:"+phone);                  
      System.out.println("保存的验证码"+code);
      System.out.println("保存的key:"+key);
      System.out.println("保存的手机号:"+phone);

        // 4.发送短信

        smsService.send(
                phone,
                code);

    };

    @Override
    public boolean verify(
            CaptchaType type,
            String phone,
            String code) {

        String key = "captcha:"
                + type.getPrefix()
                + phone;

        String redisCode = redisTemplate.opsForValue()
                .get(key);
         System.out.println("取出用的key:"+key);       
         System.out.println("取出的code:"+redisCode); 
         System.out.println("输入的code:"+code);      

        if (code.equals(redisCode)) {

            // 验证成功删除

            redisTemplate.delete(key);

            return true;
        }

        return false;
    }

}
