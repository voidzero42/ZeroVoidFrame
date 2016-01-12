package com.zerovoid.test;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.zerovoid.lib.util.CountDownHandler;
import com.zerovoid.zerovoidframe.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 测试界面
 * <p/>
 * Created by 绯若虚无 on 2015/12/1.
 */
public class TestActivity extends Activity {

    @Bind(R.id.textView13)
    TextView textView13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        TestSurfaceView sfv = (TestSurfaceView) findViewById(R.id.sur);
        sfv.setZOrderOnTop(true);    // necessary
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        SurfaceHolder sfhTrack = sfv.getHolder();
        sfhTrack.setFormat(PixelFormat.TRANSLUCENT);//透明
//        canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);//这句是清屏。
        startTimer();
    }



    private void startTimer() {
        initCoundDownHandler();
    }

    private void initCoundDownHandler() {
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
    }

    @OnClick(R.id.textView13)
    public void onClick() {
        startTimer();
    }

    private CountDownHandler coundDownHandler;
}
