package com.zerovoid.lib.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;


import com.zerovoid.library.R;

import butterknife.ButterKnife;

/**
 * 容器类
 * Created by 绯若虚无 on 2015/12/21.
 */
public class ContainerActivity extends BaseActivity {


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
