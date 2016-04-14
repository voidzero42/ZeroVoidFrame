package cc.zerovoid.house.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cc.zerovoid.house.model.HouseBean;
import com.zerovoid.zerovoidframe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/11.
 */
public class HouseLvAdapter extends BaseAdapter {

    private Context mContext;
    private List<HouseBean> mList = new ArrayList<>();

    public HouseLvAdapter(Context context) {
        this.mContext = context;
    }

    public void setList(List<HouseBean> list) {
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_house, null);
            holder.tvHouseName = (TextView) convertView.findViewById(R.id.tvHouseName);
            holder.tvFamily = (TextView) convertView.findViewById(R.id.tvFamily);
            holder.tvTenants = (TextView) convertView.findViewById(R.id.tvTenants);
            holder.tvInvite = (TextView) convertView.findViewById(R.id.tvInvite);
            holder.ivJumpDetailFamily = (ImageView) convertView.findViewById(R.id.ivJumpDetailFamily);
            holder.ivJumpDetailTenants = (ImageView) convertView.findViewById(R.id.ivJumpDetailTenants);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HouseBean hb = mList.get(position);
        holder.tvHouseName.setText(hb.getHouseInfo());
        holder.tvInvite.setOnClickListener(listener);
        handleUserInfo(hb.getFamily(), holder.tvFamily, holder.ivJumpDetailFamily);
        handleUserInfo(hb.getTenant(), holder.tvTenants, holder.ivJumpDetailTenants);
        return convertView;
    }

    class ViewHolder {
        /*房屋信息*/
        TextView tvHouseName;
        /*亲属信息列表*/
        TextView tvFamily;
        /*租客信息列表*/
        TextView tvTenants;
        /*邀请*/
        TextView tvInvite;
        /*进入详情页*/
        ImageView ivJumpDetailFamily;
        ImageView ivJumpDetailTenants;
    }

    private void handleUserInfo(String usernames, TextView tvNames, ImageView ivJumpDetail) {
        if ("".equals(usernames)) {
            tvNames.setText("无");
            ivJumpDetail.setVisibility(View.VISIBLE);
            ivJumpDetail.setOnClickListener(null);
        } else {
            tvNames.setText(usernames);
            ivJumpDetail.setVisibility(View.VISIBLE);
            ivJumpDetail.setOnClickListener(listener);
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
