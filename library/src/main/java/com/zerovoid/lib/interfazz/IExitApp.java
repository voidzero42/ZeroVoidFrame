package com.zerovoid.lib.interfazz;

import android.app.Activity;

/**
 * Created by 绯若虚无 on 2015/12/14.
 */
public interface IExitApp {
    void finishActivity(Activity activity);
    void addActivity(Activity activity);
    void exit();
    /**退出登录，即注销，清除所有的缓存，可以包括AccessToken*/
    void clearCache();
    void clearToken();
}
