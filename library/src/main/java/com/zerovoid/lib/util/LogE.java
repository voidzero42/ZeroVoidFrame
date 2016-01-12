package com.zerovoid.lib.util;

/**
 * 简化版LOG，只打印Error级别
 * Created by 绯若虚无 on 2015/11/25.
 */
public class LogE {
    private static boolean IS_DEBUG = false;
    private static final String TAG = LogE.class.getSimpleName();

    public static void E(String msg) {
        E(TAG, msg);
    }

    public void enableDebug() {
        IS_DEBUG = true;
    }

    public static void setDebug(boolean isDebug) {
        IS_DEBUG = isDebug;
    }

    public static void E(String tag, String msg) {
        if (tag == null || msg == null) {
            return;
        }
        if (IS_DEBUG) {
            android.util.Log.e(tag, msg);
        }
    }


}
