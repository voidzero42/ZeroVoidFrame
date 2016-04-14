package cc.zerovoid.template.activity;

import com.zerovoid.lib.activity.BaseActivity;
import com.zerovoid.lib.view.other.EmptyLayout;

/**
 * Created by Administrator on 2016/1/19.
 */
public class RefreshActivity extends BaseActivity{
    private final int REQ_MODE_LOAD_MORE = 0;
    private final int REQ_MODE_REFRESH = 1;
    private final int REQ_MODE_INIT = 2;
    EmptyLayout emptyLayout;/*没有自带empty的下拉刷新组件，需要手动添加*/
}
