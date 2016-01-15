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


public class ConfirmDialog extends Dialog {

    private Context context;
    private String title;
    private String message;
    private String buttonLeftText;
    private String buttonRightText;
    private ClickListenerInterface clickListenerInterface;

    public ConfirmDialog(Context context, String title, String message,
                         String buttonLeftText, String buttonRightText) {
        super(context, R.style.UIAlertViewStyle);

        this.context = context;
        this.title = title;
        this.message = message;
        this.buttonLeftText = buttonLeftText;
        this.buttonRightText = buttonRightText;
    }

    public interface ClickListenerInterface {
        void doLeft();

        void doRight();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        inite();
    }

    public void inite() {
        this.setCanceledOnTouchOutside(false);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_layout1, null);
        setContentView(view);

        TextView tvMessage = (TextView) view.findViewById(R.id.tvMessage);
        TextView tvLeft = (TextView) view.findViewById(R.id.tvBtnLeft);
        TextView tvRight = (TextView) view.findViewById(R.id.tvBtnRight);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);

        if (StringUtil.isBlank(title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
        }
        tvMessage.setText(message);
        tvLeft.setText(buttonLeftText);
        tvRight.setText(buttonRightText);

        tvLeft.setOnClickListener(new clickListener());
        tvRight.setOnClickListener(new clickListener());

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

            int id = v.getId();
            if (id == R.id.tvBtnLeft) {
                clickListenerInterface.doLeft();
            } else if (id == R.id.tvBtnRight) {
                clickListenerInterface.doRight();
            }
        }
    }
}
