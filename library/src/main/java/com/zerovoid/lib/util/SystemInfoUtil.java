package com.zerovoid.lib.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by 绯若虚无 on 2015/11/20.
 */
public class SystemInfoUtil {
    private static SystemInfoUtil systemInfoUtil;

    public static SystemInfoUtil getInstances() {
        if (systemInfoUtil == null) {
            systemInfoUtil = new SystemInfoUtil();
        }
        return systemInfoUtil;
    }

    /**
     * 获取当前版本信息
     *
     * @param context
     * @return
     */
    public String getVersionName(Context context) {
        String version = "beta";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = null;
            packInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            version = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static String getSystemInfo(Activity activity) {
        String telModel = "手机型号 = " + android.os.Build.MODEL;
        String release = "Android版本 = " + android.os.Build.VERSION.RELEASE
                + ", API=" + android.os.Build.VERSION.SDK_INT + "\n" + ""
                + android.os.Build.MANUFACTURER + ","
                + android.os.Build.PRODUCT;
        /* 屏幕分辨率 */
        String windowPixels = "屏幕分辨率 = " + WindowHelper.getWidthPixels(activity) + "px * "
                + WindowHelper.getHeightPixels(activity) + "px ( width * height )";
        /* 屏幕密度 */
        String density = "densityDpi（每英寸像素点数）=" + WindowHelper.getDensityDpi(activity)
                + ",scaledDensity=" + WindowHelper.getScaledDensity(activity);
        String s = "DEFAULT=" + "" + DisplayMetrics.DENSITY_DEFAULT
                + ",H=" + DisplayMetrics.DENSITY_HIGH + ",L="
                + DisplayMetrics.DENSITY_LOW + ",M="
                + DisplayMetrics.DENSITY_MEDIUM + ",XH="
                + DisplayMetrics.DENSITY_XHIGH + ",XXH"
                + DisplayMetrics.DENSITY_XXHIGH;
        return new StringBuilder().append(telModel).append("\n").append(release).append("\n").append(windowPixels).append("\n").append(density).append("\n").append(s).toString();
    }
}
