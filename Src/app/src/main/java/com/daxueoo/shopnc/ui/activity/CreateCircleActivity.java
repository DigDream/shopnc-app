package com.daxueoo.shopnc.ui.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.network.adapter.NormalPostRequest;
import com.daxueoo.shopnc.utils.ConstUtils;
import com.daxueoo.shopnc.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateCircleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_circle);
    }


    /**
     * 创建话题POST表单
     *
     * @param context
     * @param circleId
     * @param title
     * @param themecontent
     * @param key
     */
//    public void createTheme(final Context context, String circleId, String title, String themecontent, String type, String key) {
//        final RequestQueue requestQueue = Volley.newRequestQueue(context);
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("c_id", circleId);
//        map.put("type", type);
//        map.put("name", title);
//        map.put("content", themecontent);
//        map.put("key", key);
//        map.put("readperm", "0");
//        final Request<JSONObject> request = new NormalPostRequest(ConstUtils.CREATE_QA, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    String code = response.getString("code");
//                    if (code.equals("403")) {
//                        JSONObject error = response.getJSONObject("datas");
//                        String errorMessage = error.getString("error");
//                        ToastUtils.showToast(context, "发表失败,原因是" + errorMessage, Toast.LENGTH_SHORT);
//                    } else {
//                        ToastUtils.showToast(context, "发表成功", Toast.LENGTH_SHORT);
//                        CreateQaActivity.this.finish();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Log.w(TAG, "response -> " + response.toString());
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, error.getMessage(), error);
//            }
//        }, map);
//        requestQueue.add(request);
//    }

}
