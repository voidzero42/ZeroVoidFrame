# ZeroVoidTest
主题：ZeroVoidTest
目前只是用来测试一些组件用；


#【OrangeErrorHandler】
    /** 初始化HTTP错误处理器 */
    private void initErrorHandler() {
        OrangeErrorHandler.getInstance().setDebug(AppConfig.IS_DEBUG);
        OrangeErrorHandler.getInstance().setExitApp(this);
        OrangeErrorHandler.getInstance().setLoginClass(LoginActivity.class);
    }

#【IExitApp】

用法：

class InitApplication extends Application implements IExitApp


