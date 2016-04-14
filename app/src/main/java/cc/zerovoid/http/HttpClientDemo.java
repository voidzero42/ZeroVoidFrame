package cc.zerovoid.http;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * HttpClient's demo
 * <p/>
 * Created by ZeroVoid on 2016/1/15.
 */
public class HttpClientDemo {
    private static final int TIMEOUT_CONNECTION = 30000;
    private static final int RETRY_TIME = 1000;

    public static void test(String url, String token, String body) {
        int time = 0;
        HttpPost httpPost = null;
        do {
            try {
//        HttpGet httpGet = new HttpGet(url);

                httpPost = new HttpPost(url);
                httpPost.addHeader("charset", "utf-8");
                httpPost.addHeader("Content-Type", "application/json");
                httpPost.addHeader("AccessToken", token);
                StringEntity s = null;
                s = new StringEntity(body, "utf-8");
                s.setContentType("application/json");
                httpPost.setEntity(s);


                HttpClient httpClient = new DefaultHttpClient();
                httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIMEOUT_CONNECTION);//连接时间
                httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, TIMEOUT_CONNECTION);//数据传输时间
                HttpResponse httpResponse = httpClient.execute(httpPost);
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    String result = EntityUtils.toString(httpResponse.getEntity());
            /* Log.i(TAG, result);*/
                }
            } catch (IOException e) {
                time++;
                if (time < RETRY_TIME) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    continue;
                }
                e.printStackTrace();
            } finally {
                // 释放连接
                // httpPost.releaseConnection();
                if (httpPost != null) {
                    httpPost.abort();
                }
            }
        } while (time < RETRY_TIME);
    }
}
