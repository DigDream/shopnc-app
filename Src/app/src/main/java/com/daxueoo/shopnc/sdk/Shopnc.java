package com.daxueoo.shopnc.sdk;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.daxueoo.shopnc.network.adapter.NormalPostRequest;
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
