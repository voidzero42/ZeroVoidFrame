package com.zerovoid.lib.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Toast工具类
 *
 * @version 20160111_wgf
 */
public class ToastHelper {
    private static Handler handler = new Handler(Looper.getMainLooper());
    private static ToastHelper toastHelper;
    private static Toast toast1 = null;
    private static Toast imgToast = null;
    private Context context;
    private static Object synObj = new Object();
    private boolean isDebug;

    public void enableDebug() {
        isDebug = true;
    }

    public void setDebug(boolean isDebug) {
        isDebug = isDebug;
    }

    public static ToastHelper getInstance() {
        if (toastHelper == null) {
            toastHelper = new ToastHelper();
        }
        return toastHelper;
    }

    public Toast _toast(Context context, String str) {
        return displayToastShort(context, str);
    }

    public Toast displayToastShort(Context context, String str) {
        Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }

    public void displayToastLong(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }

    public void displayToastAtOtherPos(Context context, String str,
                                       int gravity, int xOffset, int yOffset) {
        Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        toast.setGravity(gravity, xOffset, yOffset);
        toast.show();

    }

    public void displayToastWithImg(Context context, String str,
                                    int resId, int position) {

        Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(resId);
        switch (position) {
            case 1:
                toastView.addView(imageView, 0);
                break;
            case 2:
                toastView.addView(imageView, 1);
                break;
            case 3:
                toastView.setOrientation(LinearLayout.HORIZONTAL);
                toastView.addView(imageView, 0);
                break;
            case 4:
                toastView.setOrientation(LinearLayout.HORIZONTAL);
                toastView.addView(imageView, 1);
                break;
            default:
                break;
        }
        toast.show();
    }

    public void displayToastWithOutImg(Context context, String str,
                                       int resId, int orientation) {

        Toast toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
        View toastView = toast.getView();
        ImageView imgView = new ImageView(context);
        imgView.setImageResource(resId);
        LinearLayout lyt = new LinearLayout(context);
        lyt.setOrientation(orientation);
        lyt.addView(imgView);
        lyt.addView(toastView);
        toast.setView(lyt);
        toast.show();
    }

    public void init(Context context) {
        this.context = context;
    }

    public Toast _toast(String str) {
        return displayToastShort(str);
    }

    public void _toastDebug(String str) {
        if (isDebug) {
            Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * 显示Toast，duration为short
     * <p/>
     * {@link Context} 当前窗体的上下文
     *
     * @param str {@link String} 消息主体
     */
    public Toast displayToastShort(String str) {
        Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }

    /**
     * 显示Toast，duration为long
     *
     * @param str {@link String} 消息主体
     */
    public void displayToastLong(String str) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }

    /**
     * 快速关闭toast
     * 不停的疯狂的点击某个按钮，触发了toast以后，toast内容会一直排着队的显示出来，不能很快的消失。这样可能会影响用户的使用。
     *
     * @param str {@link String} 消息主体
     */

    public void displayToastWithQuickClose(
            final String str) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (synObj) {
                            if (toast1 != null) {
                                toast1.setText(str);
                                toast1.setDuration(Toast.LENGTH_SHORT);
                            } else {
                                toast1 = Toast.makeText(context, str,
                                        Toast.LENGTH_SHORT);
                            }
                            toast1.show();
                        }
                    }
                });
            }
        }).start();
    }

    public void displayToastWithQuickClose(final Context context,
                                           final String str) {
        new Thread(new Runnable() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        synchronized (synObj) {
                            if (toast1 != null) {
                                toast1.setText(str);
                                toast1.setDuration(Toast.LENGTH_SHORT);
                            } else {
                                toast1 = Toast.makeText(context, str,
                                        Toast.LENGTH_SHORT);
                            }
                            toast1.show();
                        }
                    }
                });
            }
        }).start();
    }

    public void displayToastWithQuickClose(final Context context,
                                           final String str, final int resId) {
        new Thread(new Runnable() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        synchronized (synObj) {
                            if (imgToast != null) {
                                LinearLayout toastView = (LinearLayout) imgToast
                                        .getView();
                                toastView.removeViewAt(0);
                                imgToast.setText(str);
                                imgToast.setDuration(Toast.LENGTH_SHORT);

                            } else {
                                imgToast = Toast.makeText(context, str,
                                        Toast.LENGTH_SHORT);
                            }
                            LinearLayout toastView = (LinearLayout) imgToast
                                    .getView();
                            ImageView imageView = new ImageView(context);
                            imageView.setImageResource(resId);
                            toastView.addView(imageView, 0);
                            imgToast.show();

                        }
                    }
                });
            }
        }).start();
    }
}
