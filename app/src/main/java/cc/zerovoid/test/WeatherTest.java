package cc.zerovoid.test;

import com.zerovoid.lib.http.VolleyHttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/1/12.
 */
public class WeatherTest {
    /** 从中国天气网获得福州今日的温度和天气信息，在首页显示 */
    public void requestWeather() {
        String url = "http://m.weather.com.cn/data/101230101.html";
        VolleyHttpUtil.getInstance().getWithCallback(url, new VolleyHttpUtil.ResponseCallBack() {
            @Override
            public void handleResponse(JSONObject response, int errCode) {
                if (response == null) {
                    return;
                }
                try {
                    String s = response.getString("weatherinfo");
                    JSONObject jo1 = new JSONObject(s);
                    String temp1 = jo1.getString("temp1");
                    String weather1 = jo1.getString("weather1");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
