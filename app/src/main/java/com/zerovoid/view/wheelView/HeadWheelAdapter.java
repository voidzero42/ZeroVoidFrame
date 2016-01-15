package com.zerovoid.view.wheelView;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import java.util.List;
import java.util.Map;

/**
 * 头部轮播适配器
 */
public class HeadWheelAdapter extends PagerAdapter {

    private Context mContext;
    private List<Map<String, Object>> mList;

    public HeadWheelAdapter(Context context, List<Map<String, Object>> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imvItem = obtainHeadWheelItems(mList.get(position % mList.size()));
        container.addView(imvItem);
        return imvItem;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 获取头部轮播子项
     *
     * @param entity 数据
     * @return
     */
    private ImageView obtainHeadWheelItems(final Map<String, Object> entity) {

        ImageView imvItem = new ImageView(mContext);
        imvItem.setScaleType(ScaleType.FIT_XY);
        imvItem.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", entity.get("id").toString());
//					Toast.makeText(mContext, "图片ID:" + entity.get("id").toString(), Toast.LENGTH_SHORT).show();
                //自定义跳转
            }
        });
        //TODO 这里肯定要写成接口传入，解耦，不然离了某个组件，还不能加载图片了？
//        DisplayImageOptions.Builder options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.ic_defalt_image);
//        ImageLoader.getInstance().displayImage(entity.get("IMAGE_URL").toString(), imvItem, options.build());
        return imvItem;
    }

}