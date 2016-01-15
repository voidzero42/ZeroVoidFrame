package com.zerovoid.lib.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by YangWei
 * on 2015/12/28.
 */
public class TextWatcherNumer implements TextWatcher {

    EditText editText;
    private int type;

    public TextWatcherNumer(EditText editText,int type) {
        this.editText = editText;
        this.type=type;
    }

    @Override
    public void afterTextChanged(Editable v) {

        switch (type){
            case 0:
                percent(v);
                break;
            case 1:
                month(v);
                break;
        }


    }

    /**
     * 限制：首位不为 0，
     * @param v
     */
    private void month(Editable v) {
        CharSequence s=v.toString();

        if(StringUtil.isBlank(s.toString())){
            return;
        }
        if(s.equals("0")){
            editText.setText("");
            return;
        }
    }

    /**
     * 限制：首位为0时，变为0.
     * @param v
     */
    private void percent(Editable v) {
        CharSequence s=v.toString();
        if(StringUtil.isBlank(s.toString())){
            return;
        }
        if (s.toString().contains(".")) {
            if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                s = s.toString().subSequence(0,
                        s.toString().indexOf(".") + 3);
                editText.setText(s);
                editText.setSelection(s.length());
            }
        }
        if (s.toString().trim().substring(0).equals(".")) {
            s = "0" + s;
            editText.setText(s);
            editText.setSelection(2);
        }
        if (s.toString().startsWith("0")
                && s.toString().trim().length() > 1) {
            if (!s.toString().substring(1, 2).equals(".")) {
                editText.setText(s.subSequence(0, 1));
                editText.setSelection(1);
                return;
            }
        }
        double d = Double.parseDouble(s.toString());
        if(Double.compare(d,100.0)>0){
            editText.setText("100");
        }
        editText.setSelection(editText.getText().toString().length());
    }

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before,
                              int count) {

    }
}
