/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zerovoid.lib.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This helper class download images from the Internet and binds those with the
 * provided ImageView.
 * <p/>
 * <br>
 * 从网路下载图片并填充给指定的ImageView
 * <p/>
 * <p/>
 * It requires the INTERNET permission, which should be added to your
 * application's manifest file. <br>
 * 使用该类需要在manifest配置文件中添加 INTERNET 权限。
 * <p/>
 * <p/>
 * A local cache of downloaded images is maintained internally to improve
 * performance. <br>
 * 会维护一份本地的cache以改善性能。
 */
@Deprecated
public class ImageDownloader {

	/* private final String LOG_TAG = "ImageDownloader"; */

    private static ImageDownloader imageDownloader;
//
//
//
//    public enum Mode {
//        NO_ASYNC_TASK, NO_DOWNLOADED_DRAWABLE, CORRECT
//    }
//
//    /**
//     * 七牛图片尺寸
//     */
//    public enum ImageSize {
//        /** 原图 */
//        IMAGE_SIZE_ORIGINAL,
//        /** 七牛大图，图片格式PNG，width=960px */
//        IMAGE_SIZE_LARGE_PNG,
//        /** 七牛大图，图片格式JPG，width=960px */
//        IMAGE_SIZE_LARGE_JPG,
//        /** 七牛中图，图片格式PNG，width=640px */
//        IMAGE_SIZE_MIDDLE_PNG,
//        /** 七牛中图，图片格式JPG，width=640px */
//        IMAGE_SIZE_MIDDLE_JPG,
//        /** 七牛小图，图片格式PNG，width=320px */
//        IMAGE_SIZE_SMALL_PNG,
//        /** 七牛小图，图片格式JPG，width=320px */
//        IMAGE_SIZE_SMALL_JPG,
//        /** 七牛超小图，图片格式PNG，width=100px，一般作列表封面 */
//        IMAGE_SIZE_EXTRA_SMALL_PNG,
//        /** 七牛超小图，图片格式JPG，width=100px，一般作列表封面 */
//        IMAGE_SIZE_EXTRA_SMALL_JPG,
//        /** 七牛正方形超小图，图片格式PNG，width=100px，一般作列表封面 */
//        IMAGE_SIZE_EXTRA_SMALL_SQUARE_PNG,
//        /** 七牛正方形超小图，图片格式JPG，width=100px，一般作列表封面 */
//        IMAGE_SIZE_EXTRA_SMALL_SQUARE_JPG,
//        /** 七牛矩形超小图，图片格式JPG，width=100px，一般作列表封面 */
//        IMAGE_SIZE_EXTRA_SMALL_RECTANGLE_JPG
//    }
//
//    private Mode mode = Mode.CORRECT;
//
//    private ImageDownloader() {
//
//    }
//
//    public static ImageDownloader getImageDownloader() {
//        if (imageDownloader == null) {
//            imageDownloader = new ImageDownloader();
//        }
//        return imageDownloader;
//    }
//
//    /**
//     * Download the specified image from the Internet and binds it to the
//     * provided ImageView. The binding is immediate if the image is found in the
//     * cache and will be done asynchronously otherwise. A null bitmap will be
//     * associated to the ImageView if an error occurs.
//     *
//     * @param url       The URL of the image to download.
//     * @param imageView The ImageView to bind the downloaded image to.
//     */
//    public void download(String url, ImageView imageView) {
//        download(url, imageView, false, ImageSize.IMAGE_SIZE_ORIGINAL);
//    }
//
//    /**
//     * Download the specified image from the Internet and binds it to the
//     * provided ImageView. The binding is immediate if the image is found in the
//     * cache and will be done asynchronously otherwise. A null bitmap will be
//     * associated to the ImageView if an error occurs.
//     *
//     * @param url       The URL of the image to download.
//     * @param imageView The ImageView to bind the downloaded image to.
//     * @param imageSize 图片尺寸，IMAGE_SIZE_MIDDLE_JPG 中图
//     */
//    public void download(String url, ImageView imageView, ImageSize imageSize) {
//        download(url, imageView, false, imageSize);
//    }
//
//    /**
//     * Download the specified image from the Internet and binds it to the
//     * provided ImageView. The binding is immediate if the image is found in the
//     * cache and will be done asynchronously otherwise. A null bitmap will be
//     * associated to the ImageView if an error occurs.
//     *
//     * @param url       The URL of the image to download.
//     * @param imageView The ImageView to bind the downloaded image to.
//     * @param isRound   是否切圆
//     * @param imageSize 图片尺寸，""空字符串为默认中图
//     */
//    public void download(String url, ImageView imageView, boolean isRound,
//                         ImageSize imageSize) {
//        download(url, imageView, isRound,0,0,0, imageSize);
//    }
//
//    public void download(String url, ImageView imageView, boolean isRound,int x,int y,float outerRadiusRat,
//                         ImageSize imageSize) {
//
//        String urlWithImageSize = new StringBuilder().append(url)
//                .append(getImageSizeText(imageSize)).toString();
//        resetPurgeTimer();
//        Bitmap bitmap = getBitmapFromCache(urlWithImageSize);
//        if (bitmap == null) {
//            bitmap = getBimapFileCache(urlWithImageSize);
//        }
//        if (bitmap == null) {
//
//                forceDownload(urlWithImageSize, imageView, isRound,x,y,outerRadiusRat);
//
//        } else {
//            if (isRound) {
//                if(x==0&&y==0&&outerRadiusRat==0) {
//                    bitmap = BitmapUtil.toRoundBitmap(bitmap);
//                }else{
//                    bitmap=BitmapUtil.createFramedPhoto(x,y,bitmap,outerRadiusRat);
//                }
//            }
//            cancelPotentialDownload(urlWithImageSize, imageView);
//            imageView.setImageBitmap(bitmap);
//        }
//    }
//
//    /**
//     * 根据图片类型，返回图片类型字符串
//     *
//     * @param imageSize 图片类型
//     * @return
//     */
//    private String getImageSizeText(ImageSize imageSize) {
//        if (imageSize == null) {
//            imageSize = ImageSize.IMAGE_SIZE_ORIGINAL;
//        }
//        String imageSizeText = "";
//        switch (imageSize) {
//            case IMAGE_SIZE_LARGE_PNG:
//                imageSizeText = "-lg.png";
//                break;
//            case IMAGE_SIZE_LARGE_JPG:
//                imageSizeText = "-lg.jpg";
//                break;
//            case IMAGE_SIZE_MIDDLE_PNG:
//                imageSizeText = "-md.png";
//                break;
//            case IMAGE_SIZE_MIDDLE_JPG:
//                imageSizeText = "-md.jpg";
//                break;
//            case IMAGE_SIZE_SMALL_PNG:
//                imageSizeText = "-sm.png";
//                break;
//            case IMAGE_SIZE_SMALL_JPG:
//                imageSizeText = "-sm.jpg";
//                break;
//            case IMAGE_SIZE_EXTRA_SMALL_PNG:
//                imageSizeText = "-xs.png";
//                break;
//            case IMAGE_SIZE_EXTRA_SMALL_JPG:
//                imageSizeText = "-xs.jpg";
//                break;
//            case IMAGE_SIZE_EXTRA_SMALL_SQUARE_PNG:
//                imageSizeText = "-xssq.png";
//                break;
//            case IMAGE_SIZE_EXTRA_SMALL_SQUARE_JPG:
//                imageSizeText = "-xssq.jpg";
//                break;
//            case IMAGE_SIZE_EXTRA_SMALL_RECTANGLE_JPG:
//                imageSizeText = "-storelogo.jpg";
//                break;
//            case IMAGE_SIZE_ORIGINAL:
//                imageSizeText = "";
//                break;
//            default:
//                imageSizeText = "";
//                break;
//        }
//        ImageLoader loader;
//        return imageSizeText;
//    }
//
//    /**
//     * Same as download but the image is always downloaded and the cache is not
//     * used. Kept private at the moment as its interest is not clear.
//     */
//    private void forceDownload(String url, ImageView imageView, boolean isRound,int x,int y,float outerRadiusRat) {
//        // State sanity: url is guaranteed to never be null in
//        // DownloadedDrawable and cache keys.
//        if (url == null || "null".equals(url)) {
//            imageView.setImageDrawable(null);
//            return;
//        }
//
//        if (cancelPotentialDownload(url, imageView)) {
//            switch (mode) {
//                case NO_ASYNC_TASK:
//                    Bitmap bitmap = downloadBitmap(url, isRound,x,y,outerRadiusRat);
//                    addBitmapToCache(url, bitmap);
//                    imageView.setImageBitmap(bitmap);
//                    break;
//
//                case NO_DOWNLOADED_DRAWABLE:
//                    imageView.setMinimumHeight(156);
//                    BitmapDownloaderTask task = new BitmapDownloaderTask(imageView);
//                    task.execute(url, String.valueOf(isRound));
//                    break;
//
//                case CORRECT:
//                    task = new BitmapDownloaderTask(imageView);
//                    DownloadedDrawable downloadedDrawable = new DownloadedDrawable(
//                            task);
//                    imageView.setImageDrawable(downloadedDrawable);
//                    imageView.setMinimumHeight(156);
//
//                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url, String.valueOf(isRound),String.valueOf(x),String.valueOf(y),String.valueOf(outerRadiusRat));
//                    break;
//            }
//        }
//    }
//
//    /**
//     * Returns true if the current download has been canceled or if there was no
//     * download in progress on this image view. Returns false if the download in
//     * progress deals with the same url. The download is not stopped in that
//     * case.
//     */
//    private static boolean cancelPotentialDownload(String url,
//                                                   ImageView imageView) {
//        BitmapDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);
//
//        if (bitmapDownloaderTask != null) {
//            String bitmapUrl = bitmapDownloaderTask.url;
//            if ((bitmapUrl == null) || (!bitmapUrl.equals(url))) {
//                bitmapDownloaderTask.cancel(true);
//            } else {
//                // The same URL is already being downloaded.
//                return false;
//            }
//        }
//        return true;
//    }
//
//    /**
//     * @param imageView Any imageView
//     * @return Retrieve the currently active download task (if any) associated
//     * with this imageView. null if there is no such task.
//     */
//    private static BitmapDownloaderTask getBitmapDownloaderTask(
//            ImageView imageView) {
//        if (imageView != null) {
//            Drawable drawable = imageView.getDrawable();
//            if (drawable instanceof DownloadedDrawable) {
//                DownloadedDrawable downloadedDrawable = (DownloadedDrawable) drawable;
//                BitmapDownloaderTask getBitmapDownloaderTask = downloadedDrawable
//                        .getBitmapDownloaderTask();
//                return getBitmapDownloaderTask;
//            }
//        }
//        return null;
//    }
//
//    // public static int SCREEN_WIDTH = 0;
//
//    /**
//     * 从给定文件网络地址下载文件
//     *
//     * @param path    文件网络地址
//     * @param isRound 是否切圆
//     * @return
//     */
//    Bitmap downloadBitmap(String path, boolean isRound,int x,int y,float outerRadiusRat) {
//        InputStream inStream = null;
//        Bitmap bitmap = null;
//        String filename = getUrlName(path);
//        final HttpClient client = (mode == Mode.NO_ASYNC_TASK) ? new DefaultHttpClient()
//                : AndroidHttpClient.newInstance("Android");
//        HttpEntity entity = null;
//        try {
//            final HttpGet getRequest = new HttpGet(path);
//            HttpResponse response = client.execute(getRequest);
//            final int statusCode = response.getStatusLine().getStatusCode();
//            if (statusCode != HttpStatus.SC_OK) {
//                return null;
//            }
//            entity = response.getEntity();
//            if (entity != null) {
//                inStream = entity.getContent();
//                String file_path = getFilePath(filename, path);
//                File file = new File(file_path);
//
//                File filePath = new File(OrangeHttpConstant.IMAGE_CACHE_PATH);
//                if (!filePath.exists()) {
//                    filePath.mkdirs();
//                }
//                byte[] btImg = readInputStream(inStream);
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inJustDecodeBounds = false;
//                bitmap = BitmapFactory.decodeByteArray(btImg, 0, btImg.length);
//                if (isRound) {
//                    if(x==0&&y==0&&outerRadiusRat==0) {
//                        bitmap = BitmapUtil.toRoundBitmap(bitmap);
//                    }else{
//                        bitmap=BitmapUtil.createFramedPhoto(x,y,bitmap,outerRadiusRat);
//                    }
//                }
//                if (!file.exists()) {
//                    file.createNewFile();
//                    writeImageToDisk(btImg, file);
//                }
//
//            }
//
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//            System.out.print(path);
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//            System.out.print(path);
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.print(path);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.print(path);
//        } finally {
//            try {
//                if (inStream != null) {
//                    inStream.close();
//                }
//                if (entity != null) {
//                    entity.consumeContent();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if ((client instanceof AndroidHttpClient)) {
//                ((AndroidHttpClient) client).close();
//            }
//
//        }
//
//        return bitmap;
//    }
//
//    /**
//     * 从输入流中获取数据
//     *
//     * @param inStream 输入流
//     * @return
//     * @throws Exception
//     */
//    public byte[] readInputStream(InputStream inStream) throws Exception {
//        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//        byte[] buffer = new byte[1024];
//        int len = 0;
//        while ((len = inStream.read(buffer)) != -1) {
//            outStream.write(buffer, 0, len);
//        }
//        return outStream.toByteArray();
//    }
//
//    /**
//     * 将图片写入到磁盘
//     *
//     * @param img 图片数据流
//     * @bitmap 切圆时使用的Bitmap
//     */
//    public void writeImageToDisk(byte[] img, File file) {
//        FileOutputStream fops = null;
//        try {
//            fops = new FileOutputStream(file);
//            fops.write(img);
//            fops.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (fops != null) {
//                    fops.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
//
//    public interface ImageDownloadListener {
//        public void request(String path);
//    }
//
//    private ImageDownloadListener listener;
//
//    public void downloadImg(final String path, ImageDownloadListener listener) {
//        this.listener = listener;
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                downloadImg(path);
//            }
//
//        }).start();
//
//    }
//
//    /** 根据文件名组装本地文件路径 */
//    private String getFilePath(String filename, String path) {
//        return OrangeHttpConstant.IMAGE_CACHE_PATH + "." + filename
//                + Md5(path, 32) + ".jpg";
//    }
//
//    private void downloadImg(String path) {
//        InputStream inStream = null;
//        String filename = getUrlName(path);
//        try {
//            URL url = new URL(path);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setConnectTimeout(5 * 1000);
//            conn.setRequestMethod("GET");
//            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                inStream = conn.getInputStream();
//                String file_path = getFilePath(filename, path);
//                File file = new File(file_path);
//                File filePath = new File(OrangeHttpConstant.IMAGE_CACHE_PATH);
//                if (!filePath.exists()) {
//                    filePath.mkdirs();
//                }
//
//                if (!file.exists()) {
//                    file.createNewFile();
//                    byte[] btImg = readInputStream(inStream);
//                    writeImageToDisk(btImg, file);
//                }
//                listener.request(file_path);
//            }
//        } catch (Exception e) {
//            listener.request(OrangeHttpConstant.ERROR_DOWNLOAD_IMAGE);
//            e.printStackTrace();
//        } finally {
//            try {
//                if (inStream != null)
//                    inStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//    /*
//     * An InputStream that skips the exact number of bytes provided, unless it
//     * reaches EOF.
//     */
//    static class FlushedInputStream extends FilterInputStream {
//        public FlushedInputStream(InputStream inputStream) {
//            super(inputStream);
//        }
//
//        @Override
//        public long skip(long n) throws IOException {
//            long totalBytesSkipped = 0L;
//            while (totalBytesSkipped < n) {
//                long bytesSkipped = in.skip(n - totalBytesSkipped);
//                if (bytesSkipped == 0L) {
//                    int b = read();
//                    if (b < 0) {
//                        break; // we reached EOF
//                    } else {
//                        bytesSkipped = 1; // we read one byte
//                    }
//                }
//                totalBytesSkipped += bytesSkipped;
//            }
//            return totalBytesSkipped;
//        }
//    }
//
//    /**
//     * The actual AsyncTask that will asynchronously download the image.
//     */
//    class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {
//        private String url;
//        private final WeakReference<ImageView> imageViewReference;
//
//        public BitmapDownloaderTask(ImageView imageView) {
//            imageViewReference = new WeakReference<ImageView>(imageView);
//        }
//
//        /**
//         * Actual download method.
//         */
//        @Override
//        protected Bitmap doInBackground(String... params) {
//            url = params[0];
//            String isRound = params[1];
//            int x=Integer.parseInt(params[2]);
//            int y=Integer.parseInt(params[3]);
//            float outerRadiusRat=Float.parseFloat(params[4]);
//            return downloadBitmap(url, Boolean.parseBoolean(isRound),x,y,outerRadiusRat);
//        }
//
//        /**
//         * Once the image is downloaded, associates it to the imageView
//         */
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            if (isCancelled()) {
//                bitmap = null;
//            }
//
//            addBitmapToCache(url, bitmap);
//
//            if (imageViewReference != null) {
//                ImageView imageView = imageViewReference.get();
//                BitmapDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);
//                // Change bitmap only if this process is still associated with
//                // it
//                // Or if we don't use any bitmap to task association
//                // (NO_DOWNLOADED_DRAWABLE mode)
//                if ((this == bitmapDownloaderTask) || (mode != Mode.CORRECT)) {
//                    imageView.setImageBitmap(bitmap);
//                }
//            }
//        }
//    }
//
//    /**
//     * A fake Drawable that will be attached to the imageView while the download
//     * is in progress.
//     * <p/>
//     * <p>
//     * Contains a reference to the actual download task, so that a download task
//     * can be stopped if a new binding is required, and makes sure that only the
//     * last started download process can bind its result, independently of the
//     * download finish order.
//     * </p>
//     */
//    static class DownloadedDrawable extends ColorDrawable {
//        private final WeakReference<BitmapDownloaderTask> bitmapDownloaderTaskReference;
//
//        public DownloadedDrawable(BitmapDownloaderTask bitmapDownloaderTask) {
//            super(Color.WHITE);
//            bitmapDownloaderTaskReference = new WeakReference<BitmapDownloaderTask>(
//                    bitmapDownloaderTask);
//        }
//
//        public BitmapDownloaderTask getBitmapDownloaderTask() {
//            return bitmapDownloaderTaskReference.get();
//        }
//    }
//
//    public void setMode(Mode mode) {
//        this.mode = mode;
//        clearCache();
//    }
//
//	/*
//     * Cache-related fields and methods.
//	 *
//	 * We use a hard and a soft cache. A soft reference cache is too
//	 * aggressively cleared by the Garbage Collector.
//	 */
//
//    private static final int HARD_CACHE_CAPACITY = 10;
//    private static final int DELAY_BEFORE_PURGE = 10 * 1000; // in milliseconds
//
//    // Hard cache, with a fixed maximum capacity and a life duration
//    private final HashMap<String, Bitmap> sHardBitmapCache = new LinkedHashMap<String, Bitmap>(
//            HARD_CACHE_CAPACITY / 2, 0.75f, true) {
//
//        private static final long serialVersionUID = 8007073798642953390L;
//
//        @Override
//        protected boolean removeEldestEntry(
//                Entry<String, Bitmap> eldest) {
//            if (size() > HARD_CACHE_CAPACITY) {
//                // Entries push-out of hard reference cache are transferred to
//                // soft reference cache
//                sSoftBitmapCache.put(eldest.getKey(),
//                        new SoftReference<Bitmap>(eldest.getValue()));
//                return true;
//            } else
//                return false;
//        }
//    };
//
//    // Soft cache for bitmaps kicked out of hard cache
//    private final static ConcurrentHashMap<String, SoftReference<Bitmap>> sSoftBitmapCache = new ConcurrentHashMap<String, SoftReference<Bitmap>>(
//            HARD_CACHE_CAPACITY / 2);
//
//    private final Handler purgeHandler = new Handler();
//
//    private final Runnable purger = new Runnable() {
//        public void run() {
//            clearCache();
//        }
//    };
//
//    /**
//     * Adds this bitmap to the cache.
//     *
//     * @param bitmap The newly downloaded bitmap.
//     */
//    private void addBitmapToCache(String url, Bitmap bitmap) {
//        if (bitmap != null) {
//            synchronized (sHardBitmapCache) {
//                sHardBitmapCache.put(url, bitmap);
//            }
//        }
//    }
//
//    /**
//     * @param url The URL of the image that will be retrieved from the cache.
//     * @return The cached bitmap or null if it was not found.
//     */
//    private Bitmap getBitmapFromCache(String url) {
//        // First try the hard reference cache
//        synchronized (sHardBitmapCache) {
//            final Bitmap bitmap = sHardBitmapCache.get(url);
//
//            if (bitmap != null) {
//                // Bitmap found in hard cache
//                // Move element to first position, so that it is removed last
//                sHardBitmapCache.remove(url);
//                sHardBitmapCache.put(url, bitmap);
//                return bitmap;
//            }
//        }
//
//        // Then try the soft reference cache
//        SoftReference<Bitmap> bitmapReference = sSoftBitmapCache.get(url);
//        if (bitmapReference != null) {
//            final Bitmap bitmap = bitmapReference.get();
//            if (bitmap != null) {
//                // Bitmap found in soft cache
//                return bitmap;
//            } else {
//                // Soft reference has been Garbage Collected
//                sSoftBitmapCache.remove(url);
//            }
//        }
//
//        return null;
//    }
//
//    /**
//     * Clears the image cache used internally to improve performance. Note that
//     * for memory efficiency reasons, the cache will automatically be cleared
//     * after a certain inactivity delay.
//     */
//    public void clearCache() {
//        sHardBitmapCache.clear();
//        sSoftBitmapCache.clear();
//    }
//
//    /**
//     * Allow a new delay before the automatic cache clear is done.
//     */
//    private void resetPurgeTimer() {
//        purgeHandler.removeCallbacks(purger);
//        purgeHandler.postDelayed(purger, DELAY_BEFORE_PURGE);
//    }
//
//    /**
//     * 下载图片存放到本地
//     *
//     * @param url
//     */
//    public void saveBitmapToFile(String url, ImageSize imageSize) {
//        String urlWithImageSizeText = new StringBuilder().append(url)
//                .append(getImageSizeText(imageSize)).toString();
//        String filename = getUrlName(urlWithImageSizeText);
//        String file_path = getFilePath(filename, urlWithImageSizeText);
//        File file = new File(file_path);
//        if (file.exists()) {
//            return;
//        }
//        downloadBitmap(urlWithImageSizeText, false,0,0,0);
//    }
//
//    private String Md5(String plainText, int position) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            md.update(plainText.getBytes());
//            byte b[] = md.digest();
//
//            int i;
//
//            StringBuffer buf = new StringBuffer("");
//            for (int offset = 0; offset < b.length; offset++) {
//                i = b[offset];
//                if (i < 0)
//                    i += 256;
//                if (i < 16)
//                    buf.append("0");
//                buf.append(Integer.toHexString(i));
//            }
//
//            if (position == 32) {
//                return buf.toString();
//            }
//
//            if (position == 16) {
//                return buf.toString().substring(8, 24);
//            }
//
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 从本地文件夹获取图片，转bitmap
//     *
//     * @param url
//     * @return
//     */
//    private Bitmap getBimapFileCache(String url) {
//        String filename = getUrlName(url);
//        if (filename == null) {
//            return null;
//        }
//        String file_path = getFilePath(filename, url);
//        File file = new File(file_path);
//        if (file.exists()) {
//            Bitmap bitmap = decodeBitmap(file_path);
//            addBitmapToCache(url, bitmap);
//            return bitmap;
//        }
//        return null;
//    }
//
//    private Bitmap decodeBitmap(String path) {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;// 关闭分配空间
//        BitmapFactory.decodeFile(path, options);// 读取图片
//
//        int be = (int) (options.outHeight / (float) 200);// 按800计算压缩比
//        if (be <= 0) {
//            be = 1;
//        }
//        options.inSampleSize = be;
//        options.inJustDecodeBounds = false;// 打开分配空间
//        return BitmapFactory.decodeFile(path, options);// 读取压缩后图片
//    }
//
//    private String getUrlName(String url) {
//        int begin = url.lastIndexOf("/");
//        String file_path = url.substring(begin + 1);
//        int end = file_path.lastIndexOf("-");
//        if (end > 0) {
//            file_path = file_path.substring(0, end);
//            end = file_path.lastIndexOf(".");
//        }
//        if (end < 1) {
//            end = file_path.lastIndexOf(".");
//        }
//        if (end < 1) {
//            return "web";
//        }
//        String filename = file_path.substring(0, end);
//        return filename;
//    }

}
