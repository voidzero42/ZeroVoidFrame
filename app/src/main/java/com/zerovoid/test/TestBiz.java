package com.zerovoid.test;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.webkit.WebView;
import android.widget.TextView;

import com.zerovoid.lib.util.CountDownHandler;
import com.zerovoid.lib.view.other.ProgressWebView;
import com.zerovoid.zerovoidframe.R;

/**
 * Created by zerovoid on 2016/1/15.
 */
public class TestBiz {

    public static WebView testProgressWebView(Context context) {
        ProgressWebView wv = new ProgressWebView(context, null);
        wv.loadUrl("http://www.eoeandroid.com/thread-71606-1-1.html?_dsign=f6214dcf");
        return wv;
    }

    /**
     * 160113
     *
     * @param act
     */
    public static void testSurfaceView(Activity act) {
        TestSurfaceView sfv = (TestSurfaceView) act.findViewById(R.id.sur);
        sfv.setZOrderOnTop(true);    // necessary
        DisplayMetrics dm = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        SurfaceHolder sfhTrack = sfv.getHolder();
        sfhTrack.setFormat(PixelFormat.TRANSLUCENT);//透明
//        canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);//这句是清屏。
    }

    /**
     * 160113
     *
     * @param coundDownHandler 定时Handler
     * @param textView13       按钮
     * @return
     */
    public static CountDownHandler testCoundDownHandler(CountDownHandler coundDownHandler, final TextView textView13) {
        if (coundDownHandler == null) {
            coundDownHandler = new CountDownHandler(textView13, 1);
            coundDownHandler.setOnCountDownTimerFinishedListener(new CountDownHandler.OnCountDownTimerFinishedListener() {
                @Override
                public void onFinish() {//为0的时候，倒计时结束
                    textView13.setText("重新发送");
                }

                @Override
                public void onCountDown(String currentSecond) {

                }
            });
        }
        coundDownHandler.startTimer(60);
        return coundDownHandler;
    }

}
