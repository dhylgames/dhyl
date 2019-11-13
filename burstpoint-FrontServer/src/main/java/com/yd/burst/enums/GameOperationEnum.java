package com.yd.burst.enums;

/**
 * @Author kelly
 * @Desc 定义游戏的玩法
 *   0-牛牛 1-金花
 */
public enum GameOperationEnum {
    //牛牛
    CATTLE_READY(01, "准备"),
    CATTLE_GRAB_BANKER(02, "抢庄"),
    CATTLE_DEAL(03, "发牌"),
    CATTLE_SHOW(04, "亮牌"),
    CATTLE_CALC_SCORE(05, "计算分"),

    //炸金花
    FGF_READY(11,"准备"),
    FGF_DEAL(12,"发牌"),
    FGF_FOLLOW_BET(13,"跟注"),
    FGF_SEE_CARD(14,"看牌"),
    FGF_COMPARE_CARD(15,"比牌"),
    FGF_DISCARD(16,"弃牌"),
    FGF_CALC_SCORE(17,"计算分"),
    ;


    private int code;
    private String trans;

    GameOperationEnum(int code, String trans) {
        this.code = code;
        this.trans = trans;
    }

    public static GameOperationEnum getGameOperationEnum(int code) {
        for (GameOperationEnum gameOperationEnum:GameOperationEnum.values()){
            if(gameOperationEnum.getCode() == code){
                return gameOperationEnum;
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
