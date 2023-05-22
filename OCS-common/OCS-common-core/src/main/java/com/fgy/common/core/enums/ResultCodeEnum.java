package com.fgy.common.core.enums;
import lombok.Getter;

/**
 * @author fgy
 * description 全局错误码
 * date 2023/4/26 13:49
 */
@Getter
public enum ResultCodeEnum {
    /**
     * 成功
     */
    SUCCESS(200,"SUCCESS"),
    /**
     * 失败
     */
    FAIL(201,"FAIL"),
    /**
     * 断言入参
     */
    ASSERT_ERROR(202,"参数校验失败"),
    /**
     * JSON转换异常
     */
    JSON_ERROR(203,"JSON转换异常"),
    /**
     * 文件流操作异常
     */
    FILE_ERROR(204,"文件流操作异常"),
    /**
     *  权限异常
     */
    PERMISSION_ERROR(205,"未授权"),

    SQL_ERROR(206,"约束sql异常"),

    REPEAT_ERROR(207,"内容重复");

    private final Integer code;
    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
