package com.zerovoid.orange.http;

import android.app.Dialog;

import com.google.gson.Gson;
import com.zerovoid.lib.http.JSONHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务端的JSON返回值的专用处理类
 *
 * @author 绯若虚无
 */
public class OrangeDataHandler {

    // FIXME 还是改成LIST MAP的形式比较好，比LIST 好
    //FIXME 完善List<Map>和 Map 的处理

    /**
     * 服务端JSON格式，返回{"errCode":0,"errInfo":"OK","entity":{{"KEY":"VALUE"},{}
     * ...}
     *
     * @param response 服务端返回的JSON
     * @param key      Entity对象中的键名列表
     * @return Entity对象中的键值列表
     */
    public static ArrayList<String> getValueListWithEntity(JSONObject response,
                                                           String... key) {
        ArrayList<String> list = null;
        try {
            JSONObject jo = response.getJSONObject(OrangeHttpConstant.RESULT_KEY_ENTITY);
            list = new ArrayList<>();
            for (int i = 0; i < key.length; i++) {
                String result = null;
                try {
                    result = jo.getString(key[i]);
                } catch (JSONException e) {
                    /* 如果某个KEY出问题，那么就直接保存NULL */
                    e.printStackTrace();
                }
                list.add(result);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public static Map<String, Object> getValueWithEntity(JSONObject response) {
        Map<String, Object> map = new HashMap<>();
        try {
            JSONObject jo = response.getJSONObject(OrangeHttpConstant.RESULT_KEY_ENTITY);
            map = JSONHelper.jsonobjToMap(jo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 服务端标准JSON格式，返回{"errCode":0,"errInfo":"OK","entity":{{"KEY":"VALUE"},{}
     * ...} 其中Entity的内容，若Android有建立对应的Bean类，则可以使用此方法直接将JSON转为Bean类
     *
     * @param response 服务端返回的JSON
     * @param cls      JavaBean的类型
     * @return Bean类，需要强转
     */
    public static <T> Object getBeanWithEntity(JSONObject response, Class<T> cls) {
        JSONObject jo;
        Object obj = null;
        try {
            jo = response.getJSONObject(OrangeHttpConstant.RESULT_KEY_ENTITY);
            obj = new Gson().fromJson(jo.toString(), cls);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return obj;
    }

    /**
     * listKey=pageEntity
     */
    public static <T> List<T> getListFromJsonWithPageEntity(Object response,
                                                            Type type, Dialog dialog) {
        return getListFromJsonSingle(response, type,
                OrangeHttpConstant.RESULT_KEY_PAGE_ENTITY, dialog);
    }

    public static <T> List<T> getListFromJsonWithPageEntityRv(Object response,
                                                              Type type) {
        return getListFromJsonRv(response, type, null, OrangeHttpConstant.RESULT_KEY_PAGE_ENTITY,
                null);
    }

    /**
     * listKey=自定义，之前有个Entity
     */
    public static <T> List<T> getListFromJsonWithEntity(Object response,
                                                        String listKey, Type type,
                                                        Dialog dialog) {
        return getListFromJsonDouble(response, type,
                OrangeHttpConstant.RESULT_KEY_ENTITY, listKey, dialog);
    }

    /**
     * 直接listKey
     */
    public static <T> List<T> getListFromJsonSingle(Object response, Type type,
                                                    String listKey, Dialog dialog) {
        return getListFromJsonLv(response, type, null, listKey,
                dialog, null);
    }

    /**
     * listKey之前还有一层，ENTITY
     */
    public static <T> List<T> getListFromJsonDouble(Object response, Type type,
                                                    String beforeListKey, String listKey,
                                                    Dialog dialog) {
        return getListFromJsonLv(response, type, beforeListKey, listKey,
                dialog, null);
    }

    public static <T> List getListFromJsonWithKey(Object response, String key
            , Dialog dialog) {
        return getListFromJsonLv(response, null, null, OrangeHttpConstant.RESULT_KEY_PAGE_ENTITY,
                dialog, key);
    }

    public static <T> List<T> getListFromJsonRv(Object response, Type type,
                                                String beforeListKey, String listKey, String key) {
        return getListFromJson(response, type, beforeListKey, listKey, null, key);
    }

    public static <T> List<T> getListFromJsonLv(Object response, Type type,
                                                String beforeListKey, String listKey,
                                                Dialog dialog, String key) {
        return getListFromJson(response, type, beforeListKey, listKey, dialog, key);
    }

    /**
     * 将服务器返回的JSON转化为List，两层，
     *
     * @param response 服务器的HTTP响应，JSON数据，之所以用object，是希望将msg.obj直接传送过来
     * @param type     List的数据类型，如List<Comment>
     * @param dialog   进度对话框
     */
    public static <T> List<T> getListFromJson(Object response, Type type,
                                              String beforeListKey, String listKey,
                                              Dialog dialog, String key) {
        if (response == null) {
            return null;
        }
        JSONObject jo = null;
        if (response instanceof JSONObject) {
            jo = (JSONObject) response;
        } else if (response instanceof String) {
            try {
                jo = new JSONObject(response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        List<T> list = null;
        try {
            // 如果有对话框，则关闭对话框
            if (dialog != null) {
                dialog.dismiss();
            }
            // 如果在取list上层还有一层，则取出，否则直接取list
            if (beforeListKey != null && !beforeListKey.equals("")
                    && (jo != null && !jo.equals(""))) {
                jo = jo.getJSONObject(beforeListKey);
            }
            // 取list
            if (jo != null && !jo.equals("")) {
                JSONArray ja = jo.getJSONArray(listKey);
                if (type == null && key != null) {
                    list = new ArrayList<>();
                    for (int i = 0; i < ja.length(); i++) {
                        list.add((T) ja.getJSONObject(i).get(key));
                    }
                } else {
                    String json = String.valueOf(jo.getJSONArray(listKey));
                    Gson gson = new Gson();
                    if (!"null".equals(json)) {// 服务端可能返回null
                        list = gson.fromJson(json, type);
                    }
                }
                if (list == null) {
                    list = new ArrayList<>();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static <T> List<T> getListFromJson(Object response, String page,
                                              String listName,
                                              Dialog dialog) {
        if (response == null) {
            return null;
        }
        JSONObject jo = null;
        if (response instanceof JSONObject) {
            jo = (JSONObject) response;
        } else if (response instanceof String) {
            try {
                jo = new JSONObject(response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        List<T> list = null;
        try {
            // 如果有对话框，则关闭对话框
            if (dialog != null) {
                dialog.dismiss();
            }

            // 取list
            if (page != null && !page.equals("") && !jo.equals("")) {
                if (jo.get(page).toString().equals("") || jo.get(page).toString().equals("null")) {
                    return list;
                }
                String json = jo.getJSONArray(page).toString();


                if (!"null".equals(json)) {//服务端可能返回null
                    list = (List<T>) JSONHelper.jsonToMap(response.toString()).get(page);

                }
                if (list == null) {
                    list = new ArrayList<>();
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
