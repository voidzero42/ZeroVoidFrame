package cc.zerovoid.databinding.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zerovoid.zerovoidframe.R;
import com.zerovoid.zerovoidframe.databinding.FrgmDatabindingBinding;

import java.util.ArrayList;
import java.util.HashMap;

import cc.zerovoid.databinding.bean.UserBean;
import cc.zerovoid.databinding.handler.MyEventHandler;
import cc.zerovoid.databinding.handler.MyPresenter;
import cc.zerovoid.databinding.handler.MyTask;

/**
 * <p>
 * Created by 吴格非 on 2016-11-23.
 * <p>
 *
 * @author 吴格非
 * @since v1.0.0
 */

public class DataBindingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.frgm_databinding, null);
//        DataBindingUtil.setContentView(getActivity(), R.layout.frgm_databinding);
//        DataBindingFragmentBinding binding=DataBindingUtil.inflate(inflater, R.layout.frgm_databinding, container, false);
        FrgmDatabindingBinding binding = DataBindingUtil.inflate(inflater, R.layout.frgm_databinding, container, false);
        initBindingData(binding);
        return binding.getRoot();
    }

    private void initBindingData(FrgmDatabindingBinding binding) {

        binding.setMyStr("我的字符串");
        binding.setListPos(0);
        binding.setMapKey("name");
        binding.setUser(new UserBean("吴格非", "20", false));

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("《安卓从入门到放弃》");
        binding.setMyList(arrayList);

        HashMap<String, String> map = new HashMap<>();
        map.put("myMapKey", "我也不知道什么是Map！");
        map.put("name", "我也不知道什么是Mapnamenamenamename！");
        ArrayList<HashMap<String, String>> listMap = new ArrayList<>();
        listMap.add(map);

        binding.setMyListMap(listMap);
        binding.setMyMap(map);

        binding.setMyHandler(new MyEventHandler());
        binding.setMyPresenter(new MyPresenter());
        binding.setMyTask(new MyTask());
    }


}
