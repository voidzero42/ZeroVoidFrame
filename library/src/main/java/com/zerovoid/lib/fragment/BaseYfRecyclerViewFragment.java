package com.zerovoid.lib.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zerovoid.lib.view.yfRecyclerView.RecycleViewAdapter;


public abstract class BaseYfRecyclerViewFragment extends BaseFragment {
    protected boolean mLoadingLock = false;
    protected int pageSize = 2;
    protected int pageNo =8;

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
                swipeRefreshLayout.setRefreshing(false);
                refresh();
            }
        });
    }

    public void loadMore(RecycleViewAdapter mAdapter, int mTotalDataCount) {
        if (mLoadingLock) {
            return;
        }
        if (mAdapter.getData().size() < mTotalDataCount && mAdapter.getData().size() > 0) {
            // has more
            mLoadingLock = true;
            if (!mAdapter.getFooters().contains("loading...")) {
                mAdapter.addFooter("loading...");
            }
            load();
        } else {
            // no more
            if (mAdapter.getFooters().contains("loading...")) {
                mAdapter.removeFooter("loading...");
            }
        }
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
            }
        }
    }

    protected abstract void refresh();

    protected abstract void load();
}
