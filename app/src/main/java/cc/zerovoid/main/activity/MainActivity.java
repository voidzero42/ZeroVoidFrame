package cc.zerovoid.main.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import cc.zerovoid.main.adapter.RvAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zerovoid.zerovoidframe.R;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.zerovoid.main.biz.DataHelper;

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
        testJson();
        initSupportDesign();
        initRecycleView();
    }

    private void testJson() {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        map.put("token1", "11111111");
        map.put("token2", "222");
        map.put("token3", "333");
        map.put("token4", "44");
        map.put("token5", "15551");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<HashMap<String, String>>>() {
        }.getType();
        gson.toJson(list);
        Log.e(MainActivity.class.getSimpleName(), gson.toString());
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
        rvContent.setAdapter(new RvAdapter(DataHelper.getData(), this));
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
