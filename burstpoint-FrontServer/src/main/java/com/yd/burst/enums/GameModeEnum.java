package com.yd.burst.enums;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-08-08 11:24
 **/
public enum GameModeEnum implements ICode {
    MODE_KILL("1", "通杀模式"),
    MODE_NORMAL("2", "普通模式"),
    MODE_RELEASE("3", "放水模式");

    private String code;
    private String trans;

    GameModeEnum(String code, String trans) {
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
