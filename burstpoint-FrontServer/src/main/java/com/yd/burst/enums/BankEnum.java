package com.yd.burst.enums;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-08-19 16:40
 **/
public enum BankEnum{

    BANK_ICBC(1, "中国工商银行"),
    BANK_CCB(2, "中国建设银行"),
    BANK_BOC(3, "中国银行"),
    BANK_ABC(4, "中国农业银行"),
    BANK_BCM(5, "中国交通银行"),
    BANK_CMB(7, "招商银行"),

    ;



    private int code;
    private String trans;

    BankEnum(int code, String trans) {
        this.code = code;
        this.trans = trans;
    }

    public int getCode() {
        return code;
    }

    public String getTrans() {
        return trans;
    }
}
