package com.zerovoid.lib.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2015/11/20.
 */
public class SystemInfoUtil {
    private static SystemInfoUtil systemInfoUtil;
    public static SystemInfoUtil getInstances(){
        if(systemInfoUtil ==null){
            systemInfoUtil = new SystemInfoUtil();
        }
        return systemInfoUtil;
    }

    /**
     * 获取当前版本信息
     * @param context
     * @return
     */
    public String getVersionName(Context context){
        String version="beta";
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
}
