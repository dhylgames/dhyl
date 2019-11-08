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
    ERROR_CREATE_GAME("1016", "创建游戏错误"),
    BET_FAILED("1017", "下注失败"),
    ESCAPE_FAILED("1018", "逃跑失败"),
    LOGIN_FAILED("1019", "登录失败"),
    NONE_LOGIN("1020", "用户未登录"),
    JWT_SECRET("1021","XX#$%()(#*!()!KL<><MQLMNQNQJQK sdfkjsdrow32234545df>?N<:{LWPW_hisen"),
    JWT_PAYLOAD("1022","payload"),
    JWT_MAXAGE("1023","3600000"),
    VALIDATE_FAILED("1024","参数错误"),
    MODE_NORMAL("1025","普通模式"),
    MODE_KILL("1026","通杀模式"),
    MODE_RELEASE("1027","放水模式"),
    BALANCE_LESS("1028","可用余额不足"),
    CURRENT_BET_FAILED("1029","当前局已下注"),
    AUTO_ESCAPED("1030","已逃跑"),
    WITHDRAY_SUCCESS("1031","提现申请已提交，请等待"),
    NO_WITHDRAY_AMOUNT("1032","提现金额未设置"),
    WITHDRAY_FAILED("1033","可提现金额不足"),
    WITHDRAY_ERROR("1034","提现失败，请重试"),



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
