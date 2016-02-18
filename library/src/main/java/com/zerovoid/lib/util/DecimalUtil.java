package com.zerovoid.lib.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DecimalUtil {
	private static BigDecimal b1;
	private static BigDecimal b2;
	/**
	 * 金钱加法
	 *
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static String add(String v1, String v2) {
		setDefalutValue(v1,v2);
		return b1.add(b2).toString();
	}

	/**
	 * 金钱减法
	 *
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static String subtract(String v1, String v2) {
		setDefalutValue(v1,v2);
		return b1.subtract(b2).toString();
	}

	/**
	 * 金钱乘法
	 *
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static String multiply(String v1, String v2) {
		setDefalutValue(v1,v2);
		return b1.multiply(b2).toString();
	}

	/**
	 * 乘法
	 *
	 * @param v1
	 *            乘数
	 * @param v2
	 *            被乘数
	 * @param scale
	 *            小数点保留位数
	 * @return
	 */
	public static String multiplyWithScale(String v1, String v2, int scale) {
		setDefalutValue(v1,v2);
		BigDecimal result = b1.multiply(b2);
		result = result.setScale(scale);
		return result.toString();
	}

	/**
	 * 金钱除法
	 *
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static String divide(String v1, String v2) {
		setDefalutValue(v1,v2);
		return b1.divide(b2).toString();
	}

	/**
	 * 如果除不断，产生无限循环小数，那么就会抛出异常，解决方法就是对小数点后的位数做限制
	 *
	 * @param v1
	 * @param v2
	 * @param roundingMode
	 *            小数模式 ，DOWN，表示直接不做四舍五入，参考{@link RoundingMode}
	 * @return
	 */
	public static String divideWithRoundingDown(String v1, String v2) {
		return divideWithRoundingMode(v1, v2, RoundingMode.DOWN);
	}

	/** 选择小数部分处理方式 */
	public static String divideWithRoundingMode(String v1, String v2,
												RoundingMode roundingMode) {
		return divideWithRoundingModeAndScale(v1, v2, RoundingMode.DOWN, 0);
	}

	/**
	 * 选择小数点后部分处理方式，以及保留几位小数
	 *
	 * @param v1
	 *            除数
	 * @param v2
	 *            被除数
	 * @param roundingMode
	 *            小数处理模式
	 * @param scale
	 *            保留几位
	 * @return
	 */
	public static String divideWithRoundingModeAndScale(String v1, String v2,
														RoundingMode roundingMode, int scale) {
		setDefalutValue(v1,v2);
		return b1.divide(b2, scale, roundingMode).toString();
	}



	private static void setDefalutValue(String v1,String v2){
		if(v1==null){
			v1="0";
		}
		if(v2==null){
			v2="0";
		}
		b1 = new BigDecimal(v1);
		b2 = new BigDecimal(v2);
	}

	/**
	 * 科学计数法转正常数字
	 * @param scientific
	 * @return
	 */
	public static String ScientificToString(String scientific){
		BigDecimal db = new BigDecimal(scientific);
		return db.toPlainString();
	}

	public static String spitNumber(float f){
		int fi=(int)f;
		if(f==fi){
			return fi+"";
		}else{
			return f+"";
		}
	}
}
