package com.zerovoid.screen;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import com.zerovoid.zerovoidframe.R;

/**
 * 屏幕信息展示
 *
 * Created by Administrator on 2015/11/19.
 */
public class ScreenAdapterActivity extends Activity{

    /** 屏幕尺寸 */
    private TextView tvScreenSize;
    /** 屏幕密度 */
    private TextView tvDensity;
    private TextView tvModel;
    private TextView tvRelease;
    private TextView tvScreenSizeDp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_adapter);
        initView();
        setText();
    }

    private void initView() {
        tvScreenSize = (TextView) findViewById(R.id.tvScreenSize);
        tvDensity = (TextView) findViewById(R.id.tvDensity);
        tvModel = (TextView) findViewById(R.id.tvModel);
        tvRelease = (TextView) findViewById(R.id.tvRelease);
        tvScreenSizeDp = (TextView) findViewById(R.id.tvScreenSizeDp);
    }

    private void setText() {
		/* 手机信息 */
        tvModel.setText("手机型号 = " + android.os.Build.MODEL);
        tvRelease.setText("Android版本 = " + android.os.Build.VERSION.RELEASE
                + ", API=" + android.os.Build.VERSION.SDK_INT + "\n" + ""
                + android.os.Build.MANUFACTURER + ","
                + android.os.Build.PRODUCT);
		/* 屏幕分辨率 */
        tvScreenSize.setText("屏幕分辨率 = " + getDm().widthPixels + "px * "
                + getDm().heightPixels + "px ( width * height )");
		/* 屏幕密度 */
        tvDensity.setText("densityDpi（每英寸像素点数）=" + getDm().densityDpi
                + ",scaledDensity=" + getDm().scaledDensity);

        Log.e("TestActivity", "DEFAULT=" + "" + DisplayMetrics.DENSITY_DEFAULT
                + ",H=" + DisplayMetrics.DENSITY_HIGH + ",L="
                + DisplayMetrics.DENSITY_LOW + ",M="
                + DisplayMetrics.DENSITY_MEDIUM + ",XH="
                + DisplayMetrics.DENSITY_XHIGH + ",XXH"
                + DisplayMetrics.DENSITY_XXHIGH);
    }

    private DisplayMetrics getDm() {
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
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
