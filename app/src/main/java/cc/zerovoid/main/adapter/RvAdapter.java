package cc.zerovoid.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * 主界面的列表的适配器
 * <p/>
 * Created by zv on 2015/11/30.
 *
 * @author zv
 */
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.RvViewHolder> {
    private Context mContext;
    private List<HashMap<String, Object>> mData;

    public RvAdapter(List<HashMap<String, Object>> data, Context context) {
        this.mData = data;
        this.mContext = context;
    }

    @Override
    public RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RvViewHolder(new TextView(mContext), mContext, mData);
    }

    @Override
    public void onBindViewHolder(RvViewHolder holder, int position) {
        holder.tv.setText(String.valueOf(mData.get(position).get("title")));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    /** 自定义的Holder */
    static class RvViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;

        RvViewHolder(View itemView, final Context context, final List<HashMap<String, Object>> data) {
            super(itemView);
            tv = (TextView) itemView;
            tv.setPadding(15, 15, 15, 15);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Intent intent = new Intent(context, (Class<?>) data.get(pos).get("clazz"));
                    context.startActivity(intent);
                }
            });
        }
    }
}
