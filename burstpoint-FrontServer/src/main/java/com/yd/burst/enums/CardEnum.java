package com.yd.burst.enums;

public enum CardEnum {
    //1"黑桃", 2"红桃", 3"梅花", 4"梅花"
    CARD_HEITAO(4,"黑桃"),
    FGF_HONGTAO(3,"红桃"),
    FGF_MEIHUA(2,"梅花"),
    FGF_FANGKUAI(1,"梅花")
    ;
    private int code;
    private String trans;
    CardEnum(int code, String trans) {
        this.code = code;
        this.trans = trans;
    }

    public static CardEnum getCardEnum(int code) {
        for (CardEnum cardEnum:CardEnum.values()){
            if(cardEnum.getCode() == code){
                return cardEnum;
            }
        }
        return  null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTrans() {
        return trans;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }
}
