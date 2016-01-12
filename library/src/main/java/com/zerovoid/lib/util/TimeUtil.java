package com.zerovoid.lib.util;

import android.annotation.SuppressLint;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * 时间处理的工具类
 *
 * @author 绯若虚无
 * @version 160112
 */
public class TimeUtil {

    public static String time1ToTime2(String time1, String time2) {
        String time = "";
        String firstTime;
        String secondTime;
        if (!StringUtil.isContainBlank(time1, time2)) {
            firstTime = timeStampToDate(Long.parseLong(time1),
                    "yyyy-MM-dd");
            secondTime = timeStampToDate(Long.parseLong(time2),
                    "yyyy-MM-dd");
            time = firstTime + " 至 " + secondTime;
        }

        return time;
    }


    public static String timeStampToDate(String timeMilis) {
        String time = "";
        if (timeMilis != null && !timeMilis.equals("")
                && !timeMilis.equals("null")) {
            time = timeStampToDate(Long.parseLong(timeMilis),
                    "yyyy/MM/dd HH:mm:ss");
        }
        return time;
    }

    public static String timeStampToDate2(String timeMilis) {
        String time = "";
        if (timeMilis != null && !timeMilis.equals("")
                && !timeMilis.equals("null")) {
            time = timeStampToDate(Long.parseLong(timeMilis),
                    "yyyy-MM-dd HH:mm:ss");
        }
        return time;
    }

