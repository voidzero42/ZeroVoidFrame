package com.zerovoid.lib.interfazz;

/**
 * 退出APP
 * <p/>
 * Created by 绯若虚无 on 2015/12/14.Modify by zv on 160203.
 *
 * @author zv
 */
public interface IExitApp {
    /** 关闭所有被添加到Activity栈内的Activity以及token过期跳转到登录界面 */
    void exit();

    /** 暂未实现，用于清除缓存 */
    void clearCache();

    /** 退出登录，即注销，清除所有的缓存，可以包括AccessToken */
    void clearToken();
}
