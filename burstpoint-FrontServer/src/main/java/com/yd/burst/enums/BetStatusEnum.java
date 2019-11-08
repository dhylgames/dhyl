package com.yd.burst.enums;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-07-31 16:02
 **/
public enum BetStatusEnum implements ICode {

    MANUAL("0", "手动"),
    AUTO("1", "自动"),
    CANCEL("2", "取消")

    ;

    private String code;
    private String trans;

    BetStatusEnum(String code, String trans) {
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
