package cc.zerovoid.view.wheelView;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;


/**
 * 适配列表冲突问题
 */
public class WheelViewPager extends ViewPager {

    protected static final String TAG = WheelViewPager.class.getSimpleName();

    private Float mDownX;
    private Float mDownY;

    public WheelViewPager(Context context) {
        super(context);
    }

    public WheelViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                changeParentDisallowInterceptState(getParent(), false);
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(ev.getX() - mDownX) > Math.abs(ev.getY() - mDownY)) {
                    changeParentDisallowInterceptState(getParent(), true);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                changeParentDisallowInterceptState(getParent(), false);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 改变父空间触摸事件拦截状态
     *
     * @param parentView
     * @param isDisallow
     */
    public static void changeParentDisallowInterceptState(
            ViewParent parentView, boolean isDisallow) {
        if (parentView == null) {
            return;
        }
        if (parentView.getParent() == null) {
            return;
        }
        // 改变触摸拦截状态
        parentView.requestDisallowInterceptTouchEvent(isDisallow);
        changeParentDisallowInterceptState(parentView.getParent(), isDisallow);
    }
}
