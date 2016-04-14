package cc.zerovoid.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SpHelper {

    private static SpHelper sharePreferenceUtil;

    private SharedPreferences spInfo;

    public SpHelper() {

    }

    public static SpHelper getInstance() {
        if (sharePreferenceUtil == null) {
            sharePreferenceUtil = new SpHelper();
        }
        return sharePreferenceUtil;
    }

    /** 必须初始化 */
    public void init(Context context) {
        this.spInfo = context.getSharedPreferences("user_info", 0);
    }

    /**
     * 单例模式中获取唯一的AppContext实例1
     *
     * @return
     */

    public static SpHelper getSharePreference(Context context) {
        sharePreferenceUtil = new SpHelper(context);
        return sharePreferenceUtil;
    }

    public Editor getEditor() {
        return this.spInfo.edit();
    }

    public SharedPreferences getSP() {
        return this.spInfo;
    }

    public SpHelper(Context paramContext) {
        this.spInfo = paramContext.getSharedPreferences("user_info", 0);
    }
}
