package com.yd.burst.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 日期转换成字符串
     *
     * @param date
     * @return str
     */
    public static String dateToStr(Date date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }


    /**
     * 获取客户端IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }


//    public static List<UserModel> getDaShen(IUserService service) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:");
//        Calendar startTime = Calendar.getInstance();
//        Calendar endTime = Calendar.getInstance();
//        Calendar nowTime = Calendar.getInstance();
//        nowTime.add(Calendar.HOUR, 1);
//        startTime.setTime(new Date(119, nowTime.get(Calendar.MONTH), nowTime.get(Calendar.DAY_OF_MONTH) + 1, 9, 00));
//        endTime.setTime(new Date(119, nowTime.get(Calendar.MONTH), nowTime.get(Calendar.DAY_OF_MONTH) + 2, 02, 00));
//        Random r = new Random();
//        int index = 5;
//        List<UserModel> listDate = service.getDaShenLast();
//        List<UserModel> list = new ArrayList<UserModel>();
//        if (listDate.size() > 0) {
//            Date priorDate = DateUtil.StrToDate(listDate.get(0).getTime());
//            Calendar temTime = Calendar.getInstance();
//            temTime.setTime(priorDate);
//            if (temTime.before(startTime)) {
//                for (int i = 0; i <= 204; i++) {
//                    int a = r.nextInt(10) + 0;
//                    int b = r.nextInt(10) + 0;
//                    int c = r.nextInt(10) + 0;
//                    int num = 10 + (int) (Math.random() * (59 - 10 + 1));
//                    int e = a + b + c;//结果
//                    /*String tt=a+" + "+b+" + "+c+" = "+e;*/
//                    String tt = a + " + " + b + " + ";
//                    String time = sdf.format(startTime.getTime()) + num;
//                    if (!endTime.before(startTime)) {
//                        UserModel hfdto = new UserModel();
//                        hfdto.setTime(time);
//                        hfdto.setKj(tt);
//                        hfdto.setResult(e + "");
//                        hfdto.setThree(c);
//                        startTime.add(Calendar.MINUTE, index);
//                        if (e % 2 == 0) {
//                            hfdto.setMsg("0");
//                        } else {
//                            hfdto.setMsg("1");
//                        }
//                        list.add(hfdto);
//                    }
//                }
//            }
//        }
//        return list;
//    }

    public static String getSj() {
        String[] str = {"小单", "大单", "小双", "大双", "小单"};
        int random = (int) (Math.random() * 5);
        return str[random];
    }

    public static int getPage() {
        int[] str = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50};
        int random = (int) (Math.random() * 50);
        return str[random];
    }

  /*  public static String getMd5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }*/


    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 解析
     *
     * @param hexString
     * @return
     */
    public static String hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return new String(d);
    }

    /**
     * 将指定byte数组以16进制的形式打印到控制台
     *
     * @param b
     */
    public static void printHexString(byte[] b) {
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            System.out.print(hex.toUpperCase());
        }

    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789abcdef".indexOf(c);
    }

    /**
     * 加密
     *
     * @param str
     * @return
     */
    public static String encode(String str) {
        String strDigest = "";
        try {
            // 此 MessageDigest 类为应用程序提供信息摘要算法的功能，必须用try,catch捕获
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            byte[] data;
            data = md5.digest(str.getBytes("utf-8"));// 转换为MD5码
            strDigest = bytesToHexString(data);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return strDigest;
    }


}
