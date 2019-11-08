package com.yd.burst.enums;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-07-30 11:51
 **/
public enum UserStatusEnum implements ICode{
    NORMAL("0", "正常"),
    ACCOUNT_FREEZE("1", "账号冻结"),
    AMOUNT_FREEZE("2", "余额冻结"),
    STOP("3", "账号停用"),

    GROUP_MANAGER("1", "群主"),
    USER("2", "普通用户");
    private String code;
    private String trans;

    UserStatusEnum(String code, String trans) {
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
