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

import cc.zerovoid.databinding.bean.UserBean;

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
        View view = inflater.inflate(R.layout.frgm_databinding, null);
        DataBindingUtil.setContentView(getActivity(), R.layout.frgm_databinding);
//        DataBindingFragmentBinding binding=DataBindingUtil.inflate(inflater, R.layout.frgm_databinding, container, false);
        FrgmDatabindingBinding bingding=DataBindingUtil.inflate(inflater, R.layout.frgm_databinding, container, false);
        bingding.setUser(new UserBean("吴格非","20"));
        return view;
    }


}
