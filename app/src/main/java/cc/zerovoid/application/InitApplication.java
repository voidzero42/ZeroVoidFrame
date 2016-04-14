package cc.zerovoid.application;


import android.app.Application;
import android.util.Log;

import cc.zerovoid.common.util.SpHelper;
import com.zerovoid.lib.http.VolleyHttpUtil;
import com.zerovoid.lib.interfazz.IExitApp;
import com.zerovoid.lib.util.ToastHelper;
import cc.zerovoid.login.activity.LoginActivity;
import com.zerovoid.orange.http.OrangeErrorHandler;

/**
 * 本程序的Application
 * <p/>
 * Create by zv on unknown.Modify by zv on 160203.
 *
 * @author 绯若虚无
 */
public class InitApplication extends Application implements IExitApp {
    private static final String TAG = InitApplication.class.getSimpleName();
    private static InitApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initVolleyHelper();
        initSharePreferenceUtil();
        initToastHelper();
        initErrorHandler();
    }


    public static InitApplication getInstance() {
        if (mInstance == null) {
            mInstance = new InitApplication();
        }
        return mInstance;
    }

    /** 初始化HTTP错误处理器 */
    private void initErrorHandler() {
        OrangeErrorHandler.getInstance().setDebug(AppConfig.IS_DEBUG);
        OrangeErrorHandler.getInstance().setExitApp(this);
        OrangeErrorHandler.getInstance().setLoginClass(LoginActivity.class);
    }

    /** 初始化Volley，本APP所使用的HTTP框架 */
    private void initVolleyHelper() {
        VolleyHttpUtil.getInstance().initVolleyHttpUtil(getApplicationContext());
    }

    /** 初始化SharedPreferences */
    private void initSharePreferenceUtil() {
        SpHelper.getInstance().init(getApplicationContext());
    }

    /** 初始化Toast */
    private void initToastHelper() {
        ToastHelper.getInstance().init(getApplicationContext());
    }

    @Override
    public void exit() {

    }

    @Override
    public void clearCache() {
        //比如HTML缓存，图片缓存
    }

    @Override
    public void clearToken() {
        //SpHelper.getInstance();//Token一般存在SP中，
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