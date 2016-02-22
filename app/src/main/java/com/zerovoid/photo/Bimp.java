package com.zerovoid.photo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.media.ExifInterface;
import android.net.Uri;

import java.io.File;
import java.io.IOException;

/**
 * 图片处理
 * 
 * @author yangwei
 * 
 */
public class Bimp {
	/**
	 * 压缩图片 640*480
	 * 
	 * @param path
	 *            图片路径
	 * @return
	 */
	public static Bitmap Compression(String path) {
		try {

			int degree = readPictureDegree(path);

			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			newOpts.inJustDecodeBounds = true;/* 只读边,不读内容 */
			newOpts.inSampleSize = 2;
			Bitmap bitmap = BitmapFactory.decodeFile(path, newOpts);

			newOpts.inJustDecodeBounds = false;
			int w = newOpts.outWidth;
			int h = newOpts.outHeight;
			float hh = 640f;
			float ww = 480f;
			int be = 1;
			if (w > h && w > ww) {
				be = (int) (newOpts.outWidth / ww);
			} else if (w < h && h > hh) {
				be = (int) (newOpts.outHeight / hh);
			}
			if (be <= 0)
				be = 1;
			newOpts.inSampleSize = be;/* 设置采样率 */

			newOpts.inPreferredConfig = Config.ARGB_8888;/* 该模式是默认的,可不设 */
			newOpts.inPurgeable = true;/* 同时设置才会有效 */
			newOpts.inInputShareable = true;/* 当系统内存不够时候图片自动被回收 */

			bitmap = BitmapFactory.decodeFile(path, newOpts);
			bitmap = rotaingImageView(degree, bitmap);
			return bitmap;
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 自定义比例压缩
	 * 
	 * @param path
	 *            图片路径
	 * @param maxWidth
	 *            最大宽度
	 * @param maxHeight
	 *            最大高度
	 * @return
	 */
	@SuppressLint("NewApi")
	public static Bitmap Compression(String path, int maxWidth, int maxHeight) {
		int degree = readPictureDegree(path);
		File fileImage = new File(path);
		BitmapFactory.Options options = new BitmapFactory.Options();
		Bitmap bitmap = null;
		options.inSampleSize = 2;
		/* 关闭分配空间，此设为true，不返回bitmap，返回null，false返回bitmap */
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(fileImage.getAbsolutePath(), options);/* 读取图片 */
		double ratio = 1D;
		if (maxWidth > 0 && maxHeight <= 0) {
			/* 限定宽度，高度不做限制 */
			ratio = Math.ceil(options.outWidth / maxWidth);
		} else if (maxHeight > 0 && maxWidth <= 0) {
			/* 限定高度，不限制宽度 */
			ratio = Math.ceil(options.outHeight / maxHeight);
		} else if (maxWidth > 0 && maxHeight > 0) {
			/* 高度和宽度都做了限制，这时候我们计算在这个限制内能容纳的最大的图片尺寸，不会使图片变形 */
			double _widthRatio = Math.ceil(options.outWidth / maxWidth);
			double _heightRatio = Math.ceil(options.outHeight / maxHeight);
			ratio = _widthRatio > _heightRatio ? _widthRatio : _heightRatio;
		}
		if (ratio > 1) {
			options.inSampleSize = (int) ratio;
		}
		options.inPreferredConfig = Config.RGB_565;
		/* 打开分配空间 */
		options.inJustDecodeBounds = false;

		/* 读取压缩后图片 */
		bitmap = BitmapFactory.decodeFile(path, options);
		if (bitmap != null) {
			bitmap = rotaingImageView(degree, bitmap);
		}
		/* Log.e(Bimp.class.getName(), "BITMAP2=" + bitmap.getByteCount()); */
		return bitmap;
	}

	/** 销毁图片文件 */
	public static void destoryBimap(Bitmap bitmap) {
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
			bitmap = null;
		}
	}

	/**
	 * 读取图片属性：旋转的角度
	 * 
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 旋转图片
	 * 
	 * @param angle
	 * 
	 * @param bitmap
	 * 
	 * @return Bitmap
	 */
	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		/* 旋转图片 动作 */
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		Bitmap b = bitmap;
		if(b!=null){
			/* 创建新的图片 */
			bitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(),
					matrix, true);
		}
		
		return bitmap;
	}

	/**
	 * 根据原图和变长绘制圆形图片
	 * 
	 * @param source
	 * @param min
	 * @return
	 */
	public static Bitmap createCircleImage(Bitmap source, int min) {
		final Paint paint = new Paint();
		paint.setAntiAlias(true);
		Bitmap target = Bitmap.createBitmap(min, min, Config.ARGB_8888);
		/**
		 * 产生一个同样大小的画布
		 */
		Canvas canvas = new Canvas(target);
		/**
		 * 首先绘制圆形
		 */
		canvas.drawCircle(min / 2, min / 2, min / 2, paint);
		/**
		 * 使用SRC_IN
		 */
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		/**
		 * 绘制图片
		 */
		canvas.drawBitmap(source, 0, 0, paint);
		return target;
	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public static Intent startPhotoZoom(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		/* 设置裁剪 */
		intent.putExtra("crop", "true");
		/* aspectX aspectY 是宽高的比例 */
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		/* outputX outputY 是裁剪图片宽高 */
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		return intent;
	}
}
