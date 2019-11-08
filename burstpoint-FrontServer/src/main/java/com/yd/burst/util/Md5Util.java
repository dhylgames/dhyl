package com.yd.burst.util;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * MD5加密工具
 */
public class Md5Util {

    private static final String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    public static String CHART_SET = "UTF-8";

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

    public static String encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname)) {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
            }
        } catch (Exception exception) {
        }
        return resultString;
    }

    /**
     * 将参数以 key1=value1&key2=value2的形式按照放入顺序拼装
     *
     * @param inParams
     * @param blankIsNotIn  true - 空白字段不加入 false - 空白字段也加入
     * @param replaceString blankIsNotIn=ture且该值不为空-则将inParams中不为空的字段替换为replaceString
     * @return
     */
    public static String generateSource(Map<String, Object> inParams, boolean blankIsNotIn,
                                        String replaceString) {
        StringBuilder buffer = new StringBuilder();
        for (Iterator<Map.Entry<String, Object>> keyValuePairs = inParams.entrySet().iterator(); keyValuePairs.hasNext(); ) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) keyValuePairs.next();
            String key = entry.getKey();
            String value = java.util.Objects.toString(entry.getValue());
            if (!blankIsNotIn) {
                if (StringUtils.isNotBlank(replaceString) && StringUtils.isBlank(value))
                    buffer.append(key).append("=").append(replaceString).append("&");
                else
                    buffer.append(key).append("=").append(value).append("&");
            } else if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value))
                buffer.append(key).append("=").append(value).append("&");
        }
        return StringUtils.substringBeforeLast(buffer.toString(), "&");
    }

    /**
     * 生成签名
     *
     * @param paramMap
     * @param secretName 秘钥名称
     * @param paySecret  秘钥
     * @return
     */

    public static String getSign(Map<String, Object> paramMap, String secretName, String paySecret) {
        SortedMap<String, Object> sMap = new TreeMap<String, Object>(paramMap);
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> m : sMap.entrySet()) {
            Object value = m.getValue();
            if (value != null && StringUtils.isNotBlank(String.valueOf(value))) {
                sb.append(m.getKey()).append("=").append(value).append("&");
            }
        }
        sb.delete(sb.length() - 1, sb.length());
        //生成原文
        String source = null;
        if (null != secretName && null != paySecret) {
            source = sb.append("&").append(secretName).append("=").append(paySecret).toString();
        } else {
            if (null != paySecret) {
                source = sb.append(paySecret).toString();
            } else {
                source = sb.toString();
            }
        }
        String sign = Md5Util.encode(source, Md5Util.CHART_SET);
        return sign;
    }

    /**
     * 生成签名
     *
     * @param paramMap
     * @param secretName 秘钥名称
     * @param paySecret  秘钥
     * @return
     */

    public static String getSign2(Map<String, Object> paramMap, String secretName, String paySecret) {
        SortedMap<String, Object> sMap = new TreeMap<String, Object>(paramMap);
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> m : sMap.entrySet()) {
            Object value = m.getValue();
            sb.append(m.getKey()).append("=").append(value).append("&");
        }
        sb.delete(sb.length() - 1, sb.length());
        //生成原文
        String source = null;
        if (null != secretName && null != paySecret) {
            source = sb.append("&").append(secretName).append("=").append(paySecret).toString();
        } else {
            if (null != paySecret) {
                source = sb.append(paySecret).toString();
            } else {
                source = sb.toString();
            }
        }
        String sign = Md5Util.encode(source, Md5Util.CHART_SET);
        return sign;
    }

    public static String getMD5(String inStr) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];

        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }

    // 可逆的加密算法
    public static String KL(String inStr) {
        // String s = new String(inStr);
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;
    }

    // 加密后解密
    public static String JM(String inStr) {
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String k = new String(a);
        return k;
    }

    public static void main(String[] args) {
        //支付入口md5加密格式   demo
//        String sign = Md5Util.encode("amount=100&merchantId=ooaoZ0Nj&notifyUrl=tm.3436i.cn:8080/a2p/notifyByFunPay/&orderId=0132154&returnUrl=tm.3436i.cn:8080/bulk4/changeMall&ts=48023751&userName=hxm991&0LSXZpkxNzLHAEV2S2YZ2Hy9Q7vxyHr2", "UTF-8");
//        System.out.println(sign);
//
//
//        //回调解密md5   demo
//        String retSign="15d83fed9e6e95240df94a5b48d2d727";
//        String sign1 = Md5Util.encode("forwarding=50000&channel=unionPay&currency=CNY&method=recv.create&notify_url=http://172.17.154.33:8082/pay/notifyByJHPay&order_id=eEOZcfJtu2qrOn43U61tBDbHAJVVMt&product_id=HS123456&return_url=http://fm.196722.com/user/goChangeMall&subject=商城充值&timestamp=2019-07-02 14:51:06&user_id=A1117603244&using=QUICK&version=1.0c73d4814f604514493280008972d39dcf96e4b4e", "UTF-8");
//        System.out.println("sign1======"+sign1);
//        if(sign1.equalsIgnoreCase(retSign)){
//            System.out.println("校验通过");
//        }
//        Map map = new TreeMap();
//        map.put("forwarding", "true");
//        map.put("merchant_no", "1234567890");
//        map.put("order_no", "2018103101243372060328");
//        map.put("pay_code", "UNIONPAY_PAY_PC");
//        map.put("plat_orderid", "PO2018103101243569952525");
//        map.put("url", "http://193.112.142.128:8981/pay/doAction?service=unifiedorder&pay_code=UNIONPAY_PAY_PC&merchants=test_channel_local_leon&merchant_no=1234567890&order_no=2018103101243372060328&amount=1&product_name=testing¬ify_url=http://193.112.142.128:9305/notify/doAction&client_ip=127.0.0.1&bank_name=122&sign=d9d7908225091324fab7d4b425d54e71");
//        String sign = Md5Util.getSign(map, "key", "kkGeGy4xumFc4hAmRGR4").toLowerCase();
       String retSign="YWNUMBER000007ADMINJSTXLTRJ89A567PU9067PQWYUEHSTYEJL";
        String sign1 = Md5Util.encode(retSign,"UTF-8");
        System.out.println("sign1======" + sign1);
       System.out.println("订单号长度"+sign1.length());
    }
}
