package com.yd.burst.aop;

import com.yd.burst.common.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.groovy.syntax.TokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ValidationException;

/**
 * @Description: 全局异常处理
 * @Author: Will
 * @Date: 2019-07-31 20:32
 **/
@ControllerAdvice
@ResponseBody
public class ExceptionAspect {
    private static Logger logger = LogManager.getLogger(ExceptionAspect.class);
    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        Result result = new Result();
        result.setCode("400");
        result.setMsg("Bad Request");
        logger.error("Could not read json...", e);
        return result;
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Result handleValidationException(MethodArgumentNotValidException e) {
        Result result = new Result();
        result.setCode("400");
        result.setMsg("参数检验异常！");
        logger.error("参数检验异常！", e);
        return result;
    }

    /**
     * 405 - Method Not Allowed。HttpRequestMethodNotSupportedException
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        Result result = new Result();
        result.setCode("405");
        result.setMsg("请求方法不支持！");
        logger.error("请求方法不支持！", e);
        return result;
    }

    /**
     * 415 - Unsupported Media Type。HttpMediaTypeNotSupportedException
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public Result handleHttpMediaTypeNotSupportedException(Exception e) {
        Result result = new Result();
        result.setCode("415");
        result.setMsg("内容类型不支持！");
        logger.error("内容类型不支持！", e);
        return result;
    }

    /**
     * 401 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TokenException.class)
    public Result handleTokenException(Exception e) {
        Result result = new Result();
        result.setCode("401");
        result.setMsg("Token已失效");
        logger.error("Token已失效", e);
        return result;
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        Result result = new Result();
        result.setCode("500");
        result.setMsg("内部服务器错误！");
        logger.error("内部服务器错误！", e);
        return result;
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public Result handleValidationException(ValidationException e) {
        Result result = new Result();
        result.setCode("400");
        result.setMsg("参数验证失败！");
        logger.error("参数验证失败！", e);
        return result;
    }
}
