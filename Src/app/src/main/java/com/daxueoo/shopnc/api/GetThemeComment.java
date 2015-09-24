package com.daxueoo.shopnc.api;

import com.daxueoo.shopnc.bean.CommentList;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by user on 15-9-11.
 */
public interface GetThemeComment {
    @GET("/index.php?act=circle_theme&op=theme_detail")
    public void GetThemeComment(@Query("c_id") String c_id, @Query("t_id") String t_id,@Query("key") String key,@Query("only_id") String only_id, Callback<CommentList> commentListCallback);
}
