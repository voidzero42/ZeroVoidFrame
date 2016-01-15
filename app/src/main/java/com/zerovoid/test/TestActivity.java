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
import com.zerovoid.lib.view.other.ProgressWebView;
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
    private CountDownHandler coundDownHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        biz();
    }

    private void biz() {
        setContentView(TestBiz.testProgressWebView(this));
//        TestBiz.testSurfaceView(this);
//        TestBiz.testCoundDownHandler(coundDownHandler, textView13);
    }

    @OnClick(R.id.textView13)
    public void onClick() {
        TestBiz.testCoundDownHandler(coundDownHandler, textView13);
    }


}
