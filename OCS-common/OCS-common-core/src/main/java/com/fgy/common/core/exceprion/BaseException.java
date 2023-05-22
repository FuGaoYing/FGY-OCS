package com.fgy.common.core.exceprion;

import lombok.Getter;

/**
 * @author fgy
 * description
 * date 2023/5/22 13:09
 */
@Getter
public abstract class BaseException extends Exception {
    private Integer code;
    private String message;

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Integer code,String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
