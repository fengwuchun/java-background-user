package com.demo.backend.user.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data

public class RegisterRequest {
    @Email(message = "邮箱格式错误")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @NotBlank(message = "密码不能为空")
    private String password;
    
    @NotBlank(message = "手机号不能为空")
    private String phone;
    
    @NotBlank(message = "用户名不能为空")
    private String name;

    @NotBlank(message = "验证码不能为空")
    private String code;

}
