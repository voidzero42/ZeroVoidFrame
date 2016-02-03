package com.zerovoid.lib.fragment;

import android.support.v4.app.Fragment;

/**
 * Fragment的基类
 * <p/>
 * Created by zv on 2015/12/15.Modify by zv on 160203.
 *
 * @author 绯若虚无
 */
public abstract class BaseFragment extends Fragment {

    /**
     * 实体后退键
     *
     * @return 如果返回false，则会自动关闭Activity
     */
    public abstract boolean onBackPressed();

    /**
     * 标题栏的后退键
     *
     * @return 如果返回false，则会调用父类的onBackPressed()，即关闭Activity的方法
     */
    public abstract boolean onBackBtnClick();

}
