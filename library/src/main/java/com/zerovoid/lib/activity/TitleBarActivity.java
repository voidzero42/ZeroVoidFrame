package com.zerovoid.lib.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

/**
 * 目前支持两种标题栏，ToolBar和自定义标题栏
 * <p/>
 * Created by 绯若虚无 on 2015/11/25.
 *
 * @version 160111
 */
public abstract class TitleBarActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void setTitle(String title, Toolbar toolbar) {
        if (toolbar != null) {
            toolbar.setTitle(title);
            setSupportActionBar(toolbar);
        }
    }
}
