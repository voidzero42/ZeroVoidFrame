package com.zerovoid.lib.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zerovoid.library.R;

import java.io.Serializable;

/**
 * Intent帮助类
 *
 * @author 陈福荣
 */
public class IntentHelper<T> {
    private static IntentHelper intentHelper;

    public static final String FLAG = "INTENT_FLAG";

    public static IntentHelper getInstances() {
        if (intentHelper == null) {
            intentHelper = new IntentHelper();
        }
        return intentHelper;
    }

    public static String getStringExtra(Activity act) {
        return act.getIntent().getStringExtra(FLAG);
    }

    public void getIntent(Context context, Class<?> s) {
        intentWithExtraForResult(context, s, null, null, 0);
    }

    public void intentWithExtraString(Context context, Class<?> s,
                                      String name, T value) {
        intentWithExtraForResult(context, s, name, value, 0);
    }

    public void intentWithExtraString(Context context, Class<?> s,
                                      T value) {
        intentWithExtraForResult(context, s, FLAG, value, 0);
    }

    public void intentWithExtraStringForResult(Context context, Class<?> s,
                                               T value, int requestCode) {
        intentWithExtraForResult(context, s, FLAG, value, requestCode);
    }

    public void intentWithExtraForResult(Context context, Class<?> s,
                                         String name, T value, int requestCode) {
        intentWithExtraForResult(context, s, name, value, -1, requestCode);
    }

    public void intentWithExtraForResult(Context context, Class<?> s,
                                         String name, T value, int flag, int requestCode) {
        Intent intent = new Intent(context, s);
        if (name != null && !name.equals("")) {
            if (value instanceof Boolean) {
                intent.putExtra(name, (Boolean) value);
            } else if (value instanceof String) {
                intent.putExtra(name, (String) value);
            } else if (value instanceof Integer) {
                intent.putExtra(name, (Integer) value);
            }
        }
        if (flag != -1) {
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (requestCode != -1 && context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, requestCode);
        } else {
            context.startActivity(intent);
            jumpActivityAnimation(context, false);
        }
    }

    public void bundleWithExtra(Context context, Class<?> s, Serializable serializable) {
        bundleWithExtra(context, s, null, serializable, -1, -1);
    }

    public void bundleWithExtra(Context context, Class<?> s, Bundle bundle) {
        bundleWithExtra(context, s, bundle, null, -1, -1);
    }

    public void bundleWithExtra(Context context, Class<?> s, Bundle bundle, Serializable serializable) {
        bundleWithExtra(context, s, bundle, serializable, -1, -1);
    }

    public void bundleWithExtra(Context context, Class<?> s, Bundle bundle, Serializable serializable, int flag, int requestCode) {
        Intent intent = new Intent(context, s);
        if (serializable != null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putSerializable(FLAG, serializable);
        }
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (flag != -1) {
            intent.setFlags(flag);
        }
        if (requestCode != -1 && context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, requestCode);
        } else {
            context.startActivity(intent);
            jumpActivityAnimation(context, false);
        }
    }

    public Serializable getSerializable(Activity activity) {
        return activity.getIntent().getExtras().getSerializable(FLAG);
    }

    public void intentForResult(Activity context,
                                Class<?> s, int requestCode) {
        bundleWithBundleForResultWithoutValue(context, s, null, requestCode);
    }

    public void bundleWithBundleForResultWithoutValue(Activity context,
                                                      Class<?> s, Bundle bundle, int requestCode) {
        bundleWithExtra(context, s, bundle, null, -1, requestCode);
    }

    public void bundleWithBundleForResult(Activity context,
                                          Class<?> s, String value, int requestCode) {
        Bundle bundle = new Bundle();
        bundle.putString(FLAG, value);
        bundleWithExtra(context, s, bundle, null, -1, requestCode);
    }

//TODO 这里涉及到外部的ANIM动画资源

    /**
     * 带动画的Activity跳转，
     *
     * @param context
     * @param isFinishActivity 是否结束当前Activity
     */
    public void jumpActivityAnimation(Context context,
                                      boolean isFinishActivity) {
        if (!isFinishActivity) {
            ((Activity) context).overridePendingTransition(
                    R.anim.activity_enter, R.anim.activity_exit);
        } else {
            ((Activity) context).overridePendingTransition(
                    R.anim.activity_finish_exit,
                    R.anim.activity_finish_enter);
        }
    }

//	public String getIntentWithExtraString(Context context){
//
//		return ((Activity)context).getIntent().getStringExtra(key);
//	}
}
