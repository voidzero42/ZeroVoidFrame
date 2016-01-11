package com.zerovoid.common.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.zerovoid.zerovoidframe.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 容器类
 * Created by 吴格非 on 2015/12/21.
 */
public class ContainerActivity extends BaseActivity {

    @Bind(R.id.container)
    FrameLayout container;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        ButterKnife.bind(this);
//        String containerID = JumpManger.getContainerID(this);
//        initFragmentByID(containerID);
    }

    private void initFragmentByID(String containerID) {
        switch (containerID) {
        }
    }

//    private void initSparseArray() {
//    }
}
