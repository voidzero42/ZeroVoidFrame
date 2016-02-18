package com.zerovoid.lib.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.curry.android.R;

/**
 * 电话的工具类
 *
 * @author shz
 *
 */
public class PhoneCallUtil {
	private static Context context;
	private static PhoneCallUtil phoneCallUtil;
	public static PhoneCallUtil getInstances(){
		if(phoneCallUtil==null){
			phoneCallUtil = new PhoneCallUtil();
		}
        return phoneCallUtil;
	}
	/**
	 * 先提示再拨打电话
	 *
	 * @param context
	 *            上下文
	 * @param address
	 *            电话号码
	 */
	public void PhoneCallDialog(Context context, String address) {
		final Context ctx = context;
		final String phoneNumber = address.toString();
		new AlertDialog.Builder(context).setTitle("提示").setMessage("是否拨打此电话？")
				.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {

						String uri = "tel:" + phoneNumber;
						Intent intent = new Intent(Intent.ACTION_CALL, Uri
								.parse(uri));
						ctx.startActivity(intent);
					}
				}).setNegativeButton("取消", null).show();

	}

	/**
	 * 直接拨打电话
	 *
	 * @param context
	 *            上下文
	 * @param address
	 *            电话号码
	 */
	public void PhoneCall(Context context, String address) {
		final Context ctx = context;
		final String phoneNumber = address.toString();
		String uri = "tel:" + phoneNumber;
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));
		ctx.startActivity(intent);
	}

	/**
	 * 跳转到拨号界面
	 *
	 * @param context
	 *            上下文
	 * @param address
	 *            电话号码
	 */
	public void PhoneCallScence(Context context, String address) {
		String uri = "tel:" + address.toString();
		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
				+ address));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	public void callPhone(final Context context,final String mPhone, String title) {
		final AlertDialog builder = new AlertDialog.Builder(context).create();
		final LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.dialog_phone, null);
		final TextView tvTitle = (TextView) view.findViewById(R.id.umeng_update_content);
		tvTitle.setText(title);
		tvTitle.setGravity(Gravity.CENTER_HORIZONTAL);
		Button btnOk = (Button) view.findViewById(R.id.umeng_update_id_ok);
		Button btnCancel = (Button) view.findViewById(R.id.umeng_update_id_cancel);
		builder.setView(view, 0, 0, 0, 0);
		builder.show();
		btnOk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				builder.dismiss();
				PhoneCall(
						context, mPhone);
			}
		});
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				builder.dismiss();
			}
		});
	}
}
