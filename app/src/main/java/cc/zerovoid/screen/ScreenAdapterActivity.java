package cc.zerovoid.screen;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import com.zerovoid.lib.util.SystemInfoUtil;
import com.zerovoid.lib.util.WindowHelper;
import com.zerovoid.zerovoidframe.R;

/**
 * 屏幕信息展示
 * <p/>
 * Created by Administrator on 2015/11/19.
 */
public class ScreenAdapterActivity extends Activity {

    /** 屏幕尺寸 */
    private TextView tvScreenSize;
    /** 屏幕密度 */
    private TextView tvDensity;
    private TextView tvModel;
    private TextView tvRelease;
    private TextView tvScreenSizeDp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_adapter);
        initView();
        setText();
    }

    private void initView() {
        tvModel = (TextView) findViewById(R.id.tvModel);
    }

    private void setText() {
        /* 手机信息 */
        tvModel.setText(SystemInfoUtil.getSystemInfo(this));
    }


}
