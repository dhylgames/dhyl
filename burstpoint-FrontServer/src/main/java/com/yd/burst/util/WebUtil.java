package com.yd.burst.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-07-30 16:16
 **/
public class WebUtil {

    public static boolean checkVerifyCode(HttpServletRequest request, String code) {
        String verifyCodeExpected = (String) request.getSession().getAttribute(
                com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        if (!code.equals(verifyCodeExpected)) {
            return false;
        }
        return true;
    }
}
