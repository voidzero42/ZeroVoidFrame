package com.zerovoid.lib.view.other;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TabWidget;
import android.widget.TextView;

public class BadgeView extends TextView {
    private boolean mHideOnNull = true;

    public BadgeView(Context context) {
        this(context, null);
    }

    public BadgeView(Context context, AttributeSet attrs) {
        this(context, attrs, 16842884);
    }

    public BadgeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    private void init() {
        if (!(getLayoutParams() instanceof FrameLayout.LayoutParams)) {
            FrameLayout.LayoutParams layoutParams =
                    new FrameLayout.LayoutParams(
                            -2,
                            -2,
                            53);
            setLayoutParams(layoutParams);
        }

        setTextColor(-1);
        setTypeface(Typeface.DEFAULT_BOLD);
        setTextSize(2, 11.0F);
        setPadding(dip2Px(5.0F), dip2Px(1.0F), dip2Px(5.0F), dip2Px(1.0F));
        setBackground(9, Color.parseColor("#d3321b"));
        setGravity(17);
        setHideOnNull(true);
        setBadgeCount(0);
    }

    public void setBackground(int dipRadius, int badgeColor) {
        int radius = dip2Px(dipRadius);
        float[] radiusArray = {radius, radius, radius, radius, radius, radius, radius, radius};
        RoundRectShape roundRect = new RoundRectShape(radiusArray, null, null);
        ShapeDrawable bgDrawable = new ShapeDrawable(roundRect);
        bgDrawable.getPaint().setColor(badgeColor);
        setBackgroundDrawable(bgDrawable);
    }

    public boolean isHideOnNull() {
        return this.mHideOnNull;
    }

    public void setHideOnNull(boolean hideOnNull) {
        this.mHideOnNull = hideOnNull;
        setText(getText());
    }

    public void setText(CharSequence text, BufferType type) {
        if ((isHideOnNull()) && (((text == null) || (text.toString().equalsIgnoreCase("0")))))
            setVisibility(View.GONE);
        else {
            setVisibility(View.VISIBLE);
        }
        super.setText(text, type);
    }

    public void setBadgeCount(int count) {
        setText(String.valueOf(count));
    }

    public Integer getBadgeCount() {
        if (getText() == null) {
            return null;
        }
        String text = getText().toString();
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
        }
        return null;
    }

    public void setBadgeGravity(int gravity) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
        params.gravity = gravity;
        setLayoutParams(params);
    }

    public int getBadgeGravity() {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
        return params.gravity;
    }

    public void setBadgeMargin(int dipMargin) {
        setBadgeMargin(dipMargin, dipMargin, dipMargin, dipMargin);
    }

    public void setBadgeMargin(int leftDipMargin, int topDipMargin, int rightDipMargin, int bottomDipMargin) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
        params.leftMargin = dip2Px(leftDipMargin);
        params.topMargin = dip2Px(topDipMargin);
        params.rightMargin = dip2Px(rightDipMargin);
        params.bottomMargin = dip2Px(bottomDipMargin);
        setLayoutParams(params);
    }

    public int[] getBadgeMargin() {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
        return new int[]{params.leftMargin, params.topMargin, params.rightMargin, params.bottomMargin};
    }

    public void incrementBadgeCount(int increment) {
        Integer count = getBadgeCount();
        if (count == null)
            setBadgeCount(increment);
        else
            setBadgeCount(increment + count.intValue());
    }

    public void decrementBadgeCount(int decrement) {
        incrementBadgeCount(-decrement);
    }

    public void setTargetView(TabWidget target, int tabIndex) {
        View tabView = target.getChildTabViewAt(tabIndex);
        setTargetView(tabView);
    }

    public void setTargetView(View target) {
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }
        if (target == null) {
            return;
        }
        if (target.getParent() instanceof FrameLayout) {
            ((FrameLayout) target.getParent()).addView(this);
        } else if (target.getParent() instanceof ViewGroup) {
            ViewGroup parentContainer = (ViewGroup) target.getParent();
            int groupIndex = parentContainer.indexOfChild(target);
            parentContainer.removeView(target);
            FrameLayout badgeContainer = new FrameLayout(getContext());
            ViewGroup.LayoutParams parentlayoutParams = target.getLayoutParams();
            parentContainer.addView(badgeContainer, groupIndex, parentlayoutParams);
            badgeContainer.addView(target);
            badgeContainer.addView(this);
        } else if (target.getParent() == null) {
            Log.e(super.getClass().getSimpleName(), "ParentView is needed");
        }
    }

    private int dip2Px(float dip) {
        return (int) (dip * getContext().getResources().getDisplayMetrics().density + 0.5F);
    }
}

