package com.zerovoid.house.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zerovoid.house.model.HouseBean;
import com.zerovoid.zerovoidframe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 房屋列表的适配器
 * 其实根据设计，这次使用ListView就足够了。但是房屋管理作为一个domain，实际上ExpandableListView更符合大部分，可
 *
 * @author Administrator
 */
public class HouseExListViewAdapter extends BaseExpandableListAdapter {

    private List<String> mGroupList=new ArrayList<>();
    private List<List<String>> mChildList=new ArrayList<>();
    private Context mContext;

    public HouseExListViewAdapter(Context context) {
        this.mContext = context;
    }

    public void setList(List<String> groupList, List<List<String>> childList) {
        this.mGroupList = groupList;
        this.mChildList = childList;
    }

    public int getGroupCount() {
        return mGroupList.size();
    }

    public Object getGroup(int groupPosition) {
        return mGroupList.get(groupPosition);
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public int getChildrenCount(int groupPosition) {
        return mChildList.size();
    }

    public Object getChild(int groupPosition, int childPosition) {
        return mChildList.get(groupPosition).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public boolean hasStableIds() {
        return true;
    }

    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        GroupViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new GroupViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_elv_group_house, null);
            viewHolder.tvHouseName = (TextView) convertView
                    .findViewById(R.id.tvHouseName);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GroupViewHolder) convertView.getTag();
        }
        viewHolder.tvHouseName.setText(mGroupList.get(groupPosition)
        );
        return convertView;
    }


    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        String usernames = mChildList.get(groupPosition).get(childPosition);

        if (convertView == null) {
            holder = new ChildViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_elv_child_house, null);
            holder.tvRoleType = (TextView) convertView
                    .findViewById(R.id.tvBuilding);
            holder.tvUserList = (TextView) convertView
                    .findViewById(R.id.tvRoomNumber);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }

        holder.tvRoleType.setText(getRoleTypeWithPos(childPosition));


        if (hasUserByResult(usernames)) {
            holder.tvUserList.setText(usernames);
            holder.ivJumpDetail.setOnClickListener(listener);
            holder.ivJumpDetail.setVisibility(View.VISIBLE);
        } else {//如果没有用户，显示无，不显示点击按钮，且不能点击；
            holder.tvUserList.setText("无");
            holder.ivJumpDetail.setOnClickListener(null);
            holder.ivJumpDetail.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupViewHolder {
        /*房屋名称*/
        TextView tvHouseName;
    }

    class ChildViewHolder {
        /*角色类型*/
        TextView tvRoleType;
        /*用户列表*/
        TextView tvUserList;
        /*跳转到用户列表页*/
        ImageView ivJumpDetail;
    }

    /** 根据position获取角色类型 */
    private String getRoleTypeWithPos(int pos) {
        if (pos == 0) {//根据设计，第一行显示家属，第二行显示租客
            return HouseBean.ROLE_TYPE_FAMILY;
        }
        return HouseBean.ROLE_TYPE_TENANT;
    }

    private boolean hasUserByResult(String usernames) {
        if ("".equals(usernames)) {
            return false;
        }
        return true;
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

}
