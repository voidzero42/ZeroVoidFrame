package com.zerovoid.lib.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.zerovoid.lib.fragment.BaseFragment;

/**
 * Activity的基类
 * <p/>
 * Created by zv on 2015/11/22 0022.Modify by zv on 160203.
 *
 * @author 绯若虚无
 */
public class BaseActivity extends AppCompatActivity {

    /** 当前展示的Fragment */
    protected BaseFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KJActivityStack.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KJActivityStack.getInstance().finishActivity(this);
    }

    /**
     * 用Fragment替换视图(from https://github.com/kymjs/KJFrameForAndroid)
     *
     * @param resView        将要被替换掉的视图
     * @param targetFragment 用来替换的Fragment
     */
    public void changeFragment(int resView, BaseFragment targetFragment, Bundle bundle) {
        if (targetFragment == null || targetFragment.equals(currentFragment)) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction.add(resView, targetFragment, targetFragment.getClass()
                    .getName());
        }
        if (targetFragment.isHidden()) {
            transaction.show(targetFragment);
//            targetFragment.onChange();
        }
        if (currentFragment != null && currentFragment.isVisible()) {
            transaction.hide(currentFragment);
        }
        currentFragment = targetFragment;
        if (bundle != null) {
            currentFragment.setArguments(bundle);
        }
        transaction.commit();
    }

    public void changeFragment(int resView, BaseFragment targetFragment) {
        changeFragment(resView, targetFragment, null);
    }
}
