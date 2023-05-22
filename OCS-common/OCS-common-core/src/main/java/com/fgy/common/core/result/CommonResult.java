package com.fgy.common.core.result;

import com.fgy.common.core.enums.ResultCodeEnum;
import lombok.Data;

/**
 * @author fgy
 * description 统一返回
 * date 2023/4/26 13:49
 */
@Data
public class CommonResult<T> {

    private Integer code;

    private String message;

    private T data;

    public CommonResult(){}

    private static <T> CommonResult<T> build(Integer code,String message,T data) {
        CommonResult<T> result = new CommonResult<T>();
        result.setCode(code);
        result.setData(data);
        result.setMessage(message);
        return result;
    }

    public static<T> CommonResult<T> ok(T data) {
        return build(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(),data);
    }

    public static<T> CommonResult<T> ok() {
        return build(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(),null);
    }

    public static<T> CommonResult<T> ok(Integer code,String message,T data) {
        return build(code,message,data);
    }

    public static <T> CommonResult<T> fail(Integer code,String message,T data) {
        return build(code,message,data);
    }

    public static <T> CommonResult<T> fail(Integer code,String message) {
        return build(code,message,null);
    }

    public static <T> CommonResult<T> fail() {
        return build(ResultCodeEnum.FAIL.getCode(),ResultCodeEnum.FAIL.getMessage(),null);
    }

}
