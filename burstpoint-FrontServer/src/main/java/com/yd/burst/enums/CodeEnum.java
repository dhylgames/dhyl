package com.yd.burst.enums;

/**
 * @Description: 错误码枚举
 * @Author: Will
 * @Date: 2019-07-30 11:35
 **/
public enum  CodeEnum implements ICode{
    SUCCESS("0", "成功"),
    ERROR("1010", "错误，请联系客服"),
    EXIST_PLAYER("1011", "玩家已存在"),
    REGISTER_FAILED("1012", "注册玩家失败"),
    VERIFICATION_FAILED("1013", "验证码校验失败"),
    NOT_EXIST_PLAYER("1014", "玩家不存在"),
    ERROR_PASSWORD("1015", "玩家密码错误"),
    LOGIN_FAILED("1019", "登录失败"),
    VALIDATE_FAILED("1024","参数错误"),
    ERROR_UPDATE_PASSWORD("1015", "玩家密码错误"),
    ERROR_UPDATE_USER("1018", "修改错误"),
    ERROR_CODE("1027","token错误或者失效,请重新登入"),
    ;

    private String code;
    private String trans;

    CodeEnum(String code, String trans) {
        this.code = code;
        this.trans = trans;
    }


    public void setCode(String code) {
        this.code = code;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getTrans() {
        return trans;
    }
}
