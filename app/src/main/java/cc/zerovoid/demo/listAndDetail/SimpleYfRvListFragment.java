package cc.zerovoid.demo.listAndDetail;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zerovoid.lib.fragment.BaseYfRecyclerViewFragment;
import com.zerovoid.lib.view.yfRecyclerView.YfListRecyclerView;
import com.zerovoid.lib.view.yfRecyclerView.YfRecyclerViewAdapter;
import com.zerovoid.zerovoidframe.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 简单YF的列表的演示
 * Created by zv on 2016/2/16.
 */
public class SimpleYfRvListFragment extends BaseYfRecyclerViewFragment {

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
//    @Bind(R.id.recyclerView)
//    YfListRecyclerView mRecyclerView;
    SimpleYfRvAdapter adapter;

    @Override
    protected View onCreateViewInstead(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_recycleview, null);
        ButterKnife.bind(this, view);
        setAdapter();
//        mRecyclerView.enableAutoLoadMore(SimpleYfRvListFragment.this);
        return view;
    }

    private void setAdapter() {
        if (onInitListener != null) {
            onInitListener.onInit();
        }
    }

    private OnInitListener onInitListener;

    public void setOnInitListener(OnInitListener onInitListener) {
        this.onInitListener = onInitListener;
    }

    public interface OnInitListener {
        void onInit();
    }

    @Override
    protected SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
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

    private void request() {

    }

    @Override
    protected YfRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public boolean onBackBtnClick() {
        return false;
    }
}
