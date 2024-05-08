package com.mhx.encryption.dto;

import com.mhx.encryption.common.ErrorCodeEnum;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @className ResponseVO 
 * @description 全局渲染对象 
 * @author MuHongXin. 
 * @date 2023/05/31 15:20
 * @version v1.0.0
**/

public class ResponseVO<T> implements Serializable {
    /**
     * 响应码
     * Http code
     */
    private String code;

    /**
     * 消息
     */
    private String message;

    /**
     * 响应数据
     * Response Body
     */
    private T data;

    public ResponseVO() {
        this(null, null, null);
    }

    public ResponseVO(T data) {
        this(data, null, null);
    }

    public ResponseVO(ErrorCodeEnum code) {
        this(null, code.getCode(), code.getMsg());
    }

    public ResponseVO(String code, String message) {
        this(null, code, message);
    }

    public ResponseVO(T data, ErrorCodeEnum code) {
        this(data, code.getCode(), code.getMsg());
    }

    public ResponseVO(T data, String code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public static ResponseVO ok() {
        return new ResponseVO(null, String.valueOf(HttpStatus.OK.value()), HttpStatus.OK.getReasonPhrase());
    }

    public static <T> ResponseVO ok(T data) {
        return new ResponseVO(data, String.valueOf(HttpStatus.OK.value()), HttpStatus.OK.getReasonPhrase());
    }

    public static ResponseVO failed() {
        return new ResponseVO(null, ErrorCodeEnum.FAIL.getCode(), ErrorCodeEnum.FAIL.getMsg());
    }
}
