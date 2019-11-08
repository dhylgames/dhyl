package com.yd.burst.util;

public class Constants {
    public static final int MODE_KILL = 1;  // 通杀局
    public static final int MODE_COLLECT = 2;    // 普通局(收水)
    public static final int MODE_RELEASE = 3;   //放水局
    public static final int MODE_PROBABILITY = 4;   //概率局

    public static final String SESSION_KEY = "$PLAYER";     //session存储
    public static final String REDIS_PLAYER = "PLAYER_";     //session存储在redis


    public static final int CURRENT_GAME = 0;   // 当前局
    public static final int NEXT_GAME = 1;  //下一局

    public static final int BET_STATUS_MANUAL = 0;   // 注单状态：手动
    public static final int BET_STATUS_AUTO = 1;   // 注单状态：自动
    public static final int BET_STATUS_CANCEL = 2;   // 注单状态：取消


    public static final int WAIT_START = 0;   // 等待6S
    public static final int GAMING = 1;   // 游戏中
    public static final int GAMEOVER = 2;   // 爆炸
    public static final int OTHER = 3;   // 爆炸


}

