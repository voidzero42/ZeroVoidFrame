package com.zerovoid.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2015/11/30.
 */
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.RvViewHolder> {


    @Override
    public RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RvViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class RvViewHolder extends RecyclerView.ViewHolder {

        public RvViewHolder(View itemView) {
            super(itemView);
        }
    }
}
