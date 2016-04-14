package com.zerovoid.lib.util;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.sql.Time;

/**
 * 倒计时的通用Handler
 *
 * https://github.com/joefei
 * <p/>
 * Created by 绯若虚无 on 2016/1/5.Modify by zv on 160330
 *
 * @author zv
 */
public class CountDownHandler extends Handler {
    private WeakReference<TextView> weakReference;
    private TextView mTv;
    /** 倒计时总秒数 */
    private int mSecond = 0;
    /** 是否只显示秒数，不转化为时分秒 */
    private boolean mIsOnlySecond = false;
    /** 时间间隔 */
    private int interval = 1000;
    private int mWhat;
    private boolean hasStop = false;
    private boolean mIsUseCustomCountDownInfo;

    public CountDownHandler() {
    }

    public CountDownHandler(TextView tv, int what) {
        weakReference = new WeakReference<>(tv);
        mWhat = what;
    }

    public void setTextView(TextView newTv) {
        weakReference = new WeakReference<>(newTv);
    }

    public void startTimer(int second) {
        startTimer(String.valueOf(second));
    }

    public void startTimer(String second) {
        hasStop = false;
        //TODO 对Handler的机制还是不了解，先这么写
        removeCallbacksAndMessages(null);
        setCountSecond(second);
        sendMessageDelayed(obtainMessage(mWhat), 1000);
    }

    public void stopTimer() {
        hasStop = true;
        removeCallbacksAndMessages(null);
    }

    /**
     * 将倒计时总秒数传入
     *
     * @param second 总秒数
     */
    public void setCountSecond(String second) {
        try {
            mSecond = Integer.parseInt(second);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        setTimeToTextView();
    }

    public void isUseCustomCountDownInfo(boolean isUseCustomCountDownInfo) {
        this.mIsUseCustomCountDownInfo = isUseCustomCountDownInfo;
    }

    public void isOnlySecond(boolean isOnlySecond) {
        this.mIsOnlySecond = isOnlySecond;
    }

    private void setTimeToTextView() {
        //TODO 加入年月天时分秒
        String second = String.valueOf(mSecond);
        if (!mIsOnlySecond) { //是否需要转化为时分秒
            second = convertSecondToHour(second);
        }
        if (mIsUseCustomCountDownInfo) {
            if (mListener != null) {
                mListener.onCountDown(second);
            }
        } else {
            if (mTv != null) {
                mTv.setText(second);
            }
        }
    }

    /** 将秒数转为小时 */
    private String convertSecondToHour(String second) {
        String result = "";
        int secondInt;
        try {
            secondInt = Integer.parseInt(second);
            int h = secondInt / 3600;
            int m = (secondInt - h * 3600) / 60;
            int s = secondInt % 60;
            result = String.format("%02d:%02d:%02d", h, m, s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (msg.what == mWhat) {
            mTv = weakReference.get();
            if (mSecond > 0) {
                mSecond--;
                if (!hasStop) {
                    this.sendMessageDelayed(obtainMessage(mWhat), interval);
                } else {
                    if (mListener != null) {
                        mListener.onFinish();
                    }
                    return;
                }
                setTimeToTextView();
            } else {
                if (mListener != null) {
                    //CALLBACK TimeUp
                    mListener.onFinish();
                }
            }
        }
    }

    public boolean hasStop() {
        return hasStop;
    }


    private OnCountDownTimerFinishedListener mListener;

    public void setOnCountDownTimerFinishedListener(OnCountDownTimerFinishedListener listener) {
        mListener = listener;
    }

    //倒计时都是一致的，但是倒计时结束后的处理，是不一致的，所以将倒计时结束的事件外放，让使用者自行调用；
    public interface OnCountDownTimerFinishedListener {
        void onFinish();

        void onCountDown(String currentSecond);
    }
}
