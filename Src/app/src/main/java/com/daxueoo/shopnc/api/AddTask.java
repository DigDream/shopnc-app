package com.daxueoo.shopnc.api;

import com.daxueoo.shopnc.bean.TaskList;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by user on 15-9-9.
 */
public interface AddTask  {
    @FormUrlEncoded
    @POST("/index.php?act=task&op=createTask")
    public void AddTask(@Field("key") String key, @Field("task_title") String title, @Field("task_content") String content, @Field("task_tag") String tag, @Field("task_publish_time") String publish_time, Callback<JSONObject> response);
}
