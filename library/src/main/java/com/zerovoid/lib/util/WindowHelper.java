package com.zerovoid.lib.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 获得屏幕的长和宽
 *
 * @version 160112 绯若虚无
 */
public class WindowHelper {

    public static DisplayMetrics getDm(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    public static int getWidthPixels(Activity activity) {
        return getDm(activity).widthPixels;
    }

    public static int getHeightPixels(Activity activity) {
        return getDm(activity).heightPixels;
    }

    public static int getDensityDpi(Activity activity) {
        return getDm(activity).densityDpi;
    }

    public static float getScaledDensity(Activity activity) {
        return getDm(activity).scaledDensity;
    }


    public static int getWindowWidth(Context myContext) {
        WindowManager wm = (WindowManager) myContext
                .getSystemService(Context.WINDOW_SERVICE);

        return wm.getDefaultDisplay().getWidth();
    }

    public static int getWindowHeight(Context myContext) {
        WindowManager wm = (WindowManager) myContext
                .getSystemService(Context.WINDOW_SERVICE);

        return wm.getDefaultDisplay().getHeight();
    }

    /** 开启全屏 */
    public static void openFullScreen(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.requestWindowFeature(Window.FEATURE_PROGRESS);
    }

    /** 设置键盘不显示 */
    public static void hideSoftInput(Activity activity) {
        activity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * 获取屏幕的宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }

    /**
     * 获取屏幕的高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }

    /**
     * 获取屏幕中控件顶部位置的高度--即控件顶部的Y点
     *
     * @return
     */
    public static int getScreenViewTopHeight(View view) {
        return view.getTop();
    }

    /**
     * 获取屏幕中控件底部位置的高度--即控件底部的Y点
     *
     * @return
     */
    public static int getScreenViewBottomHeight(View view) {
        return view.getBottom();
    }

    /**
     * 获取屏幕中控件左侧的位置--即控件左侧的X点
     *
     * @return
     */
    public static int getScreenViewLeftHeight(View view) {
        return view.getLeft();
    }

    /**
     * 获取屏幕中控件右侧的位置--即控件右侧的X点
     *
     * @return
     */
    public static int getScreenViewRightHeight(View view) {
        return view.getRight();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
