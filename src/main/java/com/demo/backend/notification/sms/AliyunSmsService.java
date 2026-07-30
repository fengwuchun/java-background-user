package com.demo.backend.notification.sms;

import org.springframework.stereotype.Service;

@Service
public class AliyunSmsService implements SmsService{
   
    @Override
    public  void send(String phone, String code){

        //调用阿里云发送短信

    }

}
