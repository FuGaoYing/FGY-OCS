package com.fgy.common.security.exception;

import com.fgy.common.core.enums.ResultCodeEnum;
import com.fgy.common.core.exceprion.BaseException;
import com.fgy.common.core.result.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fgy
 * description 统一异常拦截
 * date 2023/4/26 13:49
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    /**
     *  捕获断言
     * @param e IllegalArgumentException
     * @return 触发断言响应
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public CommonResult<String> exception(IllegalArgumentException e){
        log.error("IllegalArgumentException {}",e.getMessage());
        return CommonResult.fail(ResultCodeEnum.ASSERT_ERROR.getCode(), ResultCodeEnum.ASSERT_ERROR.getMessage(),e.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public CommonResult<String> exception(MissingServletRequestParameterException e){
        log.error("MissingServletRequestParameterException {},参数 {} 类型 {}",e.getMessage(),e.getParameterName(),e.getParameterType());
        return CommonResult.fail(ResultCodeEnum.ASSERT_ERROR.getCode(), ResultCodeEnum.ASSERT_ERROR.getMessage(),"参数" + e.getParameterName() + "不能为空");
    }


    /**
     * 请求内容过大
     * @param e 异常
     * @return 统一响应
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public CommonResult<String> exception(MaxUploadSizeExceededException  e){
        log.error("MaxUploadSizeExceededException {}",e.getMessage());
        return CommonResult.fail(ResultCodeEnum.FILE_ERROR.getCode(), ResultCodeEnum.FILE_ERROR.getMessage(),"文件过大");
    }



    /**
     * 参数校验异常
     * @param e BindException
     * @return 统一响应
     */
    @ExceptionHandler(BindException.class)
    public CommonResult<List<String>> exception(BindException e) {
        log.error("BindException {}", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        // 获取所有校验失败的参数异常
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<String> list = new ArrayList<>();
        allErrors.forEach(error -> list.add(error.getDefaultMessage()));
        return CommonResult.fail(ResultCodeEnum.ASSERT_ERROR.getCode(),ResultCodeEnum.ASSERT_ERROR.getMessage(),list);
    }

    @ExceptionHandler(BaseException.class)
    public CommonResult<String> exception(BaseException e){
        log.error("BaseException {}",e.getMessage());
        return CommonResult.fail(e.getCode(), e.getMessage());
    }



    /**
     * 兜底，防止发生未知异常
     * @param e 异常
     * @return 统一返回
     */
    @ExceptionHandler(Exception.class)
    public CommonResult<String> exception(Exception e){
        log.error("RuntimeException ",e);
        // 因sql约束导致异常
        if (e instanceof SQLIntegrityConstraintViolationException || e instanceof DuplicateKeyException) {
            return CommonResult.fail(ResultCodeEnum.SQL_ERROR.getCode(), ResultCodeEnum.SQL_ERROR.getMessage(), e.getCause().getMessage());
        }
        return CommonResult.fail(ResultCodeEnum.FAIL.getCode(),ResultCodeEnum.FAIL.getMessage());
    }


}
