package com.mhx.encryption.common;

/**
 * @className AsyncConfiguration
 * @Description:    错误编码枚举类
 *                  Error encoding enumeration class
 * @author MuHongXin.
 * @date 2023/05/31 15:20
 * @version v1.0.0
 **/

public enum ErrorCodeEnum {
    /*************** 响应结果编码 | Response result coding ***************/
    SUCCESS("200", "操作成功"),
    SUCCESS_ACCEPT("202","已接收请求，尚未处理"),
    FAIL("400", "错误的请求"),
    SIGN_EXCEPTION("401", "身份认证失败"),
    RESOURCE_IS_NOT_AVAILABLE("403","该资源尚未开放"),
    METHOD_DISABLE("405","禁止请求接口"),
    TIMEOUT("408","请求处理超时"),
    SYSTEM_ERROR("500", "系统错误"),
    SERVER_DISABLE("503","服务不可用"),
    FAILED("500", "操作失败"),
    ;

    private String code;
    private String msg;

    private ErrorCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
