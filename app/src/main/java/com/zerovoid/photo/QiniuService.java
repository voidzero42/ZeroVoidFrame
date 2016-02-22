package com.zerovoid.photo;

import java.util.Date;
import java.util.List;

/**
 * 七牛云服务
 *
 * @author 陈翔
 */
public class QiniuService {
    private static final String TAG = QiniuService.class.getSimpleName();

	/* private final String TAG = QiniuService.class.getSimpleName(); */

    /** 上传服务token */
    private static String upToken = null;
    /** 图片地址数组 */
    private List<String> pathlist = null;
    private String fileName = null;
    /** 上传成功后返回的文件名集合以#分隔 */
    private StringBuffer fileNames = new StringBuffer();
    private static String url = "";
    private QiniuListener qiniuListener = null;
    private int uplodaCount = 0;
    private final int LIST_FILES = 1;
    private final int HEAD_IMG_FILE = 2;
    private final int LIST_IMG_FILES = 3;
    private final int FILE = 4;

    /**
     * 获取upToken信息
     *
     * @param type LIST_IMG_FILES=1, HEAD_IMG_FILE=2, LIST_FILES=3, FILE=4;
     * @throws Exception
     */
    private void upload2Token(final int type) throws Exception {
//        OrangeHttpUtil.getInstance().getWithCallback(Constant.URL_QINIU, new OrangeHttpUtil.ResponseCallBack() {
//            @Override
//            public void handleResponse(JSONObject response, int errCode) {
//                if (OrangeErrorHandler.getInstance().isSuccess(response)) {
//                    try {
//                        upToken = response.getString("upToken");
//                        url = response.getString("imageUrl");
//                        LogE.E(TAG, "token=" + upToken);
//                        LogE.E(TAG, "url=" + url);
//                        switch (type) {
//                            case LIST_IMG_FILES:
//                                upload();
//                                break;
//                            case HEAD_IMG_FILE:
//                                upload(fileName, HEAD_IMG_FILE);
//                                break;
//                            case LIST_FILES:
//                                uploadFiles(pathlist);
//                                break;
//                            case FILE:
//                                upload(fileName, FILE);
//                                break;
//                            default:
//                                break;
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        HttpUtil.getHttp().get(Constant.URL_QINIU, null, 1001, null,
//                new ICallBack() {
//
//                    @Override
//                    public void request(String buf, Handler handler, int wat)
//                            throws Exception {
//                        if (buf.equals("500")) {
//
//                            return;
//                        }
//                        Map<String, Object> mapQiniu = JSONHelper
//                                .jsonToMap(buf);
//                        if (mapQiniu.get(Constant.RESULT_MESSAGE_KEY).equals(
//                                OrangeHttpConstant.RESULT_OK)) {
//                            upToken = mapQiniu.get("upToken").toString();
//                            url = mapQiniu.get("imageUrl").toString();
//                            switch (type) {
//                                case LIST_IMG_FILES:
//                                    upload();
//                                    break;
//                                case HEAD_IMG_FILE:
//                                    upload(fileName, HEAD_IMG_FILE);
//                                    break;
//                                case LIST_FILES:
//                                    uploadFiles(pathlist);
//                                    break;
//                                case FILE:
//                                    upload(fileName, FILE);
//                                    break;
//                                default:
//                                    break;
//                            }
//
//                        }
//                    }
//                });
    }

    /**
     * 图片上传服务
     *
     * @param list
     * @param qiniuListener
     */
    public void imgUpload_(List<String> list, QiniuListener qiniuListener) {

        this.qiniuListener = qiniuListener;
        pathlist = list;
        /* 如果upToken为空则去服务端获取 */
        if (upToken == null) {
            try {
                upload2Token(LIST_IMG_FILES);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            upload();
        }
    }

    /**
     * 上传图片到七牛服务器
     */
    private void upload() {
        if (pathlist.size() > 0) {
            upload(pathlist.get(0));
        } else {

        }
    }

    /**
     * 传入图片地址上传图片
     *
     * @param patch
     */
    private void upload(String patch) {

//        String data = BitmapUtil.saveBitmapToFile(patch);
//        String key = patch.substring(patch.lastIndexOf(".") - 6,
//                patch.lastIndexOf(".") - 1)
//                + getDate() + patch.substring(patch.lastIndexOf("."));
//        UploadManager uploadManager = new UploadManager();
//        uploadManager.put(data, key, upToken, new UpCompletionHandler() {
//            @Override
//            public void complete(String key, ResponseInfo info,
//                                 JSONObject response) {
//
//                if (info.error == null) {
//                    fileNames.append(Constant.IMAGE_SPLITE + url
//                            + File.separator + key);
//                    if (pathlist != null && pathlist.size() > 0) {
//                        pathlist.remove(0);
//                        if (pathlist.size() > 0) {
//                            Log.i("TAG", fileNames.toString());
//                            upload(pathlist.get(0));
//                        } else {
//                            if (qiniuListener != null) {
//                                qiniuListener.request(fileNames.toString()
//                                        .substring(2));
//                            }
//                        }
//                    } else {
//                        if (qiniuListener != null) {
//                            qiniuListener.request(fileNames.toString()
//                                    .substring(2));
//                        }
//                    }
//                } else {
//                    qiniuListener.request("500");
//                }
//
//            }
//        }, null);
    }

    public long getDate() {
        Date date = new Date();
        return date.getTime();
    }

    private void uploadFiles(List<String> list) {
//        for (int i = 0; i < list.size(); i++) {
//            File file = new File(list.get(i));
//            if (!file.exists()) {
//                qiniuListener.request("500");
//                break;
//            }
//            String data = BitmapUtil.saveBitmapToFile(file.getPath());
//            file = new File(data);
//            String key = file.getName();
//            UploadManager uploadManager = new UploadManager();
//            uploadManager.put(data, key, upToken, new UpCompletionHandler() {
//                @Override
//                public void complete(String key, ResponseInfo info,
//                                     JSONObject response) {
//                    uplodaCount++;
//                    if (info.error == null) {
//                        fileNames.append(Constant.IMAGE_SPLITE + url
//                                + File.separator + key);
//                    }
//                    totalUploadFile();
//                }
//            }, null);
//        }
    }

    private void totalUploadFile() {
//        int count = 0;
//        if (uplodaCount == pathlist.size()) {
//            count = fileNames.toString().split(Constant.IMAGE_SPLITE).length;
//            if ((count - 1) == uplodaCount) {
//                qiniuListener.request(fileNames.toString().substring(2));
//            } else {
//                qiniuListener.request("500");
//            }
//        }
    }

    public void imgUpload(String fileName, QiniuListener qiniuListener) {
//        this.fileName = fileName;
//        this.qiniuListener = qiniuListener;
//
//        if (upToken == null) {// 如果upToken为空则去服务端获取
//            LogE.E(TAG, "token=null");
//            try {
//                upload2Token(FILE);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            LogE.E(TAG, "token=" + upToken);
//            upload(fileName, FILE);
//        }
    }

    /**
     * 图片上传服务
     *
     * @param list
     * @param qiniuListener
     */
    public void imgUpload(List<String> list, QiniuListener qiniuListener) {

        this.qiniuListener = qiniuListener;
        this.pathlist = list;
        if (upToken == null) {// 如果upToken为空则去服务端获取
            try {
                upload2Token(LIST_IMG_FILES);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            uploadFiles(pathlist);
        }
    }

    /**
     * 上传单个头像文件
     *
     * @param photoPath     头像本地文件路径
     * @param qiniuListener 七牛监听器
     */
    public void headportraitUpload(String photoPath, QiniuListener qiniuListener) {
        this.fileName = photoPath;
        this.qiniuListener = qiniuListener;
        if (upToken == null) {// 如果upToken为空则去服务端获取
            try {
                upload2Token(HEAD_IMG_FILE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            upload(photoPath, HEAD_IMG_FILE);
        }
    }

    /**
     * 上传单个文件
     *
     * @param filePath 文件路径
     * @param type
     */
    public void upload(String filePath, int type) {
//        File file = new File(filePath);
//        String newFilePath;
//        if (!file.exists()) {// 原文件不存在
//            qiniuListener.request("500");
//            return;
//        }
//        newFilePath = BitmapUtil.saveBitmapToFile(file.getPath());
//        LogE.E(TAG, "newFilePath = "+String.valueOf(newFilePath));
//        file = new File(newFilePath);
//        String key = file.getName();
//        UploadManager uploadManager = new UploadManager();
//        uploadManager.put(newFilePath, key, upToken, new UpCompletionHandler() {
//            @Override
//            public void complete(String key, ResponseInfo info,
//                                 JSONObject response) {
//                if (response != null) {
//                    LogE.E(TAG, response.toString());
//                } else {
//                    LogE.E(TAG, "response = null");
//                }
//
//                String result;
//                if (info.isOK()) {
//                    if (!url.equals("")) {
//                        /* 上传成功，文件的网络地址 */
//                        result = url + File.separator + key;
//                        LogE.E(TAG, "result = "+result);
//                    } else {
//                        result = "500";
//                    }
//                } else {
//                    result = "500";
//                }
//                if (qiniuListener != null) {
//                    qiniuListener.request(result);
//                }
//            }
//        }, null);
    }

    public interface QiniuListener {
        public void request(String request);
    }
}
