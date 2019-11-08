package com.yd.burst.util;

import java.util.HashMap;
import java.util.Map;

public class ReturnUtil extends HashMap<String, Object> {

    public ReturnUtil() {

    }

    public static ReturnUtil success() {
        return error(0, "操作成功");
    }
    public static ReturnUtil success(int code, String msg) {
        ReturnUtil m = new ReturnUtil();
        m.put("code", code);
        m.put("msg", msg);
        return m;
    }
    public static ReturnUtil error() {
        return error(1, "操作失败");
    }


    public static ReturnUtil error(String msg) {
        return error(500, msg);
    }


    public static ReturnUtil error(int code, String msg) {
        ReturnUtil m = new ReturnUtil();
        m.put("code", code);
        m.put("msg", msg);
        return m;
    }
    public static ReturnUtil success(int code, String msg, Object obj, String sign, long ts) {
        ReturnUtil m = new ReturnUtil();
        m.put("code", code);
        m.put("msg", msg);
        m.put("sign", sign);
        m.put("ts", ts);
        m.put("data", obj);
        return m;
    }


    public static ReturnUtil ok(String msg) {
        ReturnUtil m = new ReturnUtil();
        m.put("msg", msg);
        return m;
    }


    public static ReturnUtil ok(Map<String, Object> map) {
        ReturnUtil m = new ReturnUtil();
        m.putAll(map);
        return m;
    }


    public static ReturnUtil ok() {
        return new ReturnUtil();
    }

    public ReturnUtil put(String key, Object value) {
        super.put(key, value);
        return this;
    }
    public static ReturnUtil successObj(Object obj, Long countMin, String msg) {
        ReturnUtil m = new ReturnUtil();
        m.put("code", 0);
        m.put("msg", msg);
        m.put("data",obj);
        m.put("countMin",countMin);
        return m;
    }

    public static ReturnUtil successObj(Object obj, String ret) {
        ReturnUtil m = new ReturnUtil();
        m.put("ret", ret);
        m.put("data",obj);
        return m;
    }

    public static ReturnUtil successEasy(Object obj, String ret, int total, String zj) {
        ReturnUtil m = new ReturnUtil();
        m.put("ret", ret);
        m.put("rows",obj);
        m.put("total",total);
        m.put("zj",zj);
        return m;
    }

    public static ReturnUtil successDate(Object obj, String date) {
        ReturnUtil m = new ReturnUtil();
        m.put("code", 0);
        m.put("msg", "");
        m.put("data",obj);
        m.put("date",date);
        return m;
    }

    public static ReturnUtil inpen(int index, String msg) {
        ReturnUtil m = new ReturnUtil();
        m.put("data", index);
        m.put("msg", msg);
        return m;
    }

}
