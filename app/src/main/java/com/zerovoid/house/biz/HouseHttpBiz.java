package com.zerovoid.house.biz;

import com.google.gson.reflect.TypeToken;
import com.zerovoid.house.model.HouseBean;
import com.zerovoid.lib.http.VolleyHttpUtil;
import com.zerovoid.orange.http.OrangeDataHandler;
import com.zerovoid.orange.http.OrangeErrorHandler;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2015/11/11.
 */
public class HouseHttpBiz {

    /** 获取房屋列表 */
    public static void requestHouseList(VolleyHttpUtil.ResponseCallBack callback) {
        VolleyHttpUtil.getInstance().getWithCallback(HouseURL.URL_HOUSE_LIST, callback);
    }

    public static List<HouseBean> handleHouseList(JSONObject response) {
        List<HouseBean> list = null;
        if (OrangeErrorHandler.getInstance().isSuccess(response)) {
            list = OrangeDataHandler.getListFromJsonWithPageEntity(response, new TypeToken<List<HouseBean>>() {
            }.getType(), null);
        }
        return list;
    }
}
