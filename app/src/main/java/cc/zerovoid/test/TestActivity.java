package cc.zerovoid.test;

import android.app.Activity;
import android.os.Bundle;

import com.zerovoid.lib.util.CountDownHandler;
import com.zerovoid.zerovoidframe.R;

import butterknife.OnClick;

/**
 * 测试界面
 * <p/>
 * Created by 绯若虚无 on 2015/12/1.
 */
public class TestActivity extends Activity {

//    @Bind(R.id.textView13)
//    TextView textView13;
    private CountDownHandler countDownHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        biz();
//        ButterKnife.bind(this);
    }

    private void biz() {
        setContentView(R.layout.activity_test);
        setContentView(TestBiz.testProgressWebView(this));
        setContentView(R.layout.activity_test_drawline);
//        TestBiz.testSurfaceView(this);
//        TestBiz.testCoundDownHandler(coundDownHandler, textView13);
    }

    @OnClick(R.id.textView13)
    public void onClick() {
//        TestBiz.testCountDownHandler(countDownHandler, textView13);
    }


}
