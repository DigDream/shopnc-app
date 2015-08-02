package com.daxueoo.shopnc.sdk;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.network.adapter.NormalPostRequest;
import com.daxueoo.shopnc.ui.activity.LoginActivity;
import com.daxueoo.shopnc.utils.ConstUtils;
import com.daxueoo.shopnc.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 15-8-2.
 */
public class Shopnc {

    public static final String TAG = "WorktileSdk";

    public static void isLogin(final Context context) {
        if (SharedPreferencesUtils.getParam(context, "key", "false").equals("false")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);  //先得到构造器
            builder.setTitle("亲，您还未登录呢"); //设置标题
            builder.setMessage("是否立即登录?"); //设置内容
            builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
            builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() { //设置确定按钮
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent intent = new Intent();
                    intent.setClass(context, LoginActivity.class);
                    context.startActivity(intent);
                }
            });
            builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() { //设置取消按钮
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            //参数都设置完成，创建并显示出来
            builder.create().show();
        }
    }

    public static void login(final Context context, String username, String password, String type) {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", username);
        map.put("password", password);
        map.put("client", type);
        final Request<JSONObject> request = new NormalPostRequest(ConstUtils.login_url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("datas");
                    String key = data.getString("key");
                    String username = data.getString("username");

                    SharedPreferencesUtils.setParam(context, "key", key);
                    SharedPreferencesUtils.setParam(context, "username", username);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.w(TAG, "response -> " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
            }
        }, map);
        requestQueue.add(request);
    }

    public static void register(final Context context, String username, String password, String email, String type) {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", username);
        map.put("password", password);
        map.put("password_confirm", password);
        map.put("email", email);
        map.put("client", type);
        final Request<JSONObject> request = new NormalPostRequest(ConstUtils.register_url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("datas");
                    String key = data.getString("key");
                    String username = data.getString("username");

                    SharedPreferencesUtils.setParam(context, "key", key);
                    SharedPreferencesUtils.setParam(context, "username", username);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.w(TAG, "response -> " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
            }
        }, map);
        requestQueue.add(request);
    }
}
