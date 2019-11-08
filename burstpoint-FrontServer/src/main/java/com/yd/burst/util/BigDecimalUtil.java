package com.yd.burst.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-08-06 18:57
 **/
public class BigDecimalUtil {


    private static Pattern NUMBER_PATTERN = Pattern.compile("-?[0-9]+(\\.[0-9]+)?");

    public static boolean isNumeric(String str) {
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;
        }
        Matcher isNum = NUMBER_PATTERN.matcher(bigStr);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}
