package cc.zerovoid.main.activity;

import cc.zerovoid.game.gobang.GoBangActivity;
import cc.zerovoid.login.activity.VerifyCodeActivity;
import cc.zerovoid.screen.ScreenAdapterActivity;
import cc.zerovoid.test.TestActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 处理主界面目录的数据源
 * Created by zv on 2016/3/21.
 */
public class DataHelper {
    public static List<HashMap<String, Object>> getData() {
        List<HashMap<String, Object>> data = new ArrayList<>();
        data.add(getMap("1.屏幕适配器", ScreenAdapterActivity.class));
        data.add(getMap("2.验证码功能demo", VerifyCodeActivity.class));
        data.add(getMap("3.Test", TestActivity.class));
        data.add(getMap("4.五子棋",GoBangActivity.class));
        return data;
    }

    public static HashMap<String, Object> getMap(String title, Class clazz) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("clazz", clazz);
        return map;
    }
}
