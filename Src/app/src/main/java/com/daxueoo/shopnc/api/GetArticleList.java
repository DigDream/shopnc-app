package com.daxueoo.shopnc.api;

import com.daxueoo.shopnc.bean.ArticleListData;
import com.daxueoo.shopnc.bean.CircleThemeList;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by guodont on 15/9/19.
 */

public interface GetArticleList {
    @GET("/index.php?act=article&op=article_list")
    public void GetHomeNotice(@Query("ac_id")String classId, Callback<ArticleListData> responseCallback);
}
