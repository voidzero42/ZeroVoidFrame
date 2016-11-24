package cc.zerovoid.main.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.zerovoid.zerovoidframe.R;

import cc.zerovoid.databinding.fragment.DataBindingFragment;

/**
 * <p>
 * Created by 吴格非 on 2016-11-23.
 * <p>
 *
 * @author 吴格非
 * @since v1.0.0
 */

public class CommonFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_common_frgm);
        FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
        tran.add(R.id.container, new DataBindingFragment());
        tran.commit();
    }


}
