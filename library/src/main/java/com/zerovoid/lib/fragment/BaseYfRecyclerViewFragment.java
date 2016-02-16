package com.zerovoid.lib.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zerovoid.lib.view.yfRecyclerView.YfListInterface;
import com.zerovoid.lib.view.yfRecyclerView.YfRecyclerViewAdapter;


public abstract class BaseYfRecyclerViewFragment extends TitleBarFragment implements YfListInterface.YfLoadMoreListener {
    protected boolean mLoadingLock = false;
    protected int pageSize = 2;
    protected int pageNo = 8;
    public static boolean isLoadMore = false;
    /** 滑动-加载更多模式的请求 */
    protected final int REQ_MODE_LOAD_MORE = 0;
    /** 滑动-下拉刷新模式的请求 */
    protected final int REQ_MODE_REFRESH = 1;
    /** 初始化的请求 */
    protected final int REQ_MODE_INIT = 2;
    /** 当前的请求模式 */
    protected int currentReqMode = REQ_MODE_INIT;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = onCreateViewInstead(inflater, container, savedInstanceState);
        SwipeRefreshLayout swipeRefreshLayout = getSwipeRefreshLayout();
        initSwipeLayout(swipeRefreshLayout);
        return view;
    }

    protected abstract View onCreateViewInstead(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract SwipeRefreshLayout getSwipeRefreshLayout();

    protected void initSwipeLayout(final SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout == null) {
            throw new NullPointerException("SwipeRefreshLayout为空，请检查，如果重写了onCreateView，则必须调用super.onCreateView()方法或者调用initSwipeLayout()");
        }
//        else if (view == null) {
//            throw new NullPointerException("createView返回的view为空，或者重写了onCreateView，请检查");
//        }
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isLoadMore = false;
                mLoadingLock = false;
                pageNo = 1;
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
