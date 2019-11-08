package com.yd.burst.util;


import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;
import java.util.TreeMap;


public class RSASignature { 

	public static final String DEFAULT_CHARSET = "UTF-8";
	/** 
    * 签名算法 
    */  
   public static final String SIGN_ALGORITHMS = "SHA1WithRSA";  
 
   /** 
   * RSA签名 
   * @param content 待签名数据 
   * @param privateKey 商户私钥 
   * @return 签名值 
   */  
   public static String sign(String content, String privateKey)  
   {  
       try   
       {  
    	   System.out.println("待签名字符串：" + content);
           PKCS8EncodedKeySpec priPKCS8    = new PKCS8EncodedKeySpec( org.apache.commons.codec.binary.Base64.decodeBase64(privateKey) );
             
           KeyFactory keyf                 = KeyFactory.getInstance("RSA");  
           PrivateKey priKey               = keyf.generatePrivate(priPKCS8);  
 
           java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);  
 
           signature.initSign(priKey);  
           signature.update( content.getBytes(DEFAULT_CHARSET));  
 
           byte[] signed = signature.sign();  
             
           return org.apache.commons.codec.binary.Base64.encodeBase64String(signed);
       }  
       catch (Exception e)   
       {  
           e.printStackTrace();  
       }  
         
       return null;  
   }  
   
	/**
	 * 签名
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String sign(TreeMap<String,String> params, String privateKey) throws Exception{
		String content = getSignContent(params);
		String sign = sign(content,privateKey);
		return sign;
	}
	
	public static String getSignContent(TreeMap<String,String> params){
		if(params.containsKey("signMsg"))//签名明文组装不包含sign字段和signType
			params.remove("signMsg");
		if(params.containsKey("signType"))//签名明文组装不包含sign字段和signType
			params.remove("signType");
		
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<String, String> entry:params.entrySet()){
			if(entry.getValue()!=null&&entry.getValue().length()>0){
				sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
		}
		if(sb.length()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		//String sign = md5(sb.toString().getBytes("UTF-8"));//记得是md5编码的加签
		return sb.toString();
	}
   
	  public static boolean doCheck(TreeMap<String,String> params, String sign, String publicKey)  {
		  String content = getSignContent(params);
		  return doCheck(content, sign, publicKey);
	  }
	
   /** 
    * RSA验签名检查 
    * @param content 待签名数据 
    * @param sign 签名值 
    * @param publicKey 分配给开发商公钥 
    * @return 布尔值 
    */  
    public static boolean doCheck(String content, String sign, String publicKey)  
    {  
        try   
        {  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            byte[] encodedKey = org.apache.commons.codec.binary.Base64.decodeBase64(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));  
  
          
            java.security.Signature signature = java.security.Signature  
            .getInstance(SIGN_ALGORITHMS);  
          
            signature.initVerify(pubKey);  
            signature.update( content.getBytes(DEFAULT_CHARSET) );  
          
            boolean bverify = signature.verify( org.apache.commons.codec.binary.Base64.decodeBase64(sign) );
            return bverify;  
              
        }   
        catch (Exception e)   
        {  
            e.printStackTrace();  
        }  
          
        return false;  
    }  
    
    public static void main(String[] args) {
    	String inputCharset = "1";//1-utf-8
		String partnerId = "20180208457001";//我们提供的商户号
		String cus_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDL/yIw36Ux62D6ck3tFsiWFt/pu+8lqdh8bIoH+h+zLhmMsNDLpoqgo/2yOULymYcZxHA9E1TgPgu2rbxv+v5gQoA4xWIiJHEP9DVgFcsk/+gv99IyDaiexyOHxc3/oPkd64VnCfMjjNxP9ZREBOuzREUow2PLToapZrbgwWgrHQIDAQAB";
		String cus_private_key="MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMv/IjDfpTHrYPpyTe0WyJYW3+m77yWp2Hxsigf6H7MuGYyw0MumiqCj/bI5QvKZhxnEcD0TVOA+C7atvG/6/mBCgDjFYiIkcQ/0NWAVyyT/6C/30jINqJ7HI4fFzf+g+R3rhWcJ8yOM3E/1lEQE67NERSjDY8tOhqlmtuDBaCsdAgMBAAECgYEAv4PEPqwIM+huAFJFlHtaT4YkKxRz/SEKjC1+HOUW06pI9EufikHNTekHqUWW85ltO6SvVreKbIfziUpsaZjzLA8c5JmcRY4g9w3aRcVbV8HcP0sE4/Rib/JGhCopgwcGnG2iwjyw258Y6TUZlhVecMMy5osxxk3hU+Q81H0XfSUCQQD5r8iLAJ0RY6uYQe9ftL2410YtD9nMHCiDeZ2B2XZwdaBUUDsam5+BH2EH0qWpbQvSUpJkXtg535qZCsOg1vLvAkEA0SeZeMKCzh1LMyHjea5QFrjVSnCqKOYL1kiJ5RlmSnUjYC5AtkOSaLZlPYX9YJCGKRCRHI7V5f/UM2EkVHCSswJBAOZyZi5c75qoGizZ1hvIDj72eW+HrKXk60OFUGkTE2xyM/r9Xb+OGKYtFvoIYhvAaGPDEBgRLZIknWRY+fuNyAMCQHMi1nRIt1MZgyURubRpRcNMWnXREYrUIJ4EboyEb+/7Dc9LhuoOxpEIHzFAClxXEtOWQBu1cYBcVYc3KZWmJssCQQCzUvY8vSwnwKb07CaDixScPbjRWyFMX3eJLO7I3rpQdqO2+R8ujREvy8GAvYlSYkgyD1/lUbXT7p1EWbdVmgxL";
		String signType = "1";
		String notifyUrl = "http://localhost:8080/notify";
		String returnUrl = "http://localhost:8080/returns";
		String orderNo = String.valueOf(System.currentTimeMillis());
		String orderAmount = "1";//1分
		String orderCurrency = "156";//人民币
		String orderDatetime = "2018-03-22 08:08:00";
		String signMsg = "";//签名信息
		String payMode = "1";//1：微信，2：支付宝，5：QQ
		String isPhone = "0";
		String subject = "测试订单名称"+orderNo;//
		String body = "测试订单内容";
		
	
		TreeMap<String,String> params = new TreeMap<String,String>();
		params.put("inputCharset", inputCharset);
		params.put("partnerId", partnerId);
		params.put("signType", signType);
		params.put("notifyUrl", notifyUrl);
		params.put("returnUrl", returnUrl);
		params.put("orderNo", orderNo);
		params.put("orderAmount", orderAmount);
		params.put("orderCurrency", orderCurrency);
		params.put("orderDatetime", orderDatetime);
		params.put("signMsg", signMsg);
		params.put("payMode", payMode);
		params.put("isPhone", isPhone);
		params.put("subject", subject);
		params.put("body", body);
		
		try {
			signMsg = sign(params,cus_private_key);
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		System.out.println("签名：signMsg="+signMsg);
		
		boolean check = doCheck(params, signMsg, cus_public_key);
		System.out.println("验证签名：结果="+check);
	}

	public static String signData(Map<String, String> nvps, String path) throws Exception {
		TreeMap<String, String> tempMap = new TreeMap<String, String>(nvps);
		StringBuffer buf = new StringBuffer();
		for (String key : tempMap.keySet()) {
			buf.append(key).append("=").append((String) tempMap.get(key)).append("&");
		}
		String signatureStr = buf.substring(0, buf.length() - 1);
		String signData = signByPrivate(signatureStr, path, "UTF-8");
		System.out.println("请求数据：" + signatureStr);
		System.out.println("请求数据：" + "&signature=" + signData);
		return signData;
	}
	public static String signByPrivate(String content, String privateKey,
									   String input_charset) throws Exception {
		if (privateKey == null) {
			throw new Exception("加密私钥为空, 请设置");
		}
		PrivateKey privateKeyInfo = getPrivateKey(privateKey);
		return signByPrivate(content, privateKeyInfo, input_charset);
	}

	/**
	 * 得到私钥
	 *
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes = buildPKCS8Key(key);

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

		return privateKey;
	}

	public static String signByPrivate(String content, PrivateKey privateKey,
									   String input_charset) throws Exception {
		if (privateKey == null) {
			throw new Exception("加密私钥为空, 请设置");
		}
		java.security.Signature signature = java.security.Signature
				.getInstance(SIGN_ALGORITHMS);
		signature.initSign(privateKey);
		signature.update(content.getBytes(input_charset));
		return Base64.encode(signature.sign());
	}

	private static byte[] buildPKCS8Key(String privateKey) throws IOException {
		if (privateKey.contains("-----BEGIN PRIVATE KEY-----")) {
			return Base64.decode(privateKey.replaceAll("-----\\w+ PRIVATE KEY-----", ""));
		} else if (privateKey.contains("-----BEGIN RSA PRIVATE KEY-----")) {
			final byte[] innerKey = Base64.decode(privateKey.replaceAll("-----\\w+ RSA PRIVATE KEY-----", ""));
			final byte[] result = new byte[innerKey.length + 26];
			System.arraycopy(Base64.decode("MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKY="), 0, result, 0, 26);
			System.arraycopy(BigInteger.valueOf(result.length - 4).toByteArray(), 0, result, 2, 2);
			System.arraycopy(BigInteger.valueOf(innerKey.length).toByteArray(), 0, result, 24, 2);
			System.arraycopy(innerKey, 0, result, 26, innerKey.length);
			return result;
		} else {
			return Base64.decode(privateKey);
		}
	}

	/**
	 * RSA验签名检查
	 *
	 * @param content
	 *            待签名数据
	 * @param sign
	 *            签名值
	 * @param publicKey
	 *            支付宝公钥
	 * @param input_charset
	 *            编码格式
	 * @return 布尔值
	 */
	public static boolean verify(String content, String sign,
								 String publicKey, String input_charset) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = Base64.decode(publicKey);
			PublicKey pubKey = keyFactory
					.generatePublic(new X509EncodedKeySpec(encodedKey));
			return verify(content, sign, pubKey, input_charset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean verify(String content,String sign,PublicKey publicKey,String inputCharset){
		try {
			java.security.Signature signature = java.security.Signature
					.getInstance(SIGN_ALGORITHMS);
			signature.initVerify(publicKey);
			signature.update(content.getBytes(inputCharset));
			boolean bverify = signature.verify(Base64.decode(sign));
			return bverify;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}

