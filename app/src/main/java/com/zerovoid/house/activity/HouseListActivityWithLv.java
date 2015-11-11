package com.zerovoid.house.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.zerovoid.common.util.DialogHelper;
import com.zerovoid.common.util.ToastHelper;
import com.zerovoid.house.adapter.HouseExListViewAdapter;
import com.zerovoid.house.adapter.HouseLvAdapter;
import com.zerovoid.house.biz.HouseHttpBiz;
import com.zerovoid.house.model.HouseBean;
import com.zerovoid.http.VollyHelperNew;
import com.zerovoid.zerovoidframe.R;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/10.
 */
public class HouseListActivityWithLv extends Activity {


    @Bind(R.id.lvHouse)
    ListView lvHouse;
    private HouseLvAdapter adapter;
    private List<HouseBean> mHouseList;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_list_lv);
        ButterKnife.bind(this);
        requestHouseList();
        setAdapter();
    }


    private void setAdapter() {
        adapter = new HouseLvAdapter(this);
        lvHouse.setAdapter(adapter);
    }

    private void requestHouseList() {
        dialog = DialogHelper.getInstance().createLoadingDialog(this, "正在加载中");
        dialog.show();
        HouseHttpBiz.requestHouseList(new VollyHelperNew.ResponseCallBack() {
            @Override
            public void handleResponse(JSONObject response, int errCode) {
                dialog.dismiss();
                if (response != null) {
                    ToastHelper.getInstance()._toast(response.toString());
                    mHouseList = HouseHttpBiz.handleHouseList(response);
                    if (mHouseList != null) {
                        adapter.setList(mHouseList);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }


}
