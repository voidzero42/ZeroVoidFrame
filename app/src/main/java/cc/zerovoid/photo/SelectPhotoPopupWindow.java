package cc.zerovoid.photo;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import com.zerovoid.zerovoidframe.R;


/**
 * 拍照、取相册相片的popwin
 *
 * @author chenxiang songhuangzhong
 */
public class SelectPhotoPopupWindow extends PopupWindow {
    /** 拍照、从相册选择、取消三个按钮 */
    private Button btnTakePhoto, btnPickPhoto, btnCancel;
    /** 照片名称 */
    private String imgName;
    /** 照片存放的路径 */
    private String photoPath;
    /** 拍照请求码 */
    private final int REQUEST_CODE_PHOTOHRAPH = 0;
    /** 相册请求码 */
    private final int REQUEST_CODE_ALBUM = 1;
    /** 上下文 */
    private Context context;
    private View mMenuView;

    public SelectPhotoPopupWindow(Context context, OnClickListener itemsOnClick) {
        this.context = context;
        initView(itemsOnClick);

    }

    /**
     * 初始化组件
     * <p/>
     * popwin布局
     */
    private void initView(OnClickListener itemsOnClick) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        mMenuView = inflater.inflate(R.layout.popwin_picture, null);
//        btnTakePhoto = (Button) mMenuView.findViewById(R.id.btn_take_photo);
//        btnPickPhoto = (Button) mMenuView.findViewById(R.id.btn_pick_photo);
//        btnCancel = (Button) mMenuView.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 销毁弹出框
                dismiss();
            }
        });
        /* 设置按钮监听 */
        btnTakePhoto.setOnClickListener(itemsOnClick);
        btnPickPhoto.setOnClickListener(itemsOnClick);
		/* 设置SelectPicPopupWindow的View */
        this.setContentView(mMenuView);
		/* 设置SelectPicPopupWindow弹出窗体的宽 */
        this.setWidth(LayoutParams.FILL_PARENT);
		/* 设置SelectPicPopupWindow弹出窗体的高 */
        this.setHeight(LayoutParams.WRAP_CONTENT);
		/* 设置SelectPicPopupWindow弹出窗体可点击 */
        this.setFocusable(true);
		/* 设置SelectPicPopupWindow弹出窗体动画效果 */
        this.setAnimationStyle(R.style.AnimBottom);
		/* 实例化一个ColorDrawable颜色为半透明 */
        ColorDrawable dw = new ColorDrawable(0xb0000000);
		/* 设置SelectPicPopupWindow弹出窗体的背景 */
        this.setBackgroundDrawable(dw);
        final View view = mMenuView;
		/* mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框 */
        mMenuView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

//                int height = view.findViewById(R.id.pop_layout).getTop();
//                int y = (int) event.getY();
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (y < height) {
//                        dismiss();
//                    }
//                }
                return true;
            }
        });
    }

}
