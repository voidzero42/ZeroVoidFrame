package cc.zerovoid.common.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.zerovoid.lib.activity.BaseActivity;
import com.zerovoid.lib.view.yfRecyclerView.YfRecyclerViewAdapter;
import com.zerovoid.lib.view.yfRecyclerView.YfListInterface;

/**
 * Created by YangWeion 2015/12/22.
 */
public abstract class BaseYfRecyclerViewActivity extends BaseActivity implements YfListInterface.YfLoadMoreListener {
    protected boolean mLoadingLock = false;
    protected int pageSize = 8;
    protected int pageNo = 1;
    protected boolean isLoadMore = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected abstract SwipeRefreshLayout getSwipeRefreshLayout();

    /**
     * 抽出初始化SwipeReFreshLayout的方法
     *
     * @param swipeRefreshLayout
     */
    protected void initSwipeLayout(final SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout == null) {
            throw new NullPointerException("SwipeRefreshLayout为空，请检查，如果重写了onCreateView，则必须调用super.onCreateView()方法或者调用initSwipeLayout()");
        }
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                refresh();
            }
        });
    }

    protected void showRefreshing() {
        if (getSwipeRefreshLayout() != null) {
            if (getSwipeRefreshLayout().isRefreshing()) {
                getSwipeRefreshLayout().setRefreshing(false);
            }
            getSwipeRefreshLayout().setRefreshing(true);
        }
    }

    protected void hideRefreshing() {
        if (getSwipeRefreshLayout() != null) {
            if (getSwipeRefreshLayout().isRefreshing()) {
                getSwipeRefreshLayout().setRefreshing(false);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                }, 3000);
            }
        }
    }

    protected abstract void refresh();

    protected abstract void loadMoreData();

    //onLoadMore();

    protected abstract YfRecyclerViewAdapter getAdapter();

    final String strLoading = "橙子正在努力地加载...";
    private boolean isFirstEnableAutoLoadMore = true;

    @Override
    public void loadMore() {
        YfRecyclerViewAdapter adapter = getAdapter();
        if (adapter == null) {
            throw new NullPointerException("getAdapter()返回的Adapter不能为空...");
        }
        if (mLoadingLock) {
            return;
        }
        if (adapter.getData().size() < adapter.getTotalNum() && adapter.getData().size() > 0) {
            // has more
            if (!adapter.getFooters().contains(strLoading)) {
                adapter.addFooter(strLoading);
            }
            if (!isFirstEnableAutoLoadMore) {
                mLoadingLock = true;
                pageNo++;
                isLoadMore = true;
                loadMoreData();
            } else {
                isFirstEnableAutoLoadMore = false;
            }
        } else {
            // no more
            if (adapter.getFooters().contains(strLoading)) {
                adapter.removeFooter(strLoading);
            }
        }
    }
}
