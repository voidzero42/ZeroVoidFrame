package com.zerovoid.animation;

import android.app.Activity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;

import com.zerovoid.lib.util.LogE;

/**
 * Created by Administrator on 2016/1/12.
 */
public class AnimationUtil {

    private void playHeartbeatAnimation(final View view) {
        LogE.E("开始动画");
        AnimationSet swellAnimationSet = new AnimationSet(true);
        swellAnimationSet.addAnimation(new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f));
        swellAnimationSet.addAnimation(new AlphaAnimation(1.0f, 0.3f));

        swellAnimationSet.setDuration(500);
        swellAnimationSet.setInterpolator(new AccelerateInterpolator());
        swellAnimationSet.setFillAfter(true);

        swellAnimationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                AnimationSet shrinkAnimationSet = new AnimationSet(true);
                shrinkAnimationSet.addAnimation(new ScaleAnimation(1.2f, 1.0f, 1.2f, 1.0f, Animation.RELATIVE_TO_SELF,
                        0.5f, Animation.RELATIVE_TO_SELF, 0.5f));
                shrinkAnimationSet.addAnimation(new AlphaAnimation(0.3f, 1.0f));
                shrinkAnimationSet.setDuration(1000);
                shrinkAnimationSet.setInterpolator(new DecelerateInterpolator());
                shrinkAnimationSet.setFillAfter(false);
                view.clearAnimation();
                view.startAnimation(shrinkAnimationSet);// 动画结束时重新开始，实现心跳的View
            }
        });
        view.clearAnimation();
        view.startAnimation(swellAnimationSet);
    }

    private boolean isStopAnimation;

    private class HeatbeatThread extends Thread {
        Activity context;
        View view;
        public void run() {
            LogE.E("HeatbeatThread run");
            try {
                sleep(100);
                while (!isStopAnimation) {
                    context.runOnUiThread(new Runnable() {
                        public void run() {
                            LogE.E("runOnUiThread run");
                            if (!isStopAnimation) {
                                playHeartbeatAnimation(view);
                            }
                        }
                    });
                    Thread.sleep(1500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Thread heartbeatThread;

    /**
     * 开始心跳
     */
    private void startHeartBeat() {
        isStopAnimation = false;
        if (heartbeatThread == null) {
            heartbeatThread = new HeatbeatThread();
        }
        if (!heartbeatThread.isAlive()) {
            heartbeatThread.start();
        }
    }

    /**
     * 停止心跳
     */
    private void stopHeartBeat(View view) {
        isStopAnimation = true;
        if (heartbeatThread != null && !heartbeatThread.isInterrupted()) {
            heartbeatThread.interrupt();
            heartbeatThread = null;
            System.gc();
        }
        if (view != null) {
            view.clearAnimation();
        }
    }
}
