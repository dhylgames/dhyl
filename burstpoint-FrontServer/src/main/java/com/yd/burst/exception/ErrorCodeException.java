package com.yd.burst.exception;

import com.yd.burst.enums.CodeEnum;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-07-30 11:45
 **/
public class ErrorCodeException extends RuntimeException {

    private String code;

    private String msg;

    public ErrorCodeException(CodeEnum codeEnum) {
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getTrans();
    }

    public ErrorCodeException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
