package com.zerovoid.lib.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具包
 *
 * @version 20160111_绯若虚无
 */
public class StringUtil {

    public static void setText(View view, String str) {
        if (!isBlank(str)) {
            if (view instanceof TextView || view instanceof Button) {
                ((TextView) view).setText(str);
            }
            if (view instanceof Button) {
                ((Button) view).setText(str);
            }
        } else {
            if (view instanceof TextView || view instanceof Button) {
                ((TextView) view).setText("");
            }
        }
    }

    /**
     * 根据字符串ID获取字符串
     */
    public static String getString(Context context, int id) {
        return context.getResources().getString(id);
    }

    /**
     * 有些参数在URL中间，可以直接替换掉参数
     */
    public static String getUrlPath(String path, String replace) {
        return path.replace("$s$", replace);
    }

    /**
     * 去除空格换行
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 判断给定字符串是否空白串 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     */
    public static boolean isContainBlank(String... input) {
        for (int i = 0; i < input.length; i++) {
            if (isBlank(input[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBlank(String input) {
        if (input == null || "".equals(input) || "null".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return Pattern
                .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")
                .matcher(email).matches();
    }

    /**
     * 判断是不是一个合法的手机号码
     */
    public static boolean isPhone(String phoneNum) {
        if (phoneNum == null || phoneNum.trim().length() != 11) {
            return false;
        }
//        Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
//        // .compile("^(((d{3}))|(d{3}-))?13[0-9]d{8}|15[89]d{8}");
//        Matcher m = p.matcher(phoneNum);
//        return m.matches();
        return true;
    }

    /**
     * 判断是不是一个合法的车牌号
     */
    public static boolean isPlate(String plateNum) {
        if (plateNum == null || plateNum.trim().length() == 0) {
            return false;
        }
        Pattern p = Pattern.compile("^([\u4e00-\u9fa5]|[A-Z]{2})([0-9]{2}|[A-Z])-([0-9A-Za-z]){5}$");
        Matcher m = p.matcher(plateNum);
        return m.matches();
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * String转long
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * String转double
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static double toDouble(String obj) {
        try {
            return Double.parseDouble(obj);
        } catch (Exception e) {
        }
        return 0D;
    }

    /**
     * 字符串转布尔
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 判断一个字符串是不是数字
     */
    public static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 获取AppKey
     */
    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = metaData.getString(metaKey);
            }
        } catch (NameNotFoundException e) {

        }
        return apiKey;
    }

    /**
     * 获取手机IMEI码
     */
    public static String getPhoneIMEI(Activity aty) {
        TelephonyManager tm = (TelephonyManager) aty
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * MD5加密
     */
    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(
                    string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * MD5加密
     *
     * @param plainText 加密内容
     * @param position  位数 可以有 32位 和64位
     * @return
     */

    public static String Md5(String plainText, int position) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            if (position == 32) {
                return buf.toString();
            }

            if (position == 16) {
                return buf.toString().substring(8, 24);
            }


        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * KJ加密
     */
    public static String KJencrypt(String str) {
        char[] cstr = str.toCharArray();
        StringBuilder hex = new StringBuilder();
        for (char c : cstr) {
            hex.append((char) (c + 5));
        }
        return hex.toString();
    }

    /**
     * KJ解密
     */
    public static String KJdecipher(String str) {
        char[] cstr = str.toCharArray();
        StringBuilder hex = new StringBuilder();
        for (char c : cstr) {
            hex.append((char) (c - 5));
        }
        return hex.toString();
    }

    /**
     * 获取相关参数的值
     *
     * @param url
     * @param i
     * @return
     */
    public static String analyzeUrl(String url, int i) {
        String[] s = url.split("[?]");
        String[] iterator = s[1].split("[&]");
        return iterator[i].split("[=]")[1];
    }

    /**
     * 字符串转码
     */
    public static String encode(String string) {
        String value = "";
        try {
            value = URLEncoder.encode(string, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 版本比对
     *
     * @param newVersion
     * @param oldVersion
     * @return
     */

    public static boolean v2v(String newVersion, String oldVersion) {
        Pattern pattern = Pattern.compile("^(([0-9]+)\\.)*(([0-9]+|[a-z]+))$");


        if (!pattern.matcher(newVersion).matches() || !pattern.matcher(oldVersion).matches()) {
            return false;
        }
        String[] newVer = newVersion.split("\\.");
        String[] oldVer = oldVersion.split("\\.");

        for (int i = 0; i < newVer.length; i++) {
            if (newVer[i].compareTo(oldVer[i]) > 0) {
                return true;
            }
            if (newVer.length > oldVer.length) {
                return true;
            }
        }

        return false;

    }

//    public static int compareVersion(String version1, String version2) {
//        if (version1.equals(version2)) {
//            return 0;
//        }
//        String[] version1Array = version1.split("//.");
//        String[] version2Array = version2.split("//.");
//        int index = 0;
//        int minLen = Math.min(version1Array.length, version2Array.length);
//        int diff = 0;
//        while (index < minLen && (diff = Integer.parseInt(version1Array[index]) - Integer.parseInt(version2Array[index])) == 0) {
//            index++;
//        }
//        if (diff == 0) {
//            for (int i = index; i < version1Array.length; i++) {
//                if (Integer.parseInt(version1Array[i]) > 0) {
//                    return 1;
//                }
//            }
//            for (int i = index; i < version2Array.length; i++) {
//                if (Integer.parseInt(version2Array[i]) > 0) {
//                    return -1;
//                }
//            }
//            return 0;
//        } else {
//            return diff > 0 ? 1 : -1;
//        }
//    }

    public static int compareVersion(String version1, String version2) {
        return compare(version1, 0, version2, 0);
    }
    public static int compare(String version1,int st1, String version2,int st2) {
        int ssa = 1;
        int ssb = 1;
        if (st1 == version1.length() || st1 == version1.length() + 1) {
            ssa = 0;
            if (st2 == version2.length() || st2 == version2.length() + 1) {
                return 0;
            }
        }
        if (st2 == version2.length() || st2 == version2.length() + 1) {
            ssb = 0;
            if (st1 == version1.length() || st1 == version1.length() + 1) {
                return 0;
            }
        }
        int i = st1;
        int a=0;
        if (ssa != 0) {
            String s1 = "";
            for (; i < version1.length(); i++) {
                if (version1.charAt(i) == '.')
                    break;
                else
                    s1 = s1 + version1.charAt(i);
            }
            a = Integer.valueOf(s1);
        }
        int j = st2;
        int b=0;
        if (ssb != 0) {
            String s2 = "";
            for (; j < version2.length(); j++) {
                if (version2.charAt(j) == '.')
                    break;
                else
                    s2 = s2 + version2.charAt(j);
            }
            b = Integer.valueOf(s2);
        }
        if (a > b)
            return 1;
        else {
            if (a < b)
                return -1;
            else {
                if(ssa==0||ssb==0)return 0;
                else return compare(version1, i + 1, version2, j + 1);
            }
        }
    }
}