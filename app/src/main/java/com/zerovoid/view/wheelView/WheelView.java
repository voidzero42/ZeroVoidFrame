package com.zerovoid.view.wheelView;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zerovoid.zerovoidframe.R;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 公有轮播图实现
 */
public class WheelView extends RelativeLayout {
    // begin 轮播
    private RelativeLayout mRelHeadWheel;
    private WheelViewPager mHeadWheelImage;
    private LinearLayout mLinHeadWheelIndicator;
    private Timer mTimer;
    private boolean isEnd = false;
    private Context mContext;
    private PagerAdapter mPagerAdapter;
    private List<Map<String, Object>> mList;

    // end 轮播
    public interface OnPagerClick {
        public void onImageClick();

    }

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        //TODO 这里需要如此？
        inflater.inflate(R.layout.wheel_view, this, true);
    }


    /**
     * 初始化轮播图片
     */
    public void initWheel(List<Map<String, Object>> list, PagerAdapter pagerAdapter) {
        this.mList = list;
        this.mPagerAdapter = pagerAdapter;
        fillHeadWheelData(list);
    }


    // begin 轮播

    /**
     * 填充头部轮播数据
     *
     * @param
     */
    private void fillHeadWheelData(List<Map<String, Object>> list) {
        if (list == null || list.size() == 0) {
            // 隐藏头部轮播
            mRelHeadWheel.setVisibility(View.GONE);
            return;
        }
        mRelHeadWheel.setVisibility(View.VISIBLE);
        // 适配器设置
        //mHeadWheelImage.setAdapter(new HeadWheelAdapter(mContext,list));
        mHeadWheelImage.setAdapter(mPagerAdapter);
        mHeadWheelImage.setCurrentItem(0);
        // 监听设置
        mHeadWheelImage.setOnPageChangeListener(new OnWheelChangeListener(list));
        // 设置轮播高亮标志
        initWheelIndicatorPoint(list.size());
        // 发送轮播消息
        sendWheelMessage(list.size());
    }

    /**
     * 发送轮播消息
     */
    private void sendWheelMessage(final int size) {
        if (mTimer != null) {
            return;
        }
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                if (mContext == null) {
                    return;
                }
                ((Activity) mContext).runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (isEnd) {
                            mHeadWheelImage.setCurrentItem(0, true);
                        } else {
                            mHeadWheelImage.setCurrentItem(
                                    mHeadWheelImage.getCurrentItem() + 1, true);
                        }
                        if (mHeadWheelImage.getCurrentItem() == 0) {
                            isEnd = false;
                        } else if (mHeadWheelImage.getCurrentItem() + 1 == size) {
                            isEnd = true;
                        }
                    }
                });
            }
        };
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(task, 2000, 4000);
    }

    /**
     * 初始化头部导航点
     */
    private void initWheelIndicatorPoint(int pointCount) {
        mLinHeadWheelIndicator.removeAllViews();
        for (int i = 0; i < pointCount; i++) {
            ImageView imvPoint = new ImageView(mContext);
            if (i == 0) {
//                imvPoint.setImageResource(R.drawable.ic_indicator_on);
            } else {
//                imvPoint.setImageResource(R.drawable.ic_indicator_off);
            }
            imvPoint.setPadding(6, 0, 6, 0);
            mLinHeadWheelIndicator.addView(imvPoint);
        }
    }

    /**
     * 轮播切换监听
     *
     * @author cgy
     */
    private class OnWheelChangeListener implements OnPageChangeListener {
        private List<Map<String, Object>> mList = null;

        public OnWheelChangeListener(List<Map<String, Object>> list) {
            this.mList = list;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            try {
                // 轮播高亮
                for (int i = 0; i < mLinHeadWheelIndicator.getChildCount(); i++) {
                    ImageView imvPoint = (ImageView) mLinHeadWheelIndicator
                            .getChildAt(i);
                    if (imvPoint != null) {
                        if (mList.size() != 0 && position % mList.size() == i) {
                            //TODO 这里需要替换图片
//                            imvPoint.setImageResource(R.drawable.ic_indicator_on);
                        } else {
//                            imvPoint.setImageResource(R.drawable.ic_indicator_off);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    // end 轮播

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mRelHeadWheel = (RelativeLayout) findViewById(R.id.rel_vip_container);
        mHeadWheelImage = (WheelViewPager) findViewById(R.id.vip_wheel);
        mLinHeadWheelIndicator = (LinearLayout) findViewById(R.id.lin_vip_indicator);
    }
}
