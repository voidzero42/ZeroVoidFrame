package com.zerovoid.lib.net;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络状态检测工具
 * 
 * @author 绯若虚无-140630
 * 
 * 
 */
public class NetworkUtil {

	/** 网络未连接 */
	public static final int NETWORK_DISCONNECTED = 0;
	/** WIFI，但是不能判断是否连接外网，也不能判断是否在家中 */
	public static final int NETWORK_WIFI = 1;
	/** 移动网络 */
	public static final int NETWORK_MOBILE = 2;
	/** 未知状态 */
	public static final int NETWORK_UNKNOWN = 3;
	/** 家里的WIFI */
	public static final int NETWORK_WIFI_HOME = 4;

	/**
	 * 判断是否联网
	 * 
	 * @param context
	 *            activity
	 * @return true有联网，false没联网
	 */
	public static boolean isConnectedNet(Context context) {
		int i = checkActivityNetwork(context);
		if (i == NETWORK_WIFI || i == NETWORK_MOBILE) {
			return true;
		}
		return false;
	}

	/**
	 * 检测网络类型
	 * 
	 * @param context
	 * @return 0-网络未连接，1-WIFI，2-移动，3-未知；
	 */
	public static int checkActivityNetwork(Context context) {
		/* 检查网络是否连接 */
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo netinfo = cm.getActiveNetworkInfo();
		if (netinfo != null && netinfo.isConnected()) {
			if (netinfo.getType() == ConnectivityManager.TYPE_MOBILE) {
				return NETWORK_MOBILE;
			} else if (netinfo.getType() == ConnectivityManager.TYPE_WIFI) {
				return NETWORK_WIFI;
			} else {
				return NETWORK_UNKNOWN;
			}
		} else {
			return NETWORK_DISCONNECTED;
		}
	}
}
