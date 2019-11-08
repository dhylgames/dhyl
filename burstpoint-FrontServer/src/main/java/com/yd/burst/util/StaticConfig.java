package com.yd.burst.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class StaticConfig {
    public static LinkedBlockingQueue<Map<String, Object>> queue = new LinkedBlockingQueue<Map<String, Object>>();//定时回调集合
    public static int promptTone = 0;
    public static Map map;

    static {
        map = new HashMap<String, String>();
    }

}
