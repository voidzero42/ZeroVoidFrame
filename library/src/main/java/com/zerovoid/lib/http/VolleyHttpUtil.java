package com.zerovoid.lib.http;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zerovoid.lib.net.NetworkUtil;
import com.zerovoid.lib.util.StringUtil;
import com.zerovoid.orange.http.OrangeHttpConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * HTTP请求工具类
 */
public class VolleyHttpUtil {
    protected static final String TAG = VolleyHttpUtil.class.getSimpleName();
    private static VolleyHttpUtil httpUtil;
    private Context context;
    private RequestQueue mQueue;
    private boolean isDebug = false;
    public static final String CANCLE_TAG = "CLEAR_ALL";
    public static final String ERR_CODE_NO_NET = "100";
    /**
     * 超时、拒绝，服务器异常已经全返回99了
     */
    public static final String ERR_CODE_NET_EXCEPTION = "101";
    private HashMap<String, String> mHeader;

    public void initVolleyHttpUtil(Context context) {
        this.context = context;
        this.mQueue = Volley.newRequestQueue(context);
    }

    public static VolleyHttpUtil getInstance() {
        if (httpUtil == null) {
            httpUtil = new VolleyHttpUtil();
        }
        return httpUtil;
    }

    private String serverURL = "";

    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    /** 将Header作为一个参数传入，而不是写死在这边实现解耦 */
    public void setHeader(HashMap<String, String> header) {
        this.mHeader = header;
    }

    /**
     * 取消全部未发送的请求
     */
    public void cancelAll() {
        mQueue.cancelAll(CANCLE_TAG);
    }

    /**
     * 取消特定标签的请求
     */
    public void cancel(String tag) {
        mQueue.cancelAll(tag);
    }

    public void enableDebug() {
        this.isDebug = true;
    }

    public void setDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    /**********************
     * 使用Handler处理HTTP响应
     ************************/

    public RequestQueue getWithHandler(String url, final Handler handler,
                                       final int what) {
        return getWithHandler(url, handler, what, null);
    }

    public RequestQueue getWithHandler(String url, final Handler handler,
                                       final int what, HashMap<String, String> urlMap) {
        return request(url, Method.GET, urlMap, null, null, null, handler, what, null);
    }

    /**
     * @param url     请求地址
     * @param map     请求参数列表
     * @param handler
     * @param what
     * @author WuGefei
     */
    public RequestQueue postWithHandler(String url,
                                        HashMap<String, Object> map, final Handler handler, final int what) {
        return request(url, Method.POST, null, map, null, null, handler, what, null);
    }


    /** ####################### 使用Callback类处理HTTP响应 ####################### */

    /**
     * 不处理HTTP响应
     */
    public RequestQueue get(String url) {
        return requestOnly(url, Method.GET, null);
    }

    public RequestQueue getWithCallback(String url,
                                        final ResponseCallBack responseCallBack) {
        return getWithCallback(url, null, responseCallBack);
    }

    /**
     * 带参数的GET请求
     */
    public RequestQueue getWithCallback(String url, HashMap<String, String> urlMap,
                                        final ResponseCallBack responseCallBack) {
        return getWithCallback(url, urlMap, responseCallBack, null);
    }

    public RequestQueue getWithCallback(String url,
                                        final ResponseCallBack responseCallBack, String tag) {
        return getWithCallback(url, null, responseCallBack, tag);
    }

    /**
     * 带标签
     */
    public RequestQueue getWithCallback(String url, HashMap<String, String> urlMap,
                                        final ResponseCallBack responseCallBack,
                                        String tag) {


        /*如果GET带map，说明只是拼接在"？"后面的，而不是包体*/
        return requestWithCallback(url, Method.GET, urlMap, null, responseCallBack, tag);
    }

    public RequestQueue postWithCallback(String url, HashMap<String, Object> map, final ResponseCallBack callBack) {
        return requestWithCallBack(url, Method.POST, map, callBack);
    }

    public RequestQueue put(String url, HashMap<String, Object> jsonMap) {
        return putWithCallback(url, jsonMap, null);
    }

    public RequestQueue putWithCallback(String url, HashMap<String, Object> jsonMap,
                                        final ResponseCallBack responseCallBack) {
        return requestWithCallBack(url, Method.PUT, jsonMap, responseCallBack);
    }

