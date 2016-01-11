package com.zerovoid.orange.http;

import android.content.Context;
import android.content.Intent;

import com.zerovoid.lib.http.VolleyHttpUtil;
import com.zerovoid.lib.interfazz.IExitApp;
import com.zerovoid.lib.util.ToastHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 通用HTTP处理逻辑
 * Created by wgf on 2015/9/17.
 */
public class OrangeErrorHandler {

    private static OrangeErrorHandler biz;
    private Context mContext;
    private boolean isDebug;

    private OrangeErrorHandler() {

    }

    /**
     * 单例
     */
    public static OrangeErrorHandler getInstance() {
        if (biz == null) {
            biz = new OrangeErrorHandler();
        }
        return biz;
    }

    private void enableDebug() {
        isDebug = true;
    }

    private void setDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    public void initOrangeHttpBiz(Context context) {
        this.mContext = context;
    }

    // TODO 1、网络问题 2、服务器问题 3、
    // java.net.ConnectException: failed to connect to /10.168.7.1 (port 8080)
    // after 60000ms: isConnected failed: ECONNREFUSED (Connection refused)
    // TIMEOUT 连接超时

    // TODO 将VolleyHelper的内容移植到此之后，根本不需要errCode，需要作出整改；


    /**
     * 使用非Volley，没有返回JSONObject，直接处理JSON
     */
    public boolean isSuccess(String json) {
        if (json != null) {
            JSONObject response = null;
            try {
                response = new JSONObject(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return isSuccess(response);
        }
        return false;
    }


    /**
     * 默认只显示98的错误信息，无自定义消息
     */
    public boolean isSuccess(Object response) {
        int resultCode = getHttpErrorCode(response, true, null, null);
        if (resultCode == OrangeHttpConstant.RESULT_SUCCESS) {
            return true;
        }
        return false;
    }

    /**
     * 显示自定义的错误信息
     *
     * @param response
     * @param errInfo  自定义错误信息
     * @param okInfo   自定义成功信息
     * @return 请求成功失败
     */
    public boolean isSuccess(Object response, String errInfo, String okInfo) {
        int resultCode = getHttpErrorCode(response, true, errInfo, okInfo);
        if (resultCode == OrangeHttpConstant.RESULT_SUCCESS) {
            return true;
        }
        return false;
    }


    /**
     * 关掉98的信息，即关掉全部的信息，自然所有信息都不要显示
     *
     * @param response
     * @param isShowInfo false 就是关掉了
     * @return
     */
    public boolean isSuccess(JSONObject response, boolean isShowInfo) {
        int resultCode = getHttpErrorCode(response, isShowInfo, null, null);
        if (resultCode == OrangeHttpConstant.RESULT_SUCCESS) {
            return true;
        }
        return false;
    }

    public int getHttpErrorCode(JSONObject response, String customErrInfo, String customOkInfo) {
        return getHttpErrorCode(response, true, customErrInfo, customOkInfo);
    }

    /**
     * 总，是否成功
     *
     * @param response
     * @param isShowInfo 是否显示98型错误
     * @return
     */
    private int getHttpErrorCode(Object response,
                                 boolean isShowInfo, String customErrInfo, String customOkInfo) {
        int errCode = -1;
        if (response != null && response instanceof JSONObject) {
            errCode = ((JSONObject) response).optInt(OrangeHttpConstant.RESULT_KEY, -1);
        } else {
            if (isShowInfo) {
                ToastHelper.getInstance()._toast("请求失败了...");
            }
            return errCode;
        }
        String strErrCode = String.valueOf(errCode);
        if (errCode == OrangeHttpConstant.RESULT_SUCCESS) {
            if (isShowInfo && customOkInfo != null && !"".equals(customOkInfo)) {//不显示成功信息
                ToastHelper.getInstance()._toast(customOkInfo);
            }
            return errCode;
        } else if (strErrCode.equals(VolleyHttpUtil.ERR_CODE_NO_NET)) {
            if (isShowInfo) {
                ToastHelper.getInstance()._toast("网络还未开启...");
            }
        } else if (strErrCode.equals(VolleyHttpUtil.ERR_CODE_NET_EXCEPTION)) {
            if (isShowInfo) {
                ToastHelper.getInstance()._toast("网络不太给力...");
            }
        } else if (strErrCode.equals(OrangeHttpConstant.ERRCODE_LOGIN_EXPIRE)) {
            jumpToLogin();
        } else if (strErrCode.equals(OrangeHttpConstant.ERRCODE_SYSTEM_ERROR)) {
            if (isShowInfo) {
                ToastHelper.getInstance()._toast("服务器好像开小差了...");
            }
        } else if (OrangeHttpConstant.ERRCODE_BIZ_HEAD.equals(getHeadCode(strErrCode)) || isDebug) {//后者一般测试的时候用，所有错误信息都弹出来
            String systemErrorInfo = "";
            /* 98开头的错误，系统错误信息提示，需要弹出报错信息，否则自定义报错信息 */
            try {
                systemErrorInfo = ((JSONObject) response).getString(OrangeHttpConstant.RESULT_MESSAGE_KEY);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (systemErrorInfo == null || "".equals(systemErrorInfo)) {//如果98信息为空，做处理
                systemErrorInfo = "服务器提示：请求失败了....";
            }
            if (isShowInfo) {
                ToastHelper.getInstance()._toast(systemErrorInfo);
            }
        } else {//非98的错误信息,自定义错误信息
            if (isShowInfo && customErrInfo != null && !"".equals(customErrInfo)) {//不显示错误信息
                ToastHelper.getInstance()._toast(customErrInfo);
            }
        }
        return errCode;
    }

    /**
     * 获取errCode的头两位，用于判断是否是98开头的错误码
     *
     * @param errCode
     */
    private String getHeadCode(String errCode) {
        if (errCode != null && errCode.length() > 2) {
            errCode = errCode.substring(0, 2);
        }
        return errCode;
    }

    private void jumpToLogin() {
        ToastHelper.getInstance()._toast("登录信息过期了...");
        if (mClass != null && ie != null) {
        /*这个跳转到登录的类，是会改变的*/
            ie.exit();
            /*解耦，将SP踢出去，化作接口传入*/
            ie.clearToken();
            Intent intent = new Intent(mContext, mClass);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

    public void setExitApp(IExitApp ie) {
        this.ie = ie;
    }

    public void setLoginClass(Class clazz) {
        this.mClass = clazz;
    }

    IExitApp ie;
    Class mClass;

}
