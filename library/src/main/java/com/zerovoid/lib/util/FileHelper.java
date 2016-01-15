package com.zerovoid.lib.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 通话记录文件存储
 *
 * @author Administrator
 */
public class FileHelper {

    private Context mContext;
    private String fileName;
    private final String FOLDER_PATH_IMG = Environment
            .getExternalStorageDirectory() + "/orangelife/cache/img/";
    private final String FOLDER_PATH_TEMP = Environment
            .getExternalStorageDirectory() + "/orangelife/cache/temp/";

    public FileHelper(Context context, String name) {
        mContext = context;
        fileName = name;
    }

    /**
     * 删除路径下的所有文件
     *
     * @param directory 路径
     */
    public void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    /**
     * 清除项目缓存文件
     */
    public void cleanCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    public void delete(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                delete(childFiles[i]);
            }
            file.delete();
        }
    }

    /**
     * 存储数据到本地文件中
     *
     * @param map  传入的需要存储的内容信息
     * @param mode 存储方式
     */
    @SuppressWarnings("rawtypes")
    public void saveFile(Map map, int mode) {
        FileOutputStream out = null;
        String value = map2string(map);
        try {
            out = mContext.openFileOutput(fileName, mode);
            out.write(value.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除通话记录
     *
     * @return 是否删除成功
     */
    public boolean deleteFile(String fileName) {
        File file = new File(mContext.getFilesDir(), fileName);
        if (file.exists()) {
            file.delete();
            return true;
        }
        return false;
    }

    /**
     * 将map的数据转换为String数据
     *
     * @param map 需要被转换的数据
     * @return String类型的数据
     */
    @SuppressWarnings("rawtypes")
    public String map2string(Map map) {

        StringBuffer buf = new StringBuffer();
        /* 将数据转换为迭代器的类型 */
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            String val = entry.getValue().toString();
            if (val != null && !"".equals(val)) {
                buf.append("|");
                buf.append(key);
                buf.append("$");
                buf.append(val);

            }
        }
        /* 这里在一条数据的末尾叫上分隔符来分割每一条数据 */
        buf.append("##");
		/* 数据返回格式为|**|**|,这里是除去第一个竖线 */
        return buf.toString().substring(1);
    }

    /**
     * 将String类型的数据转换成list类型的数据
     *
     * @param str 传入的String类型的数据
     * @return 返回list类型的数据
     */
    public List<Map<String, String>> string2List(String imageSplit,String str) {

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        if (str == null || str.equals("")) {
            return list;
        }

        String[] values = str.split(imageSplit);

		/* 倒序取数据 */
        for (int i = values.length; i > 0; i--) {
            list.add(string2Map(values[i - 1]));
        }

        return list;

    }

    /**
     * 将String类型的数据转换成map类型的数据
     *
     * @param str
     * 传入的String类型的数据
     * @return map类型的数据
     */
    String args[] = {"", ""};

    public HashMap<String, String> string2Map(String str) {
        HashMap<String, String> map = new HashMap<String, String>();
        String[] values = str.split("[|]");

        for (int i = 0; i < values.length; i++) {
            String[] s = values[i].split("[$]");
            if (s.length == 2) {
                String key = s[0];
                String value = s[1];
                map.put(key, value);
            }

        }
        return map;
    }

    /**
     * 从文件中获取通话历史记录
     *
     * @return 通话历史记录的列表
     */
    public List<Map<String, String>> getFile() {
        // 获得读取的文件的名称
        FileInputStream fin;
        List<Map<String, String>> map = null;
        try {
            fin = mContext.openFileInput(fileName);

            int length = fin.available();
            if (length == 0) {
                return null;
            }
            byte[] buffer = new byte[length];

            fin.read(buffer);

            String res = URLEncoder.encode(buffer.toString(), "UTF-8");

            return string2List("#",res);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 删除文件夹下所有文件
     *
     * @param patch
     * @return
     */
    private boolean clearFile(String patch) {
        File file = new File(patch);
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
        }
        return true;
    }

    /**
     * 删除 照片和临时文件夹 下内容
     *
     * @return
     */
    public boolean clearFile() {
        boolean flag = clearFile(FOLDER_PATH_IMG);
        if (flag)
            flag = clearFile(FOLDER_PATH_TEMP);

        return flag;
    }

}
