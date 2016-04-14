package cc.zerovoid.house.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.ExpandableListView;

import cc.zerovoid.house.adapter.HouseExListViewAdapter;
import cc.zerovoid.house.biz.HouseHttpBiz;
import cc.zerovoid.house.model.HouseBean;
import com.zerovoid.lib.http.VolleyHttpUtil;
import com.zerovoid.lib.util.ToastHelper;
import com.zerovoid.lib.view.other.DialogHelper;
import com.zerovoid.zerovoidframe.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/10.
 */
public class HouseListActivityWithElv extends Activity {


    @Bind(R.id.elvHouse)
    ExpandableListView elvHouse;
    private HouseExListViewAdapter adapter;
    private List<HouseBean> mHouseList;
    private List<String> mGroupList = new ArrayList<>();
    private List<List<String>> mChildList = new ArrayList<>();
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_list_elv);
        ButterKnife.bind(this);
        requestHouseList();
        setAdapter();
    }


    private void setAdapter() {
        adapter = new HouseExListViewAdapter(this);
        elvHouse.setAdapter(adapter);
    }

    private void requestHouseList() {
        dialog = DialogHelper.getInstance().createLoadingDialog(this, "正在加载中");
        dialog.show();
        HouseHttpBiz.requestHouseList(new VolleyHttpUtil.ResponseCallBack(){
            @Override
            public void handleResponse(JSONObject response, int errCode) {
                dialog.dismiss();
                if (response != null) {
                    ToastHelper.getInstance()._toast(response.toString());
                    mHouseList = HouseHttpBiz.handleHouseList(response);
                    if (mHouseList != null) {
                        transDataSource();
                        adapter.setList(mGroupList, mChildList);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    /** 数据转换器，如果服务端获取的数据和Adapter中的数据结构不同，可以通过这个进行转换 */
    private void transDataSource() {
        if (mHouseList != null) {
            for (int i = 0; i < mHouseList.size(); i++) {
                HouseBean hb = mHouseList.get(i);
                mGroupList.add(hb.getHouseInfo());
                ArrayList<String> list = new ArrayList<>();
                list.add(hb.getFamily());
                list.add(hb.getTenant());
                mChildList.add(list);
            }
        }
    }
}
