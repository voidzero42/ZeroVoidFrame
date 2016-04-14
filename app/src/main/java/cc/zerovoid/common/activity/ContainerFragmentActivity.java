package cc.zerovoid.common.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.zerovoid.lib.activity.TitleBarActivity;
import com.zerovoid.lib.fragment.BaseFragment;
import com.zerovoid.library.R;

import butterknife.ButterKnife;

/**
 * 容器类，这个类，需要不断地添加使用的Fragment，即一直处于
 * Created by 绯若虚无 on 2015/12/21.Modify by zv on 160203.
 *
 * @author 绯若虚无
 */
public class ContainerFragmentActivity extends TitleBarActivity {

//    @Bind(R.id.toolbar)
//    Toolbar toolbar;
    public static final int FRAGMENT_SHOP_HOUR = 0;
    private BaseFragment currentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        ButterKnife.bind(this);
        int fragmentNum = getIntent().getIntExtra("fragmentNum", -1);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        changeFragment(R.id.container, getFragmentByID(fragmentNum), bundle);
    }

    private BaseFragment getFragmentByID(int fragmentNum) {
        switch (fragmentNum) {
            case FRAGMENT_SHOP_HOUR:
//                currentFragment = new ShopHourFragment();
//                setTitle("营业时间", toolbar);
                break;
            default:
                break;
        }
        return currentFragment;
    }

    public static void jumpToContainer(Context ctx, int fragmentNum) {
        jumpToContainer(ctx, fragmentNum, null);
    }

    /** 之所以不写在JumpManager中，而写在这里，因为此方法为此类的核心方法，无此方法，使用此类非常麻烦，故放于此 */
    public static void jumpToContainer(Context ctx, int fragmentNum, Bundle bundle) {
        Intent intent = new Intent(ctx, ContainerFragmentActivity.class);
        intent.putExtra("fragmentNum", fragmentNum);
        if (bundle != null) {
            intent.putExtra("bundle", bundle);
        }
        ctx.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (currentFragment != null) {
//                if (!currentFragment.onBackBtnClick()) {
//                    IntentHelper.getInstances().jumpActivityAnimation(this, true);
//                    finish();
//                }
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (currentFragment != null) {
//            if (!currentFragment.onBackPressed()) {
//                super.onBackPressed();
//            }
        }
    }

    //    private void initSparseArray() {
//    }
}
