package com.zerovoid.lib.view.yfRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.zerovoid.library.R;

import java.util.List;


/**
 * 孙子
 * Created by yefeng on 8/5/15.
 * github:yefengfreedom
 */
public abstract class RecycleViewAdapter<T> extends YfListAdapter<T> {

    public RecycleViewAdapter(List<T> data) {
        super(data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.yf_view_footer, parent, false);
        return new FooterViewHolder(view);
    }

    private static final class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView mText;

        public FooterViewHolder(View itemView) {
            super(itemView);
            mText = (TextView) itemView.findViewById(R.id.tv_footer);
        }
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindFooterViewHolder(holder, position);
        TextView tv = (TextView) holder.itemView.findViewById(R.id.tv_footer);
        if (tv != null) {
            tv.setText(String.valueOf(this.getFooters().get(position)));
        }
        holder.itemView.setTag(this.getFooters().get(position).toString());
    }

    @Override
    public RecyclerView.ViewHolder onCreateEmptyViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.yf_view_empty, parent, false);
        return new EmptyViewHolder(view);
    }

    private static final class EmptyViewHolder extends RecyclerView.ViewHolder {
        TextView mText;

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateLoadingViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.yf_view_loading, parent, false);
        return new LoadingViewHoler(view);
    }

    private static final class LoadingViewHoler extends RecyclerView.ViewHolder {
        TextView mText;

        public LoadingViewHoler(View itemView) {
            super(itemView);
        }
    }
}
