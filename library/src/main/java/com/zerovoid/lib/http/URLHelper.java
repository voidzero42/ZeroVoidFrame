package com.zerovoid.lib.http;


import com.zerovoid.lib.util.StringUtil;

/**
 * URL帮助类
 * https://github.com/joefei
 * Created by zv on 2015/12/16.
 */
public class URLHelper {

    /**
     * @param uri 请求URL
     * @return
     */
    public static boolean isContainHttp(String uri) {
        if (uri != null && (uri.toUpperCase().contains("HTTP") || uri.toUpperCase().contains("HTTPS"))) {
            return true;
        }
        return false;
    }

    /**
     * @param serverURL  服务器地址
     * @param requestURL 请求地址
     * @return
     */
    public static String getURL(String serverURL, String requestURL) {
        /*如果URI不包含HTTP，则默认表示没有加服务器地址*/
        if (!isContainHttp(requestURL)) {
            if (StringUtil.isBlank(serverURL)) {/*服务器地址为空是一种异常和错误，不允许存在*/
                throw new RuntimeException("服务器地址为空，请设置服务器地址...");
            }
            if (!isContainHttp(serverURL)) {
                throw new RuntimeException("服务器地址不是HTTP或HTTPS开头，请检查...");
            }
            requestURL = serverURL + requestURL;/*如果服务器地址不为空，则拼接URL*/
        }
        return requestURL;
    }
}