    public RequestQueue putWithCallback(String url, JSONObject jo,
                                        final ResponseCallBack responseCallBack) {
        return request(url, Method.PUT, null, null, jo, responseCallBack, null, -1, null);
    }

    /**
     * 只发送请求，不管返回值
     *
     * @param url
     * @param method
     * @param urlMap
     * @return
     */
    public RequestQueue requestOnly(String url, int method,
                                    HashMap<String, Object> urlMap) {
        return requestWithCallBack(url, method, urlMap, null);
    }

    public RequestQueue requestWithCallBack(String url, int method,
                                            HashMap<String, Object> jsonMap, final ResponseCallBack responseCallBack
    ) {
        return requestWithCallback(url, method, null, jsonMap, responseCallBack
                , null);
    }


    public RequestQueue requestWithCallback(String url, int method,
                                            HashMap<String, String> urlMap, HashMap<String, Object> jsonMap, final ResponseCallBack responseCallBack,
                                            String tag) {
        return request(url, method, urlMap, jsonMap, null, responseCallBack, null, -1, tag);
    }


    /** ####################### 默认使用JSONObject请求，回调可使用Handler或者Callback ####################### */

    /**
     * @param url              请求地址
     * @param method           请求方式，get/post/put/delete
     * @param jsonMap          请求参数，将被封装为JSONObject
     * @param jsonObject       请求参数，和jsonMap一样
     * @param responseCallBack 处理HTTP响应的回调类
     * @param handler          Handler，和callback一样的作用，
     * @param what             Handler的WHAT
     * @param tag              请求的标签，用于取消请求
     * @return 请求队列
     */
    public RequestQueue request(String url, int method,
                                HashMap<String, String> urlMap, HashMap<String, Object> jsonMap, JSONObject jsonObject, final ResponseCallBack responseCallBack, final Handler handler, final int what, String tag) {
        if (isDebug) {
            Log.e(TAG, "url=" + URLHelper.getURL(serverURL, url));
        }
        if (!isConnected(responseCallBack, handler, what)) {
            return mQueue;
        }
        Request request = getJsonObjectRequest(URLHelper.getURL(serverURL, url), method, urlMap, jsonMap, jsonObject, responseCallBack, handler, what);
        return addRequestToQueue(request, tag);
    }

    private boolean isConnected(ResponseCallBack responseCallBack, Handler handler, int what) {
        if (!NetworkUtil.isConnectedNet(context)) {
            JSONObject jo = getCustomErrCode(ERR_CODE_NO_NET);
            if (responseCallBack != null) {
                responseCallBack.handleResponse(jo, Integer.parseInt(ERR_CODE_NO_NET));
            } else if (handler != null) {
                handler.obtainMessage(what, jo).sendToTarget();
            }
            return false;
        }
        return true;
    }

