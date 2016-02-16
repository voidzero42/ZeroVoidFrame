package com.zerovoid.lib.fragment;

/**
 * Created by Administrator on 2016/2/16.
 */
public abstract class TitleBarFragment extends BaseFragment {
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
