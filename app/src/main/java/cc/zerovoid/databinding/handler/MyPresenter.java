package cc.zerovoid.databinding.handler;

import com.zerovoid.lib.util.ToastHelper;

/**
 * Created by orangelife on 16/12/1.
 */

public class MyPresenter {

    public void onPresenterSaveClick(MyTask myTask) {
        if (myTask != null) {
            myTask.doMyTask();
        } else {
            ToastHelper.getInstance()._toast("myTask is null");
        }
    }

}
