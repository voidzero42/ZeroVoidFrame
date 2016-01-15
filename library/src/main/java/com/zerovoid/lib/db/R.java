package com.zerovoid.lib.db;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class R {

    public Cursor cursor;

    static R getR() {
        R r = new R();
        return r;
    }

    public List<Map<String, String>> toArray() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map;
        if (cursor.moveToFirst()) {
            map = new HashMap<>();
            int cloumnCount = cursor.getColumnCount();
            for (int i = 0; i < cloumnCount; i++) {
                map.put(cursor.getColumnName(i), cursor.getString(i));
            }
            list.add(map);
        }
        return list;
    }
}
