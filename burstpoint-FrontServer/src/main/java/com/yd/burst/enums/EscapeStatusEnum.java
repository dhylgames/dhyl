package com.yd.burst.enums;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-07-31 16:45
 **/
public enum EscapeStatusEnum implements ICode{

    SUCCESS("0", "逃跑成功"),
    FAILED("1", "逃跑失败")

    ;

    private String code;
    private String trans;

    EscapeStatusEnum(String code, String trans) {
        this.code = code;
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
