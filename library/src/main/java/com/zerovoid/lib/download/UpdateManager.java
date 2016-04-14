package com.zerovoid.lib.download;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * APK更新
 */
@Deprecated
public class UpdateManager {
    private Context mContext;

    // 提示语
    private String updateMsg = null;

    // 返回的安装包url
    private String apkUrl = "";

    private Dialog noticeDialog;

    private Dialog downloadDialog;
    /* 下载包安装路径 */
    private static final String savePath = "/sdcard/OrangeLife/";

    private static final String saveFileName = savePath + "orangelife.apk";

    /* 进度条与通知ui刷新的handler和msg常量 */
    private ProgressBar mProgress;

    /** 更新进度条，使用第三方文件下载器 */
    private static final int UPDATE_PROGRESS = 0;
    /** 更新进度条，使用HUC */
    private static final int UPDATE_PROGRESS_HUC = 1;
    /** 下载完毕 */
    private static final int DOWNLOAD_FINISH = 2;

    private int progress;

    private boolean interceptFlag = false;

    private Button btnUpdate;
    private Button btnCancel;
    private Button btnSure;
    private TextView tvContent;
    private TextView tvTitle;
    private LinearLayout llUpdate, llShowInfo;
    private String type;
    private AlertDialog dialog;
    private File dir;


    public UpdateManager(Context context, String type) {
        this.mContext = context;
        this.type = type;
    }

    /**
     * @param apkUrl
     */
    public void checkUpdateInfo(String apkUrl, String info, String title) {
        this.apkUrl = apkUrl;
        showNoticeDialog(2, info, title);
    }

    public void checkUpdateInfo(String apkUrl, String isForce, String info, String title) {
        this.apkUrl = apkUrl;
        if (isForce.equals("1")) {
            showNoticeDialog(1, info, title);
        } else if (isForce.equals("0")) {
            showNoticeDialog(0, info, title);
        }
    }

    private void showNoticeDialog(int isForce, String info, String title) {
        final AlertDialog dialog2 = new AlertDialog.Builder(mContext).create();
        tvTitle.setText(title + "版本更新");
        switch (isForce) {
            case 0:
                tvContent.setText(info);
                llUpdate.setVisibility(View.VISIBLE);
                llShowInfo.setVisibility(View.GONE);
            /*强制更新，禁止取消*/
                btnCancel.setVisibility(View.VISIBLE);
                btnCancel.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog2 != null && dialog2.isShowing())
                            dialog2.dismiss();
                    }
                });
                break;
            case 1:
                llUpdate.setVisibility(View.VISIBLE);
                llShowInfo.setVisibility(View.GONE);
                tvContent.setText(info);
                btnCancel.setVisibility(View.GONE);
                break;
            case 2:
                llUpdate.setVisibility(View.GONE);
                llShowInfo.setVisibility(View.VISIBLE);
                tvContent.setText(info);
                break;
            default:
                break;
        }
        dialog2.setCancelable(false);
        dialog2.show();
        btnUpdate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog2 != null && dialog2.isShowing()) {
                    dialog2.dismiss();
                }
                showDownloadDialog();
            }
        });
        btnSure.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog2 != null && dialog2.isShowing()) {
                    dialog2.dismiss();
                }
            }
        });
    }

    private void showDownloadDialog() {
        dialog = new AlertDialog.Builder(mContext).create();
//		builder.setTitle("版本更新");
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        dialog.setCancelable(false);
        dialog.show();
    }


    /**
     * 下载APK
     *
     * @param path APK文件在服务器的地址
     * @param dir  本地文件夹的地址
     */
    private void downloadApkWithLoader(final String path, final File dir) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                File localDir = dir;
                if (localDir == null) {
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        localDir = new File(savePath);
                    }
                }
                //FIXME 一般使用系统的HTTP工具的文件下载功能，而不使用第三方的
//                FileDownloader loader = new FileDownloader(mContext, path, localDir, 3);
//                int length = loader.getFileSize();
//                mProgress.setMax(length);
//                try {
//                    loader.download(new DownloadProgressListener() {
//                        @Override
//                        public void onDownloadSize(int size) {//异步更新UI
//                            Message msg = new Message();
//                            msg.what = UPDATE_PROGRESS;
//                            msg.getData().putInt("size", size);
//                            mHandler.sendMessage(msg);
//                        }
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        }).start();
    }

    /**
     * 安装apk
     */
    private void installApk() {
//        File apkFile = new File(savePath + FileDownloader.filename);
//        if (!apkFile.exists()) {
//            return;
//        }
//        Intent i = new Intent(Intent.ACTION_VIEW);
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        i.setDataAndType(Uri.parse("file://" + apkFile.toString()),
//                "application/vnd.android.package-archive");
//        mContext.startActivity(i);
    }

    // 安装新的APK之后，清空本地数据存储
    private void clearCache() {

    }

    private void updateProgress(int size) {
        mProgress.setProgress(size);
        if (mProgress.getProgress() == mProgress.getMax()) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                installApk();
            }
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_PROGRESS://更新进度条
                    int size = msg.getData().getInt("size");
                    updateProgress(size);
                    break;
                case UPDATE_PROGRESS_HUC:
                    mProgress.setProgress(progress);
                    break;
                case DOWNLOAD_FINISH:
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    installApk();
                    break;
                default:
                    break;
            }
        }
    };

    /** 使用HttpURLConnection来下载APK，但是HUC下大文件有内存溢出的可能性，一般会使用Socket来下载大文件 */
    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(apkUrl);

                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setRequestProperty("Accept-Encoding", "identity");
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();

                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdir();
                }
                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                FileOutputStream fos = new FileOutputStream(ApkFile);

                int count = 0;
                byte buf[] = new byte[1024];

                do {
                    int numread = is.read(buf);
                    count += numread;
                    progress = (int) (((float) count / length) * 100);
                    // 更新进度
                    mHandler.sendEmptyMessage(UPDATE_PROGRESS_HUC);
                    if (numread <= 0) {
                        // 下载完成通知安装
                        mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                        break;
                    }
                    fos.write(buf, 0, numread);
                } while (!interceptFlag);// 点击取消就停止下载.

                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };
}
