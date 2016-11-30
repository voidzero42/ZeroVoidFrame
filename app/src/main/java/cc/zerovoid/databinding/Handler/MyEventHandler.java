package cc.zerovoid.databinding.handler;

import android.view.View;

import com.zerovoid.lib.util.ToastHelper;

/**
 * Created by orangelife on 16/11/29.
 */

public class MyEventHandler {

    public void onClickMe(View view) {
        ToastHelper.getInstance()._toast("xxx");
    }

}
