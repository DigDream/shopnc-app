package com.daxueoo.shopnc.api;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by user on 15-9-11.
 */
public interface FinishTask {
    @FormUrlEncoded
    @POST("/index.php?act=task&op=finishTask")
    public void finishTask(@Field("key") String key, @Field("task_id") String task_id, Callback<JSONObject> jsonObjectCallback);

}
