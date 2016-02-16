package com.zerovoid.demo.listAndDetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import com.zerovoid.lib.view.yfRecyclerView.YfRecyclerViewAdapter;
import com.zerovoid.zerovoidframe.R;

import java.util.ArrayList;

/**
 * 订单列表适配器
 * <p/>
 * Created by wgf on 2015/12/15.Modify by wgf on 160128
 *
 * @author wgf
 */
public class SimpleYfRvAdapter extends YfRecyclerViewAdapter<SimpleEntity> {

    private Context mContext;

    public SimpleYfRvAdapter(ArrayList<SimpleEntity> data, Context context) {
        super(data);
        this.mData = (ArrayList) data;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_simple, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindDataViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder mHolder = (MyViewHolder) holder;
        SimpleEntity ob = mData.get(position);
    }

    private String getFormatString(String content, int id) {
        return String.format(mContext.getString(id), content);
    }


    private static class MyViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlDeliver;
        private View itemView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
//            rlDeliver = (RelativeLayout) itemView.findViewById(R.id.rlDeliver);

        }

    }
}
