package com.zerovoid.test;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;

import com.zerovoid.zerovoidframe.R;

/**
 * 测试界面
 * <p/>
 * Created by 绯若虚无 on 2015/12/1.
 */
public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        TestSurfaceView sfv = (TestSurfaceView) findViewById(R.id.sur);
        sfv.setZOrderOnTop(true);    // necessary
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        SurfaceHolder sfhTrack = sfv.getHolder();
        sfhTrack.setFormat(PixelFormat.TRANSLUCENT);//透明
//        canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);//这句是清屏。
    }


}
