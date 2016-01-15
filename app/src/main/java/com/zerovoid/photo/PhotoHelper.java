package com.zerovoid.photo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.zerovoid.lib.util.TimeUtil;

import java.io.File;
import java.util.Random;

/**
 * Photo工具类
 * 
 * @author shz
 */
public class PhotoHelper {
	private static Context context;
	/** 拍照请求码 */
	private final int REQUEST_CODE_PHOTOHRAPH = 0;
	/** 相册请求码 */
	private final int REQUEST_CODE_ALBUM = 1;

	public static String photoPath="";
	
	String imgName;
    static PhotoHelper photoHelper;	
	public static PhotoHelper getInstance() {
		if(photoHelper==null){
			photoHelper = new PhotoHelper();
		}
		return photoHelper;
	}

	
	/** 打开系统照相机 */
	public void takePhoto(Context context) {
		
		if (context.getExternalCacheDir().exists()) {

			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, true);
			// 获取随机文件名
			Random ran = new Random(System.currentTimeMillis());
			String time = TimeUtil.timeStampToDate(System.currentTimeMillis(),
					"yyyyMMddHHmmss");
			imgName = "IMG"
					+ time
					+ (ran.nextInt(10000) > 100 ? ran.nextInt(10000) : ran
							.nextInt(10000));
			Uri uri = Uri.fromFile(new File(context.getExternalCacheDir(),
					imgName + ".jpg"));
			
			photoPath = context.getExternalCacheDir() + File.separator
					+ imgName + ".jpg";
			
			intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
			((Activity) context).startActivityForResult(intent,
					REQUEST_CODE_PHOTOHRAPH);
		}
	}

	/** 打开系统相册 */
	public void openAlbum(Context context) {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		intent.setType("image/*");
	
		intent.setAction(Intent.ACTION_PICK);

		((Activity) context).startActivityForResult(intent, REQUEST_CODE_ALBUM);
	}

	/**
	 * 获取拍照返回的照片路径
	 * 
	 * @param data
	 * @return
	 */
	public String getPath(Context context,Intent data) {
		Uri originalUri = data.getData();
		// 这里开始的第二部分，获取图片的路径：
		String[] proj = { MediaStore.Images.Media.DATA };
		// 好像是android多媒体数据库的封装接口，具体的看Android文档
		@SuppressWarnings("deprecation")
		Cursor cursor = ((Activity) context).managedQuery(originalUri, proj,
				null, null, null);
		// 按我个人理解 这个是获得用户选择的图片的索引值
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		// 将光标移至开头 ，这个很重要，不小心很容易引起越界
		cursor.moveToFirst();
		// 最后根据索引值获取图片路径
		String path = cursor.getString(column_index);
		return path;
	}

}
