package com.zerovoid.orange.http;

import android.os.Environment;

/**
 * 服务端的专用参数
 */
public class OrangeHttpConstant {
    public static final String SP_NAME = "OrangelifeBase";
    /**
     * 下载图片过程中发生异常
     */
    public static final String ERROR_DOWNLOAD_IMAGE = "error";

    /** OrangeLife协议标准，授权令牌的KEY */
    public static final String RESULT_KEY_ACCESS_TOKEN = "authToken";
    /** OrangeLife协议标准，返回信息码的KEY */
    public static final String RESULT_KEY = "errCode";
    /** OrangeLife协议标准，返回信息的KEY */
    public static final String RESULT_MESSAGE_KEY = "errInfo";
    /** 响应结果，正确 */
    public static final int RESULT_SUCCESS = 0;
    /** 对应JSONObject */
    public static final String RESULT_KEY_ENTITY = "entity";
    /** 列表 */
    public static final String RESULT_KEY_PAGE_ENTITY = "pageEntity";

    /** accessToken过期 */
    public static final int WHAT_TOKEN_EXPIRED = 1011;
    /** 登录已过期 */
    public static final String RESULT_LOGIN_OUT_OF_DATE = "55";
    /** 协议标准，返回成功 */
    public static final String RESULT_OK = "OK";

    /** 业务状态码 */
    public static final String ERRCODE_BIZ_HEAD = "98";
    /** 系统内部错误 */
    public static final String ERRCODE_SYSTEM_ERROR = "99";
    /** △△△不能为空 */
    public static final String ERRCODE_IS_EMPTY = "1001";
    /** △△△长度错误。({0}) */
    public static final String ERRCODE_WRONG_LENGTH = "1002";
    /** △△△格式错误。({0}) */
    public static final String ERRCODE_WRONG_FORMAT = "1003";
    /** 区分错误。({0}) */
    public static final String ERRCODE_WRONG_CASE = "1004";
    /** 登录过期 */
    public static final String ERRCODE_LOGIN_EXPIRE = "1011";
}
