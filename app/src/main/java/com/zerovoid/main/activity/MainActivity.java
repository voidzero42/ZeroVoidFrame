package com.zerovoid.main.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.zerovoid.login.activity.VerifyCodeActivity;
import com.zerovoid.main.adapter.RvAdapter;
import com.zerovoid.screen.ScreenAdapterActivity;
import com.zerovoid.test.TestActivity;
import com.zerovoid.zerovoidframe.R;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 主界面，目录
 * <p/>
 * Create by zv on unknown,Modify by zv on 160126
 *
 * @author zv
 */
public class MainActivity extends AppCompatActivity {


    @Bind(R.id.rvContent)
    RecyclerView rvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        test();
        initSupportDesign();
        initRecycleView();
    }

    private void initSupportDesign() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initRecycleView() {
        rvContent.setLayoutManager(new GridLayoutManager(this, 2));
        rvContent.setAdapter(new RvAdapter(getData(), this));
    }

    private List<HashMap<String, Object>> getData() {
        List<HashMap<String, Object>> data = new ArrayList<>();
        data.add(getMap("1.屏幕适配器", ScreenAdapterActivity.class));
        data.add(getMap("2.验证码功能demo", VerifyCodeActivity.class));
        data.add(getMap("3.Test", TestActivity.class));
        return data;
    }

    private HashMap<String, Object> getMap(String title, Class clazz) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("clazz", clazz);
        return map;
    }

    private void test() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