    private JSONObject getCustomErrCode(String code) {
        JSONObject jo = new JSONObject();
        try {
            jo.put(OrangeHttpConstant.RESULT_KEY, code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jo;
    }


    /**********************
     * JSONObject请求
     ************************/

    private JsonObjectRequest getJsonObjectRequest(String url, int method, HashMap<String, String> urlMap, final HashMap<String, Object> jsonMap, JSONObject jsonObject, final ResponseCallBack responseCallBack, final Handler handler, final int what) {
        JSONObject jo = new JSONObject();
        if (method != Method.GET && method != Method.DELETE && jsonMap != null) {
            String jsons = JSONHelper.Map2Json(jsonMap);
            try {
                jo = new JSONObject(jsons);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject != null) {
            jo = jsonObject;
        }
        if (urlMap != null) {
            url = replaceUrlParam(url, urlMap);
        }
        JsonObjectRequest json = new JsonObjectRequest(method, URLHelper.getURL(serverURL, url), jo,
                new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        /*之所以将ErrorHandler分离出去，因为错误处理经常变动，且无论如何，在请求后都需要调用错误处理器来处理*/
                        if (isDebug) {
                            Log.e(VolleyHttpUtil.class.getName(), "onResponse="
                                    + response.toString());
                        }
                        int errCode = response.optInt(OrangeHttpConstant.RESULT_KEY, -1);
                        if (responseCallBack != null) {
                            responseCallBack.handleResponse(response, errCode);
                        } else if (handler != null) {
                            handler.obtainMessage(what, response).sendToTarget();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (isDebug) {
                    Log.e(VolleyHttpUtil.class.getName(), "Volley onErrorResponse = " + error.getMessage());
                }
                JSONObject jo = getCustomErrCode(ERR_CODE_NET_EXCEPTION);
                if (responseCallBack != null) {
                    responseCallBack.handleResponse(jo, Integer.parseInt(ERR_CODE_NET_EXCEPTION));
                }
                if (handler != null) {
                    handler.obtainMessage(what, jo).sendToTarget();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (mHeader == null) {
                    mHeader = new HashMap<>();
                }
                return mHeader;
            }
        };
        return json;
    }

    /**********************
     * StringRequest请求
     ************************/

    public RequestQueue stringRequest(String url, int method,
                                      Map<String, String> map, String request, final ResponseCallBack responseCallBack, String tag) {
        if (isDebug) {
            Log.e(TAG, "url=" + URLHelper.getURL(serverURL, url));
        }
        if (!StringUtil.isContainBlank(request)) {
            StringRequest stringRequest = getStringRequest(URLHelper.getURL(serverURL, url), method, map, responseCallBack);
            mQueue.add(stringRequest);
        }
        return mQueue;
    }

    private StringRequest getStringRequest(String url, int method, final Map<String, String> map, final ResponseCallBack responseCallBack) {
        StringRequest stringRequest = new StringRequest(method, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (isDebug && response != null) {
                            Log.e(VolleyHttpUtil.class.getName(), "onResponse="
                                    + response);
                        }
                        int errCode = -1;
                        if (jsonObject != null) {
                            jsonObject.optInt(OrangeHttpConstant.RESULT_KEY, -1);
                        }
                        if (responseCallBack != null) {
                            responseCallBack.handleResponse(jsonObject, errCode);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (isDebug) {
                    Log.e(VolleyHttpUtil.class.getName(), "Volley onErrorResponse = " + error.getMessage());
                }
                if (responseCallBack != null) {
                    responseCallBack.handleResponse(null, -1);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return map;
            }
        };

        return stringRequest;
    }

    /**********************
     * 处理
     ************************/

    private RequestQueue addRequestToQueue(Request request, String tag) {
        request.setRetryPolicy(new DefaultRetryPolicy(60000, 0, 1f) {

            @Override
            public int getCurrentRetryCount() {
                /*
                 * Volley默认尝试两次，MAX=1,count=0;count<=MAX;count++;count=2时，
				 * 表示当前已经重复请求两次，就不会再第三次重复请求，从而屏蔽掉Volley的自动重复请求功能；
				 */
                return 2;
            }

        });
        if (tag == null) {
            tag = CANCLE_TAG;
        }
        request.setTag(tag);
        mQueue.add(request);
        return mQueue;
    }


    private String replaceUrlParam(String url, Map<String, String> map) {
        if (map != null) {
            StringBuilder sb = null;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (entry != null && entry.getKey() != null && entry.getValue() != null) {
                    String key = entry.getKey();
                    /*URL的UTF-8转码*/
                    String value = StringUtil.encode(entry.getValue());
                    if (!key.contains("{") || !key.contains("}")) {/*如果没有{}，直接加尾部，先判断是因为带“{}”的参数极少，提高效率*/
                        if (sb == null) {
                            sb = new StringBuilder();
                        }
                        sb.append("&");
                        sb.append(key);
                        sb.append("=");
                        sb.append(value);
                    } else {/*带{}的参数，直接替换*/
                        url = url.replace(key, value);
                    }
                } else {
                    Log.e(TAG, "HTTP请求参数有null值");
                }
            }
            if (sb != null && sb.length() > 0) {
                String params = sb.replace(0, 1, "?").toString();
                url += params;
            }
        }
        return url;
    }


    /**********************
     * ResponseCallBack
     ************************/

    public interface ResponseCallBack {

        /**
         * 整个JSON对象
         * 处理後的数据
         */
        void handleResponse(JSONObject response, int errCode);
    }

}
