package com.zerovoid.lib.util;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitmapUtil {

    /**
     * 获取合适的Bitmap平时获取Bitmap就用这个方法吧.
     *
     * @param path    路径.
     * @param data    byte[]数组.
     * @param context 上下文
     * @param uri     uri
     * @param target  模板宽或者高的大小.
     * @param width   是否是宽度
     * @return
     */
    public static Bitmap getResizedBitmap(String path, byte[] data,
                                          Context context, Uri uri, int target, boolean width) {
        Options options = null;

        if (target > 0) {

            Options info = new Options();
            // 这里设置true的时候，decode时候Bitmap返回的为空，
            // 将图片宽高读取放在Options里.
            info.inJustDecodeBounds = false;

            decode(path, data, context, uri, info);

            int dim = info.outWidth;
            if (!width)
                dim = Math.max(dim, info.outHeight);
            int ssize = sampleSize(dim, target);

            options = new Options();
            options.inSampleSize = ssize;

        }

        Bitmap bm = null;
        try {
            bm = decode(path, data, context, uri, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bm;

    }

    /**
     * 解析Bitmap的公用方法.
     *
     * @param path
     * @param data
     * @param context
     * @param uri
     * @param options
     * @return
     */
    public static Bitmap decode(String path, byte[] data, Context context,
                                Uri uri, Options options) {

        Bitmap result = null;

        if (path != null) {

            result = BitmapFactory.decodeFile(path, options);

        } else if (data != null) {

            result = BitmapFactory.decodeByteArray(data, 0, data.length,
                    options);

        } else if (uri != null) {
            // uri不为空的时候context也不要为空.
            ContentResolver cr = context.getContentResolver();
            InputStream inputStream = null;

            try {
                inputStream = cr.openInputStream(uri);
                result = BitmapFactory.decodeStream(inputStream, null, options);
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return result;
    }

    /**
     * 获取合适的sampleSize. 这里就简单实现都是2的倍数啦.
     *
     * @param width
     * @param target
     * @return
     */
    private static int sampleSize(int width, int target) {
        int result = 1;
        for (int i = 0; i < 10; i++) {
            if (width < target * 2) {
                break;
            }
            width = width / 2;
            result = result * 2;
        }
        return result;
    }

    /**
     * 将Bitmap切圆
     *
     * @param bitmap
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }


    public static Bitmap createFramedPhoto(int x, int y, Bitmap image, float outerRadiusRat) {
        //根据源文件新建一个darwable对象
        Drawable imageDrawable = new BitmapDrawable(image);

        // 新建一个新的输出图片
        Bitmap output = Bitmap.createBitmap(x, y, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        // 新建一个矩形
        RectF outerRect = new RectF(0, 0, x, y);

        // 产生一个红色的圆角矩形
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        canvas.drawRoundRect(outerRect, outerRadiusRat, outerRadiusRat, paint);

        // 将源图片绘制到这个圆角矩形上
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        imageDrawable.setBounds(0, 0, x, y);
        canvas.saveLayer(outerRect, paint, Canvas.ALL_SAVE_FLAG);
        imageDrawable.draw(canvas);
        canvas.restore();

        return output;
    }

    /**
     * 将相片文件夹中的相片，压缩并存储到APP文件夹中，切圆
     */
    public static String saveBitmapTofileWithRound(String savePath, String filePath) {
        return saveBitmapToFile(savePath, filePath, true);
    }

    /**
     * 将相片文件夹中的相片，压缩并存储到APP文件夹中
     */
    public static String saveBitmapToFile(String savePath, String filePath) {
        return saveBitmapToFile(savePath, filePath, false);
    }

    /**
     * 将相片文件夹中的相片，压缩并存储到APP文件夹中
     *
     * @param filePath 文件路径
     * @param isRound  是否切圆
     * @return
     */
    public static String saveBitmapToFile(String savePath, String filePath, boolean isRound) {
        BufferedOutputStream os = null;
        /* 压缩过的Bitmap */
        Bitmap smallbitmap = decodeBitmap(filePath);
        if (isRound) {
            smallbitmap = toRoundBitmap(smallbitmap);
        }
        String fileName = getFileNameFromPath(filePath);
        if (fileName == null) {
            return "";
        }
        String newFilePath = savePath + fileName + ".png";
        try {
            File folder = new File(savePath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File file = new File(newFilePath);
            // String _filePath_file.replace(File.separatorChar +
            // file.getName(), "");
            if (file.exists()) {
                return newFilePath;
            } else {
                file.createNewFile();
            }
            os = new BufferedOutputStream(new FileOutputStream(file));

            smallbitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            return newFilePath;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {

                }

            }
        }
        return "";
    }

    /**
     * 将文件路径中的文件转为Bitmap
     *
     * @param path
     * @return
     */
    private static Bitmap decodeBitmap(String path) {
        Options options = new Options();
        options.inJustDecodeBounds = true;// 关闭分配空间
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);// 读取图片

        int be = (int) (options.outHeight / (float) 200);// 按800计算压缩比

        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        options.inJustDecodeBounds = false;// 打开分配空间
        return BitmapFactory.decodeFile(path, options);// 读取压缩后图片
    }

    /**
     * 从文件路径中，截取文件名，不包括后缀名
     *
     * @param filePath 文件路径
     * @return
     */
    private static String getFileNameFromPath(String filePath) {
        int begin = filePath.lastIndexOf(File.separator);
        int end = filePath.lastIndexOf(".");
        if (end < 1) {
            return null;
        }
        String fileName = filePath.substring(begin + 1, end);
        return fileName;
    }
}
