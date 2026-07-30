package com.demo.backend.common.result;

public class HttpResult<T> {
    private Integer code;
    private String message;
    private T data;

    public HttpResult() {
    }

    public HttpResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> HttpResult<T> success(T data) {
        return new HttpResult<>(200, "success", data);
    }

    public static <T> HttpResult<T> error(String message) {
        return new HttpResult<>(500, message, null);
    }

     public static <T> HttpResult<T> error(Integer code, String message) {
        return new HttpResult<>(code, message, null);
    }
      public static <T> HttpResult<T> error(Integer code,String message, T data) {
        return new HttpResult<>(code, message, data);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
