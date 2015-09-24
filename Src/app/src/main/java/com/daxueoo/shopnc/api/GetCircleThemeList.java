package com.daxueoo.shopnc.api;

import com.daxueoo.shopnc.bean.CircleThemeList;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by user on 15-9-12.
 */
public interface GetCircleThemeList {
    @GET("")
    public void GetThemeList();

    @GET("/index.php?act=circle&op=circleThemes")
    public void GetThemeListPage(@Query("curpage")String curpage, @Query("page")String page, @Query("circle_id")String circleId, Callback<CircleThemeList> responseCallback);

    @GET("/index.php?act=circle&op=get_reply_themelist")
    public void GetHomeThemeListPage(@Query("curpage")String curpage, @Query("page")String page, Callback<CircleThemeList> responseCallback);


}
