package com.zerovoid.lib.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/9.
 */
public class FastJsonTools {
    /**
     * 用fastjson 将jsonString 解析成 List<Map<String,Object>>
     * @param jsonString
     * @return
     */
    public static List<Map<String,Object>> getListMap(String jsonString){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        try {
            JSONObject jsonObject = JSON.parseObject(jsonString);
            list = JSON.parseObject(jsonObject.getString("pageEntity"), new TypeReference<List<Map<String, Object>>>() {
            });
        }catch (Exception e){
        }
        return list;
    }

    /**
     * 用fastjson 将json字符串解析为一个 JavaBean
     * @param jsonString
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T getBean(String jsonString,Class<T> cls){
        T t = null;
        try{
            JSONObject jsonObject = JSON.parseObject(jsonString);
           t = JSON.parseObject(jsonObject.getString("entity"),cls);
        }catch (Exception e){
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 用fastjson 将json字符串 解析成为一个 List<JavaBean> 及 List<String>
     * @param jsonString
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> getBeans(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            list = JSON.parseArray(jsonString, cls);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }
}
