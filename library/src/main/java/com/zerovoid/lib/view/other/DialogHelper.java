package com.zerovoid.lib.view.other;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zerovoid.library.R;


/**
 * 进度条
 * 
 * @author Administrator
 * 
 */
public class DialogHelper {
	private static DialogHelper dialogHelper;
	
	public static DialogHelper getInstance(){
		if(dialogHelper==null){
			dialogHelper = new DialogHelper();
		}
		return dialogHelper;
	}
	/**
	 * 得到自定义的progressDialog
	 * 
	 * @param context
	 * @param msg
	 * @return
	 */
	public Dialog createLoadingDialog(Context context, String msg) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.dialog_system, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		// main.xml中的ImageView
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		// 加载动画
		// Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
		// context, R.anim.dialog_animation2);
		// // 使用ImageView显示动画
		// spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		spaceshipImage.setBackgroundResource(R.drawable.dialog_animation3);
		AnimationDrawable animationDrawable = (AnimationDrawable) spaceshipImage
				.getBackground();
		// 开始执行动画
		//tipTextView.setTextColor(0xff000000);
		animationDrawable.start();
		tipTextView.setText(msg);// 设置加载信息

		Dialog loadingDialog = new Dialog(context, R.style.anim_dialog);// 创建自定义样式dialog

		// loadingDialog.setCancelable(false);// 不可以用“返回键”取消
		loadingDialog.setContentView(layout);// 设置布局
		loadingDialog.setCanceledOnTouchOutside(false);
		return loadingDialog;

	}
	
	public static void closeDialog(Dialog dialog){
		if(dialog != null){
			try{
			dialog.dismiss();
			}catch(Exception e){
				
			}
		}
	}
}
