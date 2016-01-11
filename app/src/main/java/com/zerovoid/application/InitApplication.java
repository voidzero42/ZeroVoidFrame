package com.zerovoid.application;


import android.app.Application;
import android.util.Log;

import com.zerovoid.common.util.SpHelper;
import com.zerovoid.lib.http.VolleyHttpUtil;
import com.zerovoid.lib.util.ToastHelper;


public class InitApplication extends Application {
    private static final String TAG = InitApplication.class.getSimpleName();
    private static InitApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initVollyHelper();
        initSharePreferenceUtil();
        initToastHelper();
    }


    public static InitApplication getInstance() {
        if (mInstance == null) {
            mInstance = new InitApplication();
        }
        return mInstance;
    }

    private void initVollyHelper() {
        VolleyHttpUtil.getInstance().initVolleyHttpUtil(getApplicationContext());
    }

    private void initSharePreferenceUtil() {
        SpHelper.getInstance().init(getApplicationContext());
    }

    private void initToastHelper() {
        ToastHelper.getInstance().init(getApplicationContext());
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.e(TAG, "onTrimMemory: level=" + level);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.e(TAG, "onTerminate: ");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.e(TAG, "onLowMemory: 内存不足，请注意安全");
    }
}