    public static String timeStampToDate(String timeMilis, String format) {
        long time = 0;
        try {
            time = Long.parseLong(timeMilis);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return timeStampToDate(time, format);
    }


    @SuppressLint("SimpleDateFormat")
    public static String timeStampToDate(long timeMilis, String format) {

        Timestamp ts = new Timestamp(timeMilis);
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat(format);
        try {

            tsStr = sdf.format(ts);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tsStr;
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 返回当前系统时间
     */
    public static String getDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    /**
     * 返回当前系统时间
     */
    public static String getDataTime() {
        return getDataTime("HH:mm");
    }

    /**
     * 毫秒值转换为mm:ss
     *
     * @param ms
     * @author kymjs
     */
    public static String timeFormat(int ms) {
        StringBuilder time = new StringBuilder();
        time.delete(0, time.length());
        ms /= 1000;
        int s = ms % 60;
        int min = ms / 60;
        if (min < 10) {
            time.append(0);
        }
        time.append(min).append(":");
        if (s < 10) {
            time.append(0);
        }
        time.append(s);
        return time.toString();
    }

    /**
     * 将字符串转位日期类型
     *
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 将时间戳转化为今天、昨天
     * 这个方法太复杂了，性能低下
     *
     * @param timeStamp 时间戳
     * @return
     */
    public static String convertTimestampToHanZi(String timeStamp) {
        SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (timeStamp == null || "".equals(timeStamp)) {
            return "";
        }
        String strDate;
        strDate = TimeUtil.timeStampToDate(timeStamp, "yyyy-MM-dd HH:mm");
        LogE.E(strDate);
        Date date;
        try {
            date = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return strDate;
        }

        Calendar inputCld = Calendar.getInstance();

        Calendar today = Calendar.getInstance();    //今天

        today.set(Calendar.YEAR, inputCld.get(Calendar.YEAR));
        today.set(Calendar.MONTH, inputCld.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, inputCld.get(Calendar.DAY_OF_MONTH));
        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance();    //昨天

        yesterday.set(Calendar.YEAR, inputCld.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, inputCld.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, inputCld.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        inputCld.setTime(date);

        if (inputCld.after(today)) {
            return "今天 ";
        } else if (inputCld.before(today) && inputCld.after(yesterday)) {
            return "昨天 ";
        } else {
            int index = timeStamp.indexOf(" ") + 1;
            return timeStamp.substring(0, index);
        }
    }

    /**
     * 字符串转为long类型
     *
     * @throws ParseException
     */
    @SuppressLint("SimpleDateFormat")
    public static long StringTranLong(String paramString) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(paramString);
        return date.getTime();
    }

    /**
     * 字符串转为long类型
     *
     * @throws ParseException
     */
    @SuppressLint("SimpleDateFormat")
    public static long StringTranLong2(String paramString)
            throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm");
        Date date = simpleDateFormat.parse(paramString);
        return date.getTime();
    }


    public static long[] countTimeVal(String paramString1, String paramString2) {
        long[] arrayOfLong = new long[4];
        try {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm");
            Date localDate1 = localSimpleDateFormat.parse(paramString1);
            Date localDate2 = localSimpleDateFormat.parse(paramString2);
            long l = (localDate1.getTime() - localDate2.getTime()) / 1000L;
            arrayOfLong[0] = (l / 86400L);
            arrayOfLong[1] = (l % 86400L / 3600L);
            arrayOfLong[2] = (l % 3600L / 60L);
            arrayOfLong[3] = (l % 60L / 60L);
            return arrayOfLong;
        } catch (Exception localException) {
        }
        return arrayOfLong;
    }

    public static String getCountTime(String paramString1, String paramString2) {
        long[] arrayOfLong = countTimeVal(paramString1, paramString2);
        if (arrayOfLong == null)
            return "";
        if (arrayOfLong[0] > 0L)
            return arrayOfLong[0] + "天前";
        if (arrayOfLong[1] > 0L)
            return arrayOfLong[1] + "小时前";
        if (arrayOfLong[2] > 0L)
            return arrayOfLong[2] + "分钟前";
        return "1分钟前";
    }

    public static String getCountTime2(String paramString1, String paramString2) {
        long[] arrayOfLong = countTimeVal(paramString1, paramString2);
        if (arrayOfLong == null)
            return "";
        if (arrayOfLong[0] > 0L)
            return arrayOfLong[0] + "天" + arrayOfLong[1] + "小时"
                    + arrayOfLong[2] + "分钟";
        if (arrayOfLong[1] > 0L)
            return arrayOfLong[1] + "小时后";
        if (arrayOfLong[2] > 0L)
            return arrayOfLong[2] + "分钟后";
        return null;
    }

    public String convertMillisecondsToDay(long milliseconds) {
        if (milliseconds < 0) {
            return null;
        }
        int seconds = (int) (milliseconds / 1000);
        int s = seconds % 60;
        int mm = (seconds / 60) % 60;
        int hh = (seconds / 60 / 60) % 24;
        int dd = seconds / 60 / 60 / 24;
        return dd + "天" + hh + "小时" + mm + "分钟" + s + "秒";
    }


    /** 将秒数转为小时 */
    public static String convertSecondToHour(String second) {
        String result = "";
        int secondInt;
        try {
            secondInt = Integer.parseInt(second);
            int h = secondInt / 3600;
            int m = (secondInt - h * 3600) / 60;
            int s = secondInt % 60;
            result = String.format("%02d:%02d:%02d", h, m, s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getChineseDateWithOutYearAndSecond(String paramString) {
        return getCustomerDate("MM月dd日 HH:mm", paramString);
    }

    public static int getCurMonthNum() {
        return 1 + Calendar.getInstance().get(Calendar.MONTH);
    }

    public static String getCustomerDate(String paramString1,
                                         String paramString2) {
        try {
            Date localDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .parse(paramString2);
            String str = new SimpleDateFormat(paramString1).format(localDate);
            return str;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return "";
    }

    public static String getDate() {
        SimpleDateFormat localSimpleDateFormat = (SimpleDateFormat) SimpleDateFormat
                .getDateInstance();
        localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        localSimpleDateFormat.applyPattern("yyyy-MM-dd");
        return localSimpleDateFormat
                .format(new Date(System.currentTimeMillis()));
    }

    public static String getDate(Date paramDate) {
        return SimpleDateFormat.getDateInstance()
                .format(paramDate);
    }

    public static Date getDate(String paramString) {
        try {
            SimpleDateFormat localSimpleDateFormat = (SimpleDateFormat) SimpleDateFormat
                    .getDateTimeInstance();
            localSimpleDateFormat.applyPattern("yyyyMMdd HH:mm:ss");
            Date localDate = localSimpleDateFormat.parse(paramString);
            return localDate;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return new Date();
    }

    public static String getDatePart(String paramString) {
        return paramString.substring(0, paramString.indexOf(" "));
    }

    public static String getDateStamp() {
        SimpleDateFormat localSimpleDateFormat = (SimpleDateFormat) SimpleDateFormat
                .getDateInstance();
        localSimpleDateFormat.applyPattern("yyyy/MM/dd");
        return localSimpleDateFormat
                .format(new Date(System.currentTimeMillis()));
    }

    public static String getDateTime() {
        return SimpleDateFormat.getDateTimeInstance()
                .format(new Date(System.currentTimeMillis()));
    }

    public static String getDateWithOutSecond(String paramString) {
        return paramString.substring(0, -3 + paramString.length());
    }

    public static int getDayCountOfCurMonth() {
        return Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static String getDayFromDate(String paramString) {
        return paramString.substring(1 + paramString.indexOf("-"));
    }

    public static int getDayValue(String paramString) {
        SimpleDateFormat localSimpleDateFormat = (SimpleDateFormat) SimpleDateFormat
                .getTimeInstance();
        localSimpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
        try {
            Date localDate = localSimpleDateFormat.parse(paramString);
            Calendar localCalendar = Calendar.getInstance();
            localCalendar.setTime(localDate);
            int i = localCalendar.get(Calendar.DAY_OF_MONTH);
            return i;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return 0;
    }

    public static String getHeadTimeStamp() {
        SimpleDateFormat localSimpleDateFormat = (SimpleDateFormat) SimpleDateFormat
                .getDateTimeInstance();
        localSimpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
        return localSimpleDateFormat
                .format(new Date(System.currentTimeMillis()));
    }

    public static String getMonthFromYM(String paramString) {
        return paramString.substring(1 + paramString.indexOf("-"),
                paramString.length());
    }

    public static String getRefreshTime() {
        SimpleDateFormat localSimpleDateFormat = (SimpleDateFormat) SimpleDateFormat
                .getDateTimeInstance();
        localSimpleDateFormat.applyPattern("MM-dd HH:mm");
        return localSimpleDateFormat
                .format(new Date(System.currentTimeMillis()));
    }

    public static Date getTheDate(int paramInt) {
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(new Date());
        localCalendar.add(Calendar.DAY_OF_MONTH, paramInt);
        return localCalendar.getTime();
    }

    public static String getTimePart(String paramString) {
        return paramString.substring(1 + paramString.indexOf(" "),
                paramString.length());
    }

    public static String getTimeStamp() {
        SimpleDateFormat localSimpleDateFormat = (SimpleDateFormat) SimpleDateFormat
                .getDateTimeInstance();
        localSimpleDateFormat.applyPattern("yyyyMMddHHmmss");
        return localSimpleDateFormat
                .format(new Date(System.currentTimeMillis()));
    }

    public static String getDayOfWeekHanZi(Date paramDate) {
        String dayOfWeekHanZi = "";
        if (paramDate == null) {
            return dayOfWeekHanZi;
        }
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(paramDate);
        int dayOfWeek = localCalendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                dayOfWeekHanZi = "星期日";
                break;
            case Calendar.MONDAY:
                dayOfWeekHanZi = "星期一";
                break;
            case Calendar.TUESDAY:
                dayOfWeekHanZi = "星期二";
                break;
            case Calendar.WEDNESDAY:
                dayOfWeekHanZi = "星期三";
                break;
            case Calendar.THURSDAY:
                dayOfWeekHanZi = "星期四";
                break;
            case Calendar.FRIDAY:
                dayOfWeekHanZi = "星期五";
                break;
            case Calendar.SATURDAY:
                dayOfWeekHanZi = "星期六";
                break;
        }
        return dayOfWeekHanZi;
    }

    public static String getDayOfWeekHanZi(String paramString) {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        Date date = null;
        try {
            date = localSimpleDateFormat.parse(paramString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getDayOfWeekHanZi(date);
    }

    public static String getYearFromYM(String paramString) {
        return paramString.substring(0, paramString.indexOf("-"));
    }

    public static String getYearMonth(Calendar paramCalendar) {
        SimpleDateFormat localSimpleDateFormat = (SimpleDateFormat) SimpleDateFormat
                .getTimeInstance();
        localSimpleDateFormat.applyPattern("yyyy-MM");
        return localSimpleDateFormat.format(paramCalendar.getTime());
    }

    public static String getyyyy_MM() {
        SimpleDateFormat localSimpleDateFormat = (SimpleDateFormat) SimpleDateFormat
                .getDateInstance();
        localSimpleDateFormat.applyPattern("yyyy-MM");
        return localSimpleDateFormat
                .format(new Date(System.currentTimeMillis()));
    }

    public static boolean isSameDay(Date paramDate1, Date paramDate2) {
        Calendar localCalendar1 = Calendar.getInstance();
        Calendar localCalendar2 = Calendar.getInstance();
        localCalendar1.setTime(paramDate1);
        localCalendar2.setTime(paramDate2);
        return (localCalendar1.get(Calendar.YEAR) == localCalendar2.get(Calendar.YEAR))
                && (localCalendar1.get(Calendar.DAY_OF_YEAR) == localCalendar2.get(Calendar.DAY_OF_YEAR));
    }

    public static boolean isToday(Date paramDate) {
        return isSameDay(paramDate, new Date(System.currentTimeMillis()));
    }

    public static int timeCompare(String paramString1, String paramString2) {
        try {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm");
            int i = localSimpleDateFormat.parse(paramString1).compareTo(
                    localSimpleDateFormat.parse(paramString2));
            return i;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return 0;
    }

    public static String toChineseDateWithoutSecond(String paramString) {
        return getCustomerDate("yyyy年MM月dd日 HH:mm", paramString);
    }

    public static String toHomeTaskListDateString(Date paramDate) {
        SimpleDateFormat localSimpleDateFormat = (SimpleDateFormat) SimpleDateFormat
                .getDateInstance();
        localSimpleDateFormat.applyPattern("yyyy年M月d日    ");
        return localSimpleDateFormat.format(paramDate) + getDayOfWeekHanZi(paramDate);
    }

    public static String toHomeTaskListTimeString(Date paramDate) {
        SimpleDateFormat localSimpleDateFormat = (SimpleDateFormat) SimpleDateFormat
                .getTimeInstance();
        localSimpleDateFormat.applyPattern("HH:mm");
        return localSimpleDateFormat.format(paramDate);
    }

    public static String toString(Calendar paramCalendar) {
        SimpleDateFormat localSimpleDateFormat = (SimpleDateFormat) SimpleDateFormat
                .getTimeInstance();
        localSimpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
        return localSimpleDateFormat.format(paramCalendar.getTime());
    }

    public static String toTaskDetailTimeString(Date paramDate1, Date paramDate2) {
        SimpleDateFormat localSimpleDateFormat = (SimpleDateFormat) SimpleDateFormat
                .getTimeInstance();
        StringBuffer localStringBuffer = new StringBuffer();
        localSimpleDateFormat.applyPattern("yyyy年M月d日    ");
        localStringBuffer.append(localSimpleDateFormat.format(paramDate1));
        localSimpleDateFormat.applyPattern("HH:mm");
        localStringBuffer.append(" ");
        localStringBuffer.append(localSimpleDateFormat.format(paramDate1));
        localStringBuffer.append("-");
        localStringBuffer.append(localSimpleDateFormat.format(paramDate2));
        return localStringBuffer.toString();
    }

    public static String toTaskListDateString(Date paramDate) {
        SimpleDateFormat localSimpleDateFormat = (SimpleDateFormat) SimpleDateFormat
                .getDateInstance();
        localSimpleDateFormat.applyPattern(" yyyy.MM.dd");
        String str = localSimpleDateFormat.format(paramDate);
        return getDayOfWeekHanZi(paramDate) + str;
    }

    public static String setTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime();   //这个时间就是日期往后推一天的结果
        return sdf.format(date);
    }
}
