package com.zerovoid.demo.listAndDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;

import com.zerovoid.lib.activity.BaseYfRecyclerViewActivity;
import com.zerovoid.lib.http.VolleyHttpUtil;
import com.zerovoid.lib.view.yfRecyclerView.YfListMode;
import com.zerovoid.lib.view.yfRecyclerView.YfRecyclerViewAdapter;
import com.zerovoid.lib.view.yfRecyclerView.YfListRecyclerView;
import com.zerovoid.orange.http.OrangeHttpBiz;
import com.zerovoid.zerovoidframe.R;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 列表演示，一般包含标题栏（标题、后退键）、列表、item（CheckBox、Button、ImageView）、顶部拉扯、底部组件、嵌套滚动
 * 下拉刷新、上拉加载、
 * 常见的：聊天列表、商品列表、城市列表
 * RecyclerView、ListView，两种
 * Created by zv on 2016/2/16.
 */
public class SimpleYfRvListActivity extends BaseYfRecyclerViewActivity {
    //TODO ListView RecyclerView SwipeLayout 其他下拉刷新组件
    //YfAdapter的三个mode（无数据加载中、上拉或下拉加载中、网络问题、无数据）和EmptyLayout（无数据加载中、有数据加载中[弹出框]、网络问题、无数据）对应，如果不用YF呢？

    /** 顶部栏 */
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    /** 谷歌V4包提供的下拉刷新组件，配合RecyclerView使用 */
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    /** 替代ListView */
    @Bind(R.id.recyclerView)
    YfListRecyclerView mRecyclerView;
    /** yf的适配器 */
    private SimpleYfRvAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setAdapter();
        request();
    }

    @Override
    protected SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    /** 网络请求 */
    private void request() {
        if (adapter == null) {
            return;
        }
        showProgress();
        String url = "www.baidu.com";
        VolleyHttpUtil.getInstance().getWithCallback(url, new VolleyHttpUtil.ResponseCallBack() {
            @Override
            public void handleResponse(JSONObject response, int errCode) {
                hideRefreshing();
//                adapter.setTotalNum(getTotalNum());/*如果可以获取列表总数，则可以无需加载更多，即可知道到底没有*/
                if (!isLoadMore) {
                    adapter.setData(null);
                } else {
                    adapter.addData(null);
                }
            }
        });
    }

    private int getTotalNum() {
        return OrangeHttpBiz.getTotalNum(null);
    }

    //如果界面内，有切换请求，在发起新请求之前，需要调用resetProgress()
    private void resetProgress() {
        currentReqMode = REQ_MODE_INIT;
        adapter.setData(null);/*切换完，清空原来的数据*/
        hideProgress();/*切换，所以需要关闭之前的进度条*/
    }

    @Override
    protected void refresh() {
        currentReqMode = REQ_MODE_REFRESH;
        request();
    }

    @Override
    protected void loadMoreData() {
        currentReqMode = REQ_MODE_LOAD_MORE;
        request();
    }

    /** 显示进度条（初始化进度条[弹出框或YfAdapter的中心转]、SWL下拉刷新进度条、SWL加载更多进度条） */
    private void showProgress() {
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
    private void hideProgress() {
        hideRefreshing();
    }

    /** 数据集合中是否有数据，作为进度条展示形态的依据 */
    private boolean hasData() {
        if (adapter != null && adapter.getData() != null && adapter.getData().size() > 0) {
            return true;
        }
        return false;
    }

    private void setAdapter() {
        ArrayList<SimpleEntity> list = new ArrayList<>();
        adapter = new SimpleYfRvAdapter(list, this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected YfRecyclerViewAdapter getAdapter() {
        return adapter;
    }


}
