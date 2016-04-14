package cc.zerovoid.common.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import cc.zerovoid.application.InitApplication;
import com.zerovoid.lib.util.ToastHelper;

/**
 * 跳转Activity的管理类
 * <p/>
 * Created by 绯若虚无 on 2016/1/12.
 */
public class JumpUtil {
    private boolean isExit;

    Handler mHandler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    public void exit(Activity activity) {
        if (!isExit) {
            isExit = true;
            ToastHelper.getInstance()._toast("再按一次退出程序");
            mHandler2.sendEmptyMessageDelayed(0, 2000);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            activity.startActivity(intent);
            InitApplication.getInstance().exit();
        }
    }

}
