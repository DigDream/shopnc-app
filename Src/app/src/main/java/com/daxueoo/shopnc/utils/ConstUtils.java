package com.daxueoo.shopnc.utils;

/**
 * 常量类
 * Created by user on 15-8-2.
 */
public class ConstUtils {

    //  客户端类型
    public static final String CLIENT_TYPE = "android";

    //  商城类型
    public static final String WEB_TYPE_MALL = "mall";

    //  圈子类型
    public static final String WEB_TYPE_CIRCLE = "circle";

    public static final String BASE_URL = "http://keyango.com/api";

    //  首页地址
    public static final String URL_WAP_SHOPNC = "http://keyango.com/wap";

    public static final String NOTICE_URL = URL_WAP_SHOPNC +"/tmpl/article_show.html?article_id=";

    public static final String URL_WAP_THEME = "";

    public static final String URL_API_SHOPNC = "http://keyango.com/api";

    //  用户登录地址
    public static final String LOGIN_URL = URL_API_SHOPNC + "/index.php?act=login";

    //  用户注册地址
    public static final String REGISTER_URL = URL_WAP_SHOPNC + "/index.php?act=login&op=register";

    //  圈子api地址
    public static final String CIRCLE_URL = URL_API_SHOPNC + "/index.php?act=circle&op=";

    //  圈子详情
    public static final String CIRCLE_PROFILE = CIRCLE_URL + "circleInfo";

    //  圈子分类
    public static final String CIRCLE_CLASS = URL_API_SHOPNC + "/index.php?act=circle&op=class";

    //  所有圈子
    public static final String ALL_CIRCLE = URL_API_SHOPNC + "/index.php?act=circle&op=get_reply_themelist";

    //  圈子热门话题
    public static final String HOT_THEME = CIRCLE_URL + "get_reply_themelist";

    //  圈子推荐话题
    public static final String RECOMMAND_THEME = CIRCLE_URL + "get_theme_list";

    //  问答列表
    public static final String QA_LIST = URL_API_SHOPNC + "/index.php?act=question&op=allQuestions";

    //  创建问题
    public static final String CREATE_QA = URL_API_SHOPNC + "/index.php?act=question_answer&op=createQuestion";

    //  我的圈子
    public static final String MY_CIRCLE = URL_API_SHOPNC + "/index.php?act=user_center&op=" + "user_circles";

    //  获取用户信息api
    public static final String USER_PROFILES_API = URL_API_SHOPNC + "/index.php?act=user_center&op=user_info" ;

    //  获取粉丝
    public static final String USER_FOLLERS = URL_API_SHOPNC + "/index.php?act=user_center&op=followers";

    //  获取关注
    public static final String USER_FOLLERING = URL_API_SHOPNC + "/index.php?act=user_center&op=following";

    //  所有圈子
    public static final String CIRCLE_SEARCH = CIRCLE_URL + "group";

    //  圈子话题列表
    public static final String CIRCLE_THEMES = CIRCLE_URL + "circleThemes";

    //  圈子置顶贴
    public static final String CIRCLE_STICK_THEMES = CIRCLE_URL +"circleStickThemes";

    //  话题详情
    public static final String THEME_DETAIL = URL_API_SHOPNC + "/index.php?act=circle_theme&op=ajax_themeinfo";

    //  建立话题
    public static final String CREATE_THEME = URL_API_SHOPNC + "/index.php?act=circle_op&op=create_theme";

    //  首页动态
    public static final String HOME_TRENDS = URL_API_SHOPNC + "/index.php?act=trend&op=trends";

    //  所有交易列表
    public  static final String TRADE_ALL = URL_API_SHOPNC + "/index.php?act=trade&op=all_trade_list";

    //  交易分类
    public static final String TRADE_CATEGORY = URL_API_SHOPNC + "/index.php?act=trade_class";

    //  获取商品列表
    public static final String GOODS_LIST = URL_API_SHOPNC + "/index.php?act=goods&op=goods_list";

    //  webpage
    //  商品详情页面
    public static final String GOODS_DETAIL_WEB = URL_WAP_SHOPNC + "/tmpl/product_detail.html?goods_id=";

}