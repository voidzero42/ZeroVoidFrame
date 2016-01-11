package com.zerovoid.common.view.yfRecyclerView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.zerovoid.zerovoidframe.R;


/**
 * Created by yefeng on 8/7/15.
 * github:yefengfreedom
 * this is a list view base on recycler view,with auto load more model,divider
 */
public class YfListRecyclerView extends RecyclerView {

    public static final int WRAP_CONTENT = -1;

    private static final String TAG = "YfListView";

    private int mVisibleItemCount, mTotalItemCount, mFirstVisibleItemPosition, spanCount;
    private LinearLayoutManager mLayoutManager;
    private Adapter mAdapter;
    private Context mContext;

    public YfListRecyclerView(Context context) {
        super(context);
        mContext = context;
        initDefaultConfig(context);
    }

    public YfListRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefaultConfig(context);
    }

    public YfListRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initDefaultConfig(context);
    }

    private void initDefaultConfig(Context context) {
        setLayoutManager(context, YfListMode.LIST);
    }

    public void setGridLayoutManager(Context context, int spanCount) {
        this.spanCount = spanCount;
        setLayoutManager(context, YfListMode.GRID);
    }

    private void setLayoutManager(Context context, int type) {
        mLayoutManager = null;
        switch (type) {
            case YfListMode.LIST:
                mLayoutManager = new LinearLayoutManager(context);
                break;
            case YfListMode.GRID:
                mLayoutManager = new GridLayoutManager(context, spanCount);
                break;
        }
        setLayoutManager(mLayoutManager);
    }

    public void setDivider() {
        setDivider(R.drawable.divider, 1);
    }

    /**
     * set list divider
     *
     * @param dividerRes divider resource
     */
    public void setDivider(int dividerRes) {
        setDivider(dividerRes, WRAP_CONTENT);
    }

    /**
     * set list divider
     *
     * @param dividerRes    divider resource
     * @param dividerHeight divider height
     */
    public void setDivider(int dividerRes, int dividerHeight) {
        Drawable drawable = getResources().getDrawable(dividerRes);
        setDivider(drawable, dividerHeight);
    }

    /**
     * set list divider
     *
     * @param drawable      drawable
     * @param dividerHeight divider height
     */
    public void setDivider(final Drawable drawable, final int dividerHeight) {
        if (null == drawable) {
            throw new NullPointerException("drawable resource is null");
        }
        addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                int left = parent.getPaddingLeft();
                int right = parent.getWidth() - parent.getPaddingRight();

                int childCount = parent.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View child = parent.getChildAt(i);

                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                    int top = child.getBottom() + params.bottomMargin;
                    int bottom;
                    if (dividerHeight == WRAP_CONTENT) {
                        bottom = top + drawable.getIntrinsicHeight();
                    } else {
                        if (dividerHeight < 0) {
                            bottom = top;
                        } else {
                            bottom = top + dividerHeight;
                        }

                    }

                    drawable.setBounds(left, top, right, bottom);
                    drawable.draw(c);
                }
            }
        });
    }

    /**
     * set list divider
     *
     * @param drawable drawable
     */
    public void setDivider(Drawable drawable) {
        setDivider(drawable, WRAP_CONTENT);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        this.mAdapter = adapter;
        super.setAdapter(this.mAdapter);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        this.mLayoutManager = (LinearLayoutManager) layout;
        super.setLayoutManager(this.mLayoutManager);
    }

    private boolean isFirst = true;
    private boolean isScrolled = true;

    /**
     * enable list view auto load more
     *
     * @param loadMoreListener load more listener
     */
    public void enableAutoLoadMore(final YfListInterface.YfLoadMoreListener loadMoreListener) {

        addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (null == mLayoutManager || null == mAdapter) {
                    return;
                }
                mVisibleItemCount = mLayoutManager.getChildCount();
                mTotalItemCount = mLayoutManager.getItemCount();
                mFirstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
                if ((mVisibleItemCount + mFirstVisibleItemPosition) >= mTotalItemCount) {
                    if (null != loadMoreListener) {
//                        LogUtil.d(TAG, "loadMore");
                        loadMoreListener.loadMore();
                    }
                }
            }
        });
    }
}
