package com.yd.burst.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间加减工具类
 *
 * @Author Tian You
 * @Date 2019-06-13 20:06:42
 */
public class CalendarUtil {
    private static Logger log = LogManager.getLogger(CalendarUtil.class);
    private final static Calendar DATE_CALENDAR = Calendar.getInstance();
    private final static DateFormat ORDER_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 时间加减小时
     *
     * @param date
     * @param num
     * @return
     */
    public static String subAndAddDate(Date date, Integer num) {
        String time = null;
        try {
            DATE_CALENDAR.setTime(date);
            DATE_CALENDAR.add(Calendar.HOUR, num);
            date = DATE_CALENDAR.getTime();
            time = ORDER_TIME_FORMAT.format(date);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("异常+++++++" + e.getMessage());
        }
        return time;
    }

    public static void main(String[] args) {
        Date date = new Date();
        System.out.println("当前时间=================>" + ORDER_TIME_FORMAT.format(date));
        String time = CalendarUtil.subAndAddDate(date, -1);
        System.out.println("前一个小时的时间=================>" + time);

    }


}
