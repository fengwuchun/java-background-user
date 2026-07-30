package com.demo.backend.common.constant;

public class ResultCode {

       // 成功
    public static final Integer SUCCESS = 200;

    // 客户端错误
    public static final Integer PARAM_ERROR = 400;

    // 未登录
    public static final Integer UNAUTHORIZED = 401;

    // 权限不足
    public static final Integer FORBIDDEN = 403;

    // 资源不存在
    public static final Integer NOT_FOUND = 404;


    // 数据冲突，例如邮箱重复
    public static final Integer CONFLICT = 409;


    // 服务器异常
    public static final Integer SERVER_ERROR = 500;

    


}
