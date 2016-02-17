package com.zerovoid.lib.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.zerovoid.lib.view.yfRecyclerView.YfListMode;
import com.zerovoid.lib.view.yfRecyclerView.YfRecyclerViewAdapter;
import com.zerovoid.lib.view.yfRecyclerView.YfListInterface;

import java.util.List;


/**
 * YfRecyclerView（某下拉刷新组件的基类）
 * <p/>
 * Created by YangWei on 2015/12/22.
 *
 * @author zv
 */
public abstract class BaseYfRecyclerViewActivity extends TitleBarActivity implements YfListInterface.YfLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    protected boolean mLoadingLock = false;
    protected int pageSize = 8;
    protected int pageNo = 1;
    protected boolean isLoadMore = false;
    /** 滑动-加载更多模式的请求 */
    protected final int REQ_MODE_LOAD_MORE = 0;
    /** 滑动-下拉刷新模式的请求 */
    protected final int REQ_MODE_REFRESH = 1;
    /** 初始化的请求 */
    protected final int REQ_MODE_INIT = 2;
    /** 当前的请求模式 */
    protected int currentReqMode = REQ_MODE_INIT;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSwipeLayout(getSwipeRefreshLayout());
    }

    /** 获得子类中的mSwipeRefreshLayout的实例，父类中需要用到此实例。如果分类写mSwipeRefreshLayout，子类实例化XXX，可读性不高 */
    protected abstract SwipeRefreshLayout getSwipeRefreshLayout();

    /**
     * 初始化
     *
     * @param swipeRefreshLayout
     */
    protected void initSwipeLayout(final SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout == null) {
            throw new NullPointerException("SwipeRefreshLayout为空，请检查，1、如果重写了onCreate(Bundle)，则必须调用super.onCreate(Bundle)方法，2、或者调用initSwipeLayout()");
        }
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        getSwipeRefreshLayout().setRefreshing(false);
        refresh();
        pageNo = 1;
        request();
        changeMode(REQ_MODE_REFRESH);
    }

    /**
     * 原本也是写在子类中，但是这种东西最好还是父类处理掉，免得遗漏
     *
     * @param mode
     */
    private void changeMode(int mode) {
        currentReqMode = mode;
    }

    protected void showRefreshing() {
        if (getSwipeRefreshLayout() != null) {
            if (getSwipeRefreshLayout().isRefreshing()) {
                getSwipeRefreshLayout().setRefreshing(false);
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getSwipeRefreshLayout().setRefreshing(true);
                }
            }, 3000);
        }
    }

    protected void hideRefreshing() {
        if (getSwipeRefreshLayout() != null) {
            if (getSwipeRefreshLayout().isRefreshing()) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getSwipeRefreshLayout().setRefreshing(false);
                    }
                }, 3000);
            }
        }
    }

    protected abstract void request();

    protected abstract void refresh();

    protected abstract void loadMoreData();

    protected abstract void setAdapter();

    /** 获取RecyclerViewAdapter */
    protected abstract YfRecyclerViewAdapter getAdapter();

    final String strLoading = "正在拼命加载中...";
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
                isLoadMore = true;
                loadMoreData();
                pageNo++;
                request();
                changeMode(REQ_MODE_LOAD_MORE);
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

    /** 显示进度条（初始化进度条[弹出框或YfAdapter的中心转]、SWL下拉刷新进度条、SWL加载更多进度条） */
    protected void showProgress() {
        YfRecyclerViewAdapter adapter = getAdapter();
        /*如果有数据，则显示SWL的进度条或者悬浮进度条，无数据则显示中部*/
        if (hasData()) {
            /*Refresh已经会转了，不需要再show*/
            if (currentReqMode == REQ_MODE_LOAD_MORE) {
                showRefreshing();/*浮刷*/
            }
        } else {/*无数据，不可能有加载更多*/
            /*下拉刷新*/
            if (adapter.mMode == YfListMode.MODE_LOADING) {/*如果上一次还没请求完，则*/
                hideRefreshing();
            }
            /*新进*/
            adapter.changeMode(YfListMode.MODE_LOADING);/*全刷*/
        }
    }

    /** 隐藏进度条 */
    protected void hideProgress() {
        hideRefreshing();
    }

    /** 数据集合中是否有数据，作为进度条展示形态的依据 */
    private boolean hasData() {
        YfRecyclerViewAdapter adapter = getAdapter();
        if (adapter != null && adapter.getData() != null && adapter.getData().size() > 0) {
            return true;
        }
        return false;
    }

    //如果界面内，有切换请求，在发起新请求之前，需要调用resetProgress()
    protected void resetProgress() {
        currentReqMode = REQ_MODE_INIT;
        getAdapter().setData(null);/*切换完，清空原来的数据*/
        hideProgress();/*切换，所以需要关闭之前的进度条*/
    }

    protected void setData(List data) {
        if (!isLoadMore) {
            getAdapter().setData(data);
        } else {
            getAdapter().addData(data);
        }
    }


}
