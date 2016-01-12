package com.zerovoid.lib.util;


/**
 * 金钱的橙子化处理，即所有金额都保留最后两位小数
 *
 * Created by 绯若虚无 on 2015/12/31.
 *
 * @version 20160111_绯若虚无
 */
public class MoneyUtil {

    /** 展示和提交，这里只处理符合标准格式的金额（非数字、多小数点，无法处理，应该在输入时处理）1、整数补.00,2、一位小数补0，超过两位小数，截取 */
    public static String handleMoney(String money) {
        /*第二种方法：new DecimalFormat("0.00").format(num) */
        String moneyResult = "0.00";
        if (!StringUtil.isBlank(money)) {
            if (money.contains(".")) {
                int indexDot = money.indexOf(".");
                if (indexDot == 0) {
                    moneyResult = "0.00";
                } else if ((indexDot + 1) == money.length()) {//尾部一个点，加上00
                    moneyResult = money + "00";
                } else if ((money.length() - (indexDot + 1)) == 1) {//判断小数点后的位数
                    moneyResult = money + "0";
                } else if ((money.length() - (indexDot + 1)) > 2) {
                    moneyResult = money.substring(0, indexDot + 1 + 2);//开始 + 小数点 + 小数点后两位
                } else {
                    moneyResult = money;
                }
            } else {//整数补零
                moneyResult = money + ".00";
            }
        }
        return moneyResult;
    }
}
