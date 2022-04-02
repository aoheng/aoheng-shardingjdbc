package com.snowalker.shardingjdbc.snowalker.demo.common.api;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: api结果
 * @author: Aoheng
 * @date: 2022/4/2 16:52
 */
public class ApiResult<T> implements Serializable {
    private static final long serialVersionUID = 8004487252556526569L;
    private int code;
    private String message;
    private T data;

    public ApiResult() {
    }

    public static ApiResult<Object> result(boolean flag) {
        return flag ? ok() : fail();
    }

    public static ApiResult<Object> result(ApiCode apiCode) {
        return result(apiCode, (Object) null);
    }

    public static <T> ApiResult<T> result(ApiCode apiCode, T data) {
        return result(apiCode, (String) null, data);
    }

    public static <T> ApiResult<T> result(ApiCode apiCode, String message, T data) {
        String apiMessage = apiCode.getMessage();
        if (StringUtils.isNotBlank(apiMessage)) {
            message = apiMessage;
        }

        return (ApiResult<T>) builder().code(apiCode.getCode()).message(message).data(data).build();
    }

    public static ApiResult<Object> ok() {
        return ok((Object) null);
    }

    public static <T> ApiResult<T> ok(T data) {
        return result(ApiCode.SUCCESS, data);
    }

    public static <T> ApiResult<T> ok(T data, String message) {
        return result(ApiCode.SUCCESS, message, data);
    }

    public static ApiResult<Map<String, Object>> okMap(String key, Object value) {
        Map<String, Object> map = new HashMap(1);
        map.put(key, value);
        return ok(map);
    }

    public static ApiResult<Object> fail(ApiCode apiCode) {
        return result(apiCode, (Object) null);
    }

    public static ApiResult<Object> fail(String message) {
        return result(ApiCode.FAIL, message, (Object) null);
    }

    public static <T> ApiResult<T> fail(ApiCode apiCode, T data) {
        if (ApiCode.SUCCESS == apiCode) {
            throw new RuntimeException("失败结果状态码不能为" + ApiCode.SUCCESS.getCode());
        } else {
            return result(apiCode, data);
        }
    }

    public static ApiResult<String> fail(Integer errorCode, String message) {
        return (new ApiResult()).setCode(errorCode).setMessage(message);
    }

    public static ApiResult<Map<String, Object>> fail(String key, Object value) {
        Map<String, Object> map = new HashMap(1);
        map.put(key, value);
        return result(ApiCode.FAIL, map);
    }

    public static ApiResult<Object> fail() {
        return fail(ApiCode.FAIL);
    }

    public static <T> ApiResult.ApiResultBuilder<T> builder() {
        return new ApiResult.ApiResultBuilder();
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

    public ApiResult<T> setCode(final int code) {
        this.code = code;
        return this;
    }

    public ApiResult<T> setMessage(final String message) {
        this.message = message;
        return this;
    }

    public ApiResult<T> setData(final T data) {
        this.data = data;
        return this;
    }


    protected boolean canEqual(final Object other) {
        return other instanceof ApiResult;
    }


    public ApiResult(final int code, final String message, final T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static class ApiResultBuilder<T> {
        private int code;
        private String message;
        private T data;

        ApiResultBuilder() {
        }

        public ApiResult.ApiResultBuilder<T> code(final int code) {
            this.code = code;
            return this;
        }

        public ApiResult.ApiResultBuilder<T> message(final String message) {
            this.message = message;
            return this;
        }

        public ApiResult.ApiResultBuilder<T> data(final T data) {
            this.data = data;
            return this;
        }

        public ApiResult<T> build() {
            return new ApiResult(this.code, this.message, this.data);
        }

    }
}
