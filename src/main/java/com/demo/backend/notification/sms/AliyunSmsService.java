package com.demo.backend.notification.sms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.DefaultCredentialProvider;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dypnsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dypnsapi20170525.models.SendSmsVerifyCodeRequest;
import com.aliyun.sdk.service.dypnsapi20170525.models.SendSmsVerifyCodeResponse;
import com.google.gson.Gson;

import darabonba.core.client.ClientOverrideConfiguration;

import java.util.concurrent.CompletableFuture;

@Service
public class AliyunSmsService implements SmsService {

    @Value("${aliyun.sms.access-key-id}")
    private String accessKeyId;

    @Value("${aliyun.sms.access-key-secret}")
    private String accessKeySecret;

    @Value("${aliyun.sms.region}")
    private String region;

    @Value("${aliyun.sms.sign-name}")
    private String signName;

    @Value("${aliyun.sms.template-code}")
    private String templateCode;

    @Override
    public void send(String phone, String code) {
        aliyunSendSms(phone, code);
    }

   private void aliyunSendSms(String phone, String code) {
           // 调用阿里云发送短信
        Credential credential = Credential.builder()
                .accessKeyId(accessKeyId)
                .accessKeySecret(accessKeySecret)
                .build();
       StaticCredentialProvider provider = StaticCredentialProvider.create(credential);
        
        try (AsyncClient client = AsyncClient.builder()
                .region("cn-shenzhen")
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride("dypnsapi.aliyuncs.com")
                )
                .build()) {

            SendSmsVerifyCodeRequest sendSmsRequest = SendSmsVerifyCodeRequest.builder()
                    .signName("速通互联验证码")
                    .templateCode("100001")
                    .templateParam("{\"code\":\"" + code + "\",\"min\":\"20\"}")
                    .phoneNumber(phone)
                    .build();

            CompletableFuture<SendSmsVerifyCodeResponse> response = client.sendSmsVerifyCode(sendSmsRequest);
            try {
                SendSmsVerifyCodeResponse resp = response.get();
                System.out.println(new Gson().toJson(resp));
            } catch (Exception e) {
                System.err.println("发送短信失败: " + e.getMessage());
                throw new RuntimeException("短信发送失败", e);
            }

        }
    }

}
