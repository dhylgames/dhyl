package com.yd.burst.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharArrayBuffer;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import org.apache.http.HttpResponse;



public class HttpUtil {

    public static String CHART_SET = "UTF-8";
    /**
     * 设置连接超时时间，单位毫秒
     */
    private static int connectTimeout = 10000;
    /**
     * 设置从connectManager获取Connection超时时间，单位毫秒
     */
    private static int connectionRequestTimeout = 10000;
    /**
     * 请求获取数据的超时时间，单位毫秒
     */
    private static int setSocketTimeout = 10000;
    private static ConnectionKeepAliveStrategy myStrategy = null;
    private static CloseableHttpClient httpclient = HttpClientBuilder.create().setMaxConnTotal(1000).setMaxConnPerRoute(15)
            .setKeepAliveStrategy(myStrategy).build();

    static {
        myStrategy = new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator("Keep-Alive"));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if ((value != null) && ("timeout".equalsIgnoreCase(param))) {
                        return Long.parseLong(value) * 1000L;
                    }
                }
                return connectTimeout;
            }
        };
    }

    /**
     * @描述:http发送报文请求
     * @时间:2017年9月11日 下午3:43:40
     */
    public static String baseHttpSendPost(String url, String reqParam, String charset) throws SocketTimeoutException, Exception {
        CloseableHttpResponse response = null;
        HttpPost httpPost = null;
        String respContent = null;
        try {
            System.out.println("Posturl = " + url);
            System.out.println("reqParam = " + reqParam);
            httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().
                    setConnectTimeout(connectTimeout).
                    setConnectionRequestTimeout(connectionRequestTimeout).
                    setSocketTimeout(setSocketTimeout).build();
            httpPost.setConfig(requestConfig);
            StringEntity postParams = new StringEntity(reqParam, charset);
            postParams.setContentEncoding(charset);
            postParams.setContentType("application/json");
            httpPost.setEntity(postParams);
            response = httpclient.execute(httpPost);

            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity he = response.getEntity();
                respContent = EntityUtils.toString(he, charset);
            } else {
                throw new SocketTimeoutException("基础网络链接非200异常{}" + url);
            }
            return respContent;
        } catch (Exception e) {
            System.out.println("基础网络请求异常{" + url + "},参数{" + reqParam + "}");
            e.printStackTrace();
            return respContent;
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpPost != null) {
                    httpPost.releaseConnection();
                }
            } catch (Exception e) {
                System.out.println("释放网络资源异常！");
                e.printStackTrace();
            }
        }
    }

    /**
     * 回调接收数据
     *
     * @param request
     * @return
     */
    public static Map<String, String> getRequestMap(HttpServletRequest request) {
        Map<String, String> requestMap = new HashMap<String, String>();
        try {
            request.setCharacterEncoding("utf-8");
            Map parameterMap = request.getParameterMap();
            System.out.println("parameterMap = " + parameterMap.toString());
            if (null != parameterMap && parameterMap.size() > 0) {
                for (Object objkey : parameterMap.keySet()) {
                    String key = (String) objkey;
                    Object objvalue = parameterMap.get(key);
                    if (objvalue instanceof String || objvalue instanceof Long || objvalue instanceof Integer) {
                        requestMap.put(key, objvalue.toString());
                    }
                    if (objvalue instanceof String[]) {
                        String[] arrylist = (String[]) objvalue;
                        requestMap.put(key, arrylist[0]);
                    }
                }
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), CHART_SET));
            StringBuffer sb = new StringBuffer("");
            String temp = null;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
            br.close();
            String params = sb.toString();
            System.out.println("params = " + params);
            if (StringUtils.isEmpty(params)) {
                return requestMap;
            }
            params = params.substring(1, params.length() - 1);
            String param[] = params.split(",");
            for (String content : param) {
                if (content.indexOf("=") > 0) {
                    String key = content.substring(0, content.indexOf("="));
                    String value = content.substring(content.indexOf("=") + 1, content.length());
                    requestMap.put(StringUtils.deleteWhitespace(key).replace("\"", ""), StringUtils.deleteWhitespace(value).replace("\"", ""));
                } else if (content.indexOf(":") > 0) {
                    String key = content.substring(0, content.indexOf(":"));
                    String value = content.substring(content.indexOf(":") + 1, content.length());
                    requestMap.put(StringUtils.deleteWhitespace(key).replace("\"", ""), StringUtils.deleteWhitespace(value).replace("\"", ""));
                }
            }
            System.out.println("requestMap = " + requestMap.toString());
        } catch (Exception e) {
            System.out.println("回调接收数据异常！");
            e.printStackTrace();
        }
        return requestMap;
    }

    public static String appendPrefix(String url) {
        if (StringUtils.isNotBlank(url)) {
            String lowerCaseUrl = StringUtils.lowerCase(url);
            if (lowerCaseUrl.startsWith("http://") || lowerCaseUrl.startsWith("https://")) {
                return url;
            } else {
                return "http://" + url;
            }
        }
        return url;
    }
    /**
     * 发送post请求，参数用map接收
     * @param url 地址
     * @param map 参数
     * @return 返回值
     */
    public static String postMap(String url,Map<String,String> map) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for(Map.Entry<String,String> entry : map.entrySet())
        {
            pairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
        }
        CloseableHttpResponse response = null;
        try {
            post.setEntity(new UrlEncodedFormEntity(pairs,"UTF-8"));
            response = httpClient.execute(post);
            if(response != null && response.getStatusLine().getStatusCode() == 200)
            {
                HttpEntity entity = response.getEntity();
                result = entityToString(entity);
            }
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
                if(response != null)
                {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    private static String entityToString(HttpEntity entity) throws IOException {
        String result = null;
        if(entity != null)
        {
            long lenth = entity.getContentLength();
            if(lenth != -1 && lenth < 2048)
            {
                result = EntityUtils.toString(entity,"UTF-8");
            }else {
                InputStreamReader reader1 = new InputStreamReader(entity.getContent(), "UTF-8");
                CharArrayBuffer buffer = new CharArrayBuffer(2048);
                char[] tmp = new char[1024];
                int l;
                while((l = reader1.read(tmp)) != -1) {
                    buffer.append(tmp, 0, l);
                }
                result = buffer.toString();
            }
        }
        return result;
    }

    /**
     * post请求，参数为json字符串
     * @param url 请求地址
     * @param par 加密的参数
     * @return 响应
     */
    public static String postPar(String url,String par)
    {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            post.setEntity(new StringEntity(par));
            response = httpClient.execute(post);
            System.out.println(response.getStatusLine().getStatusCode());
            if(response != null && response.getStatusLine().getStatusCode() == 200)
            {
                HttpEntity entity = response.getEntity();
                result = entityToString(entity);
                return result;
            }
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
                if(response != null)
                {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
