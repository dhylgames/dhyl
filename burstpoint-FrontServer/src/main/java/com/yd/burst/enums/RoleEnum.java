package com.yd.burst.enums;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-08-01 19:27
 **/
public enum RoleEnum implements ICode {

    PLAY("0", "正常玩家"),
    NPC("1", "机器人")

     ;

    private String code;
    private String trans;

    RoleEnum(String code, String trans) {
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
