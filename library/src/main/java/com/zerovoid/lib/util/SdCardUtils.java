package com.zerovoid.lib.util;

import android.os.Environment;

import java.io.File;

/**
 * 判断SD卡的工具类
 *
 * @author chenxiang
 *
 */
public class SdCardUtils {

	/**
	 * 返回SD卡跟目录
	 */
	public static String getSdCardPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
			return sdDir.toString();
		} else
			return null;
	}

	/**
	 * 判断SD卡是否存在
	 */
	public static boolean isSdCardExist() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}
}
