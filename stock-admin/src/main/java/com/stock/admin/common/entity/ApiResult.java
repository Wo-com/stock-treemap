package com.stock.admin.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;

@Data
public class ApiResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;
    private String message;
    private T data;
    private Object param = null;

    {
        code = "0000";
        message = "SUCCESS";
    }

    public ApiResult() {
    }

    public ApiResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> ApiResult<T> success() {
        ApiResult<T> apiResult = new ApiResult();
        apiResult.data = (T) new HashMap<>(1);
        return apiResult;
    }

    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.data = (data != null) ? data : (T) new HashMap<>(1);
        return apiResult;
    }

    public static <T> ApiResult<T> success(T data, Object param) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.data = (data != null) ? data : (T) new HashMap<>(1);
        if (param != null) {
            apiResult.param = param;
        }
        return apiResult;
    }

    public static <T> ApiResult<T> error(String code, String message) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.code = code;
        apiResult.message = message;
        apiResult.data = (T) new HashMap<>(1);
        return apiResult;
    }

}
