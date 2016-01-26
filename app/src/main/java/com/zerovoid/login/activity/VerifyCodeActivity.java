package com.zerovoid.login.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zerovoid.lib.activity.BaseActivity;
import com.zerovoid.lib.util.CountDownHandler;
import com.zerovoid.login.biz.LoginHttpBiz;
import com.zerovoid.zerovoidframe.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 通用验证码功能
 * <p/>
 * Created by zv on 2016/1/26.
 *
 * @author zv
 */
public class VerifyCodeActivity extends BaseActivity {
    //TODO 修改意见，还是作为父类继承好了，使用CountDownHandler还是很麻烦；
    @Bind(R.id.tvTime)
    TextView tvTime;
    @Bind(R.id.btnReq)
    Button btnReq;
    @Bind(R.id.editText)
    EditText editText;
    @Bind(R.id.btnSubmit)
    Button btnSubmit;
    @Bind(R.id.tvResult)
    TextView tvResult;
    /** 倒计时Handler */
    private CountDownHandler handler;
    /** 倒计时时间 */
    private final int countDownTime = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);
        ButterKnife.bind(this);
        initCountDownHandler();
    }

    @OnClick({R.id.btnReq, R.id.btnSubmit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnReq:
                startTimer();
                requestVerifyCode();
                break;
            case R.id.btnSubmit:
                requestSubmit();
                break;
        }
    }

    /** 请求验证码 */
    private void requestVerifyCode() {
        LoginHttpBiz.getVerifyCode();
    }

    /** 提交验证码 */
    private void requestSubmit() {
        LoginHttpBiz.submitVerifyCode();
        setSubmitResult();
    }

    private void setTimeResult() {
        //TODO Toast
        tvTime.setText("请求验证码");
        tvTime.setText("重新请求");
    }

    private void setSubmitResult() {
        tvResult.setText("提交失败，请重试");
    }

    /** 启动倒计时定时器 */
    private void startTimer() {
        if (handler != null) {
            handler.startTimer(countDownTime);
        }
    }

    /** 停止倒计时定时器 */
    private void stopTimer() {
        if (handler != null && !handler.hasStop()) {
            handler.stopTimer();
        }
    }

    private CountDownHandler initCountDownHandler() {
        if (handler == null) {
            handler = new CountDownHandler(tvTime, 0);
            handler.setOnCountDownTimerFinishedListener(new CountDownHandler.OnCountDownTimerFinishedListener() {
                @Override
                public void onFinish() {
                    //TODO 这块应该也继承，这样连监听器也不用谢了
                    setTimeResult();
                }

                @Override
                public void onCountDown(String currentSecond) {

                }
            });
        }
        return handler;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }
}
