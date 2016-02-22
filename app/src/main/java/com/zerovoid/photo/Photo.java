package com.zerovoid.photo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;

import java.io.File;

public class Photo {
    private static final String TAG = "PhotoActivity";
    /** 照片名称 */
    private String imgName;
    /** 照片存放的路径 */
    public String photoPath;
    /** 拍照请求码 */
    private final int REQUEST_CODE_PHOTOHRAPH = 5;
    /** 相册请求码 */
    private final int REQUEST_CODE_ALBUM = 6;

    private static Context photoContext;
    /** 打开系统照相机 */
    public Uri photoUri = null;

    public Photo(Context context) {
        Photo.photoContext = context;
    }

    // 获取随机文件名
    private String genFileName() {
        return "";
        //		 return TimeUtil.timeStampToDate(System.currentTimeMillis(),
        //				"yyyyMMddHHmmss");
//        "IMG"+ time+ (ran.nextInt(10000) > 100 ? ran.nextInt(10000) : ran
//                .nextInt(10000));
//        Random ran = new Random(System.currentTimeMillis());
    }

    public void takePhoto() {
        Intent intent = null;
        if (photoContext.getExternalCacheDir().exists()) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, true);
            imgName = genFileName();
            photoUri = Uri.fromFile(new File(photoContext.getExternalCacheDir(), imgName
                    + ".jpg"));
            photoPath = photoContext.getExternalCacheDir() + File.separator + imgName + ".jpg";
            ContentValues values = new ContentValues();
            values.put(MediaColumns.TITLE, imgName + ".jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            ((Activity) photoContext).startActivityForResult(intent, REQUEST_CODE_PHOTOHRAPH);
        }

    }

    /** 打开系统相册 */
    public void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//		String photoPath = getPath(intent);
        intent.putExtra("photoPath", photoPath);
        ((Activity) photoContext).startActivityForResult(intent, REQUEST_CODE_ALBUM);
    }

    public interface PhotoListener {
        void onClick(Intent intent);
    }

    private PhotoListener photoListenter;

    /** 打开系统相册 */

    public void openAlbum(PhotoListener photoListenter) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");

        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//		String photoPath = getPath(intent);
        intent.putExtra("photoPath", photoPath);

        this.photoListenter = photoListenter;
        photoListenter.onClick(intent);
    }

    /**
     * 获取拍照返回的照片路径
     *
     * @param data
     * @return
     */
    public static String getPath(Intent data) {
        Uri originalUri = data.getData();
        // 这里开始的第二部分，获取图片的路径：
        String[] proj = {MediaColumns.DATA};
        // 好像是android多媒体数据库的封装接口，具体的看Android文档
        @SuppressWarnings("deprecation")
        Cursor cursor = ((Activity) photoContext).managedQuery(originalUri, proj, null, null,
                null);
        // 按我个人理解 这个是获得用户选择的图片的索引值
        int column_index = cursor
                .getColumnIndexOrThrow(MediaColumns.DATA);
        // 将光标移至开头 ，这个很重要，不小心很容易引起越界
        cursor.moveToFirst();
        // 最后根据索引值获取图片路径
        String path = cursor.getString(column_index);
        return path;
    }
}
