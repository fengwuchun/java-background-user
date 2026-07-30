package com.demo.backend.user.captcha;

import com.demo.backend.user.enums.CaptchaType;


public interface CaptchaService {

      /**
     * 发送验证码
     */
     void sendCode(
        CaptchaType type,
         String phone
        );

        /**
     * 校验验证码
     */
    boolean verify(
            CaptchaType type,
            String phone,
            String code
    );
    
    
}
