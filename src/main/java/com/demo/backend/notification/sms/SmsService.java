 package com.demo.backend.notification.sms;

public interface SmsService {

    void send(String phone, String code);
} 