package com.yd.burst.common;

import com.yd.burst.enums.CodeEnum;
import com.yd.burst.enums.ICode;

import java.io.Serializable;

/**
 * @Description: 返回对象
 * @Author: Will
 * @Date: 2019-07-29 20:05
 **/
public class Result implements Serializable {


    private static final long serialVersionUID = 4184468922813488911L;

    /**
     * 失败消息
     */
    private String msg;

    /**
     * 返回代码
     */
    private String code;

    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();

    /**
     * 返回的数据
     */
    private Object data;

    public static Result success() {
        Result result = new Result();
        result.setCode(CodeEnum.SUCCESS.getCode());
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setCode(CodeEnum.SUCCESS.getCode());
        result.setData(data);
        return result;
    }


    public static Result fail(ICode code) {
        Result result = new Result();
        result.setCode(code.getCode());
        result.setMsg(code.getTrans());
        return result;
    }


    public String getMsg() {
        return msg;
    }

    public Result setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Result setCode(String code) {
        this.code = code;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Result setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }
}
