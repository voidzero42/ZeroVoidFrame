package cc.zerovoid.game.gobang;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import cc.zerovoid.view.drawLine.ZvLine;

/**
 * Created by Administrator on 2016/3/21.
 */
public class GoBangActivity extends Activity {
    private Screen screen;
    private GameView gameView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getScreen();//这里这么写，怎么你不去死，TMD对于另一个类那么重要的参数，就这样写？
        setContentView(getView());
    }

    private View getView() {
        View view = null;
//        gameView = new GameView(this);
//        view=gameView;
        view=new ZvLine(this);
        return view;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        menu.add("重新开始");
        menu.add("退出");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals("重新开始")) {
            gameView.restartGame();
        } else if (item.getTitle().equals("退出")) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 得到屏幕的大小
     */
    private void getScreen() {
        int height = this.getWindowManager().getDefaultDisplay().getHeight();
        int width = this.getWindowManager().getDefaultDisplay().getWidth();
        screen = new Screen(width, height);
    }
}
