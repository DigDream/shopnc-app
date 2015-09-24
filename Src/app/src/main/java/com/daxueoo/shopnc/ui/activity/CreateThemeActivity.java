package com.daxueoo.shopnc.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.network.adapter.NormalPostRequest;
import com.daxueoo.shopnc.scan.Intents;
import com.daxueoo.shopnc.sdk.Shopnc;
import com.daxueoo.shopnc.ui.activity.BaseActivity;
import com.daxueoo.shopnc.utils.ConstUtils;
import com.daxueoo.shopnc.utils.SharedPreferencesUtils;
import com.daxueoo.shopnc.utils.TitleBuilder;
import com.daxueoo.shopnc.utils.ToastUtils;
import com.melnykov.fab.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 15-8-25.
 */
public class CreateThemeActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "CreateThemeActivity";
    private EditText et_title;
    private EditText et_content;

    private String circleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_theme);

        new TitleBuilder(this).setTitleText("发表主题").setLeftImage(R.drawable.navigationbar_back_sel).setLeftOnClickListener(this);

        circleId = getIntent().getStringExtra("circleId");
        initView();
        initFab();
        initData();
    }

    private void initView() {
        et_title = (EditText) findViewById(R.id.et_title);
        et_content = (EditText) findViewById(R.id.et_content);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.titlebar_iv_left:
                this.finish();
                break;

        }
    }

    private void initData() {

    }

    private void initFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.normal_plus);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, et_title.getText().toString());
                Log.e(TAG, et_content.getText().toString());
                //  发送数据
                createTheme(CreateThemeActivity.this, circleId, et_title.getText().toString(), et_content.getText().toString(), (String) SharedPreferencesUtils.getParam(CreateThemeActivity.this, "key", "test"));
            }
        });
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
    public void createTheme(final Context context, String circleId, String title, String themecontent, String key) {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        Map<String, String> map = new HashMap<String, String>();
        map.put("c_id", circleId);
        map.put("name", title);
        map.put("themecontent", themecontent);
        map.put("key", key);
        map.put("readperm", "0");
        map.put("thtype", "0");
        final Request<JSONObject> request = new NormalPostRequest(ConstUtils.CREATE_THEME, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String code = response.getString("code");
                    if (code.equals("403")){
                        JSONObject error = response.getJSONObject("datas");
                        String errorMessage = error.getString("error");
                        ToastUtils.showToast(context, "发表失败,原因是" + errorMessage, Toast.LENGTH_SHORT);
                    }else {
                        ToastUtils.showToast(context, "发表成功", Toast.LENGTH_SHORT);
                        CreateThemeActivity.this.finish();
                    }
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
