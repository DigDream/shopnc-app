package com.daxueoo.shopnc.api;

/**
 * Created by user on 15-9-3.
 */

import com.daxueoo.shopnc.bean.TaskList;
import com.squareup.okhttp.Call;


import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by user on 15-9-3.
 */
public interface GetTaskList {
    @GET("/index.php?act=task&op=tasks")
    public void GetTaskList(@Query("type") String username, @Query("date") String date, @Query("key") String key, @Query("page") String page, @Query("curpage") String curpage,Callback<TaskList> response);
}