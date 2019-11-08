package com.yd.burst.enums;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-07-30 21:17
 **/
public enum GameStatusEnum implements ICode {
    NORMAL("0", "正常");

    private String code;
    private String trans;

    GameStatusEnum(String code, String trans) {
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
