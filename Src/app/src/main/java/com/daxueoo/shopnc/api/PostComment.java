package com.daxueoo.shopnc.api;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by user on 15-9-11.
 */
public interface PostComment {
    @FormUrlEncoded
    @POST("/index.php?act=circle_theme&op=create_reply")
    public void AddComment(@Field("key") String key, @Field("c_id") String c_id, @Field("t_id") String t_id, @Field("replycontent") String replycontent, @Field("answer_id") String answer_id, Callback<JSONObject> response);

}
