package com.zerovoid.lib.util;

import android.widget.EditText;

/**
 * Created by Administrator on 2016/1/5.
 */
public class EditTextUtil {

    public static void setText(EditText et, String txt) {
        et.setText(txt);
        if (!StringUtil.isBlank(txt)) {
            et.setSelection(txt.length());
        }
    }
}
