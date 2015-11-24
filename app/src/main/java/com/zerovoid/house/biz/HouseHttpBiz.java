package com.zerovoid.house.biz;

import com.google.gson.reflect.TypeToken;
import com.zerovoid.common.biz.RequestErrorHandler;
import com.zerovoid.house.model.HouseBean;
import com.zerovoid.http.RequestDataHandler;
import com.zerovoid.http.VollyHelperNew;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2015/11/11.
 */
public class HouseHttpBiz extends RequestErrorHandler {

    /** 获取房屋列表 */
    public static void requestHouseList(VollyHelperNew.ResponseCallBack callback) {
        VollyHelperNew.getInstance().sendRequestGetWithCallback(HouseURL.URL_HOUSE_LIST, callback);
    }

    public static List<HouseBean> handleHouseList(JSONObject response) {
        List<HouseBean> list = null;
        if (isSuccess(response)) {
            list = RequestDataHandler.getListFromJsonWithPageEntity(response, new TypeToken<List<HouseBean>>() {
            }.getType(), null, null);
        }
        return list;
    }
}
