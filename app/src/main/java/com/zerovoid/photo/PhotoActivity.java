package com.zerovoid.photo;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


import com.zerovoid.lib.util.TimeUtil;
import com.zerovoid.zerovoidframe.R;

import java.io.File;
import java.util.Random;

/**
 * 通用获取图片、拍照工具类。这个类耦合了R、TimeUtil、Constant，不好，要解耦
 *
 * @author 2014年5月，软件园万科
 */
public class PhotoActivity extends Activity implements
        OnClickListener {
    /** 照片名称 */
    private String imgName;
    /** 照片存放的路径 */
    private String photoPath;
    /** 拍照请求码 */
    private final int REQUEST_CODE_PHOTOHRAPH = 0;
    /** 相册请求码 */
    private final int REQUEST_CODE_ALBUM = 1;
    /** 是否存在SD卡 */

    private Button btn_photo, btn_album, btn_cancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        initView();
        findView();
    }

    private void initView() {
        btn_photo = (Button) findViewById(R.id.btn_photo);
        btn_album = (Button) findViewById(R.id.btn_album);
        btn_cancle = (Button) findViewById(R.id.btn_cancel);

    }

    private void findView() {
        btn_photo.setOnClickListener(this);
        btn_album.setOnClickListener(this);
        btn_cancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_photo:
                takePhoto();
                break;
            case R.id.btn_album:
                openAlbum();
                break;
            case R.id.btn_cancel:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // 其实这里什么都不要做
        super.onConfigurationChanged(newConfig);
    }

    /** 打开系统照相机 */
    private void takePhoto() {
        try {
            if (getExternalCacheDir().exists()) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, true);
                // 获取随机文件名
                Random ran = new Random(System.currentTimeMillis());
                //FIXME 解耦
                String time = TimeUtil.timeStampToDate(System.currentTimeMillis(),
                        "yyyyMMddHHmmss");
                imgName = "IMG"
                        + time
                        + (ran.nextInt(10000) > 100 ? ran.nextInt(10000) : ran
                        .nextInt(10000));
                Uri uri = Uri.fromFile(new File(this.getExternalCacheDir(), imgName
                        + ".jpg"));
            /*
             * Uri uri = Uri.fromFile(new File(Constant.DIR_PHOTO, imgName +
			 * ".jpg"));
			 * photoPath = Constant.DIR_PHOTO + "/" + imgName + ".jpg";
			 */
                photoPath = getExternalCacheDir() + File.separator + imgName
                        + ".jpg";
                // 保存文件到URI地址中
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, REQUEST_CODE_PHOTOHRAPH);
            }
        } catch (Exception e) {

        }
    }


    /** 打开系统相册 */
    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, REQUEST_CODE_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int resultCodePhoto = 0;
        if (requestCode == REQUEST_CODE_PHOTOHRAPH
                && resultCode == Activity.RESULT_OK) {// 系统照相机返回
            resultCodePhoto = REQUEST_CODE_PHOTOHRAPH;
        } else if (requestCode == REQUEST_CODE_ALBUM && resultCode == RESULT_OK) {// 系统相册返回
            try {
                photoPath = getPath(data);
                // bundle.putBoolean("isAlbum", true);
                // 高清的压缩图片全部就在 list 路径里面了
                // 高清的压缩过的 bmp 对象 都在 Bimp.bmp里面
                // 完成上传服务器后 .........
                // FileUtils.deleteDir();
                resultCodePhoto = REQUEST_CODE_PHOTOHRAPH;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Bundle bundle = new Bundle();
        Intent intent = new Intent();
        bundle.putString("photoPath", photoPath);
        intent.putExtras(bundle);
        setResult(resultCodePhoto, intent);
        finish();
    }

    /**
     * 获取拍照返回的照片路径
     *
     * @param data
     * @return
     */
    private String getPath(Intent data) {
        Uri originalUri = data.getData();
        // 这里开始的第二部分，获取图片的路径：
        String[] proj = {MediaColumns.DATA};
        // 好像是android多媒体数据库的封装接口，具体的看Android文档
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(originalUri, proj, null, null, null);
        // 按我个人理解 这个是获得用户选择的图片的索引值
        int column_index = cursor
                .getColumnIndexOrThrow(MediaColumns.DATA);
        // 将光标移至开头 ，这个很重要，不小心很容易引起越界
        cursor.moveToFirst();
        // 最后根据索引值获取图片路径
        String path = cursor.getString(column_index);
        return path;
    }

    private static final int RESULT_REQUEST_CODE = 2;

}
