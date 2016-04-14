package cc.zerovoid.demo.listAndDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;

import com.zerovoid.lib.activity.BaseYfRecyclerViewActivity;
import com.zerovoid.lib.http.VolleyHttpUtil;
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

    //TODO 继续改进，加入Adapter可以判断是否有数据，那么show hide progress完全可以放到父类去了
    //将与业务逻辑无关的代码抽出，此类可以专心完成业务逻辑部分；至少Dialog和Progress是与业务逻辑无关的部分；可以称之为ZV风格，必然出现。而本身这个也是基于YF风格，而YF又是基于RV。所以是层层抽象，直到最简单
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

    /** 网络请求、数据库请求、本地数据 */
    @Override
    protected void request() {
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
                //if success
                setData(null);/*这么写，如何让人直到要调用这个类，所以应该怎么写？对了，监听器，必须时间数据请求结束监听器，*/
            }
        });
    }

    //TODO PAGE_NO和PAGE_SIZE肯定不可能是通用的，即需要服务端配合，可能就是没有分页。而且怎么知道父类有PAGE_NO？

    /** 需要服务端提供这个接口 */
    private int getTotalNum() {
        return OrangeHttpBiz.getTotalNum(null);
    }


    @Override
    protected void refresh() {
//        request();//TODO 将这块也写成接口？列表，下拉上拉之后，必然要获取数据（无论是网络、数据库、内存，随便），还需要人来写？
    }

    @Override
    protected void loadMoreData() {
//        request();
    }

    @Override
    protected void setAdapter() {
        ArrayList<SimpleEntity> list = new ArrayList<>();
        adapter = new SimpleYfRvAdapter(list, this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected YfRecyclerViewAdapter getAdapter() {
        return adapter;
    }


}
