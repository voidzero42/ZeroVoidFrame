package com.zerovoid.photo;

import android.app.Activity;
import android.os.Bundle;

import com.zerovoid.lib.activity.BaseActivity;
import com.zerovoid.lib.activity.IntentHelper;

/**
 * 选择图片和照片
 * <p/>
 * Created by zv on 2016/2/22.
 */
public class SelectPicActivity extends BaseActivity {

    /** 跳转到拍照界面 */
    public static void jumpToPhoto(Activity act, int requestCode) {
        IntentHelper.getInstances().intentForResult(act, PhotoActivity.class, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
