package com.zerovoid.orange.http;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 网络请求业务逻辑
 * Created by zv on 2016/2/16.
 * @author zv
 * @since v1.0.0
 */
public class OrangeHttpBiz {
    /**
     * 获取总页数
     *
     * @param response
     * @return
     */
    public static int getTotalNum(JSONObject response) {
        if (response != null) {
            try {
                String total = response.getString("total");
                if (total != null) {
                    int t = Integer.parseInt(total);
                    return t;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
