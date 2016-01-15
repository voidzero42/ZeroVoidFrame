package com.zerovoid.lib.view.other;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zerovoid.lib.util.StringUtil;
import com.zerovoid.library.R;


public class OneBtnConfirmDialog extends Dialog {

    private Context context;
    private String title;
    private String message;
    private String btnConfirm;
    private ClickListenerInterface clickListenerInterface;

    public OneBtnConfirmDialog(Context context, String title, String message, String btnConfirm) {
        super(context, R.style.UIAlertViewStyle);

        this.context = context;
        this.message = message;
        this.btnConfirm = btnConfirm;
        this.title = title;
    }

    public interface ClickListenerInterface {
        public void doConfirm();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        inite();
    }

    public void inite() {
        setCanceledOnTouchOutside(false);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_confirm, null);
        setContentView(view);

        TextView tvMessage = (TextView) view.findViewById(R.id.tvMessage);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        TextView tvBtnConfirm = (TextView) view.findViewById(R.id.tvBtnConfirm);

        if (StringUtil.isBlank(title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
        }
        tvMessage.setText(message);
        tvBtnConfirm.setText(btnConfirm);

        tvBtnConfirm.setOnClickListener(new clickListener());

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();

        lp.width = (int) (d.widthPixels * 0.8);
        dialogWindow.setAttributes(lp);
    }

    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            clickListenerInterface.doConfirm();
        }
    }

    ;
}
