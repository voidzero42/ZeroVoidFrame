package com.zerovoid.http;

/**
 * Created by Administrator on 2015/11/11.
 */
public class HttpConfig {
    /** Intent传递Bean的KEY */
    public static final String KEY_INTENT_BEAN = "bean";

    /** 响应结果，正确 */
    public static final int RESULT_SUCCESS = 0;

    /** 登录已过期 */
    public static final String RESULT_LOGIN_OUT_OF_DATE = "55";

    /** 授权令牌的KEY */
    public static final String RESULT_KEY_ACCESS_TOKEN = "authToken";

    public static final String RESULT_KEY = "errCode";

    public static final String RESULT_MESSAGE_KEY = "errInfo";

    public static final String RESULT_CONTENT = "entity";

    public static final String RESULT_KEY_ENTITY = "entity";

    public static final String RESULT_KEY_PAGE_ENTITY = "pageEntity";

    public static final String RESULT_KEY_QINIU_TOKEN = "uptoken";

    /** 网络错误 */
    public static final String TEXT_ERROR_500 = "网络错误500";

    /** 网络错误500 */
    public static final int STATUS_NET_ERROR = 500;

    /** 500的提示 */
    public static final String JSON500 = "网络连接不稳定";

    /** 服务器返回的错误信息 */
    public static final int WHAT_ERROR_HTTP = 500;

    /** accessToken过期 */
    public static final int WHAT_TOKEN_EXPIRED = 1011;

    /** 忘记密码 */
    public static final int WHAT_FORGET = 100;

    /** 请求短信验证码 */
    public static final int WHAT_VALIDATION_CODE = 101;

    /** 倒计时 */
    public static final int WHAT_COUNT_DOWN = 102;

    /** 登录 */
    public static final int WHAT_LOGIN = 103;

    /** 评论列表 */
    public static final int WHAT_COMMENT_LIST = 104;
}
