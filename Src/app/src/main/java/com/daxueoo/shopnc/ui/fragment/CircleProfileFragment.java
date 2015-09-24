package com.daxueoo.shopnc.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.cache.ImageCacheManger;
import com.daxueoo.shopnc.ui.activity.CircleProfileActivity;
import com.daxueoo.shopnc.ui.activity.JoinCircleActivity;
import com.daxueoo.shopnc.ui.activity.UserInfoActivity;
import com.daxueoo.shopnc.utils.ConstUtils;
import com.daxueoo.shopnc.utils.TitleBuilder;
import com.daxueoo.shopnc.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import mehdi.sakout.dynamicbox.DynamicBox;

/**
 * A placeholder fragment containing a simple view.
 */
public class CircleProfileFragment extends Fragment implements View.OnClickListener{

    private TextView tv_circle_profile_name;
    private TextView tv_circle_profile_master;
    private TextView tv_circle_profile_desc;
    private RelativeLayout rl_circle_profile;
    private Button btn_circle_profile_op;
    private DynamicBox box;
    private String circleId;
    View view;
    private boolean isCircleMember;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_circle_profile, null);
//        rl_circle_profile = (RelativeLayout) view.findViewById(R.id.rl_circle_profile);
        tv_circle_profile_name = (TextView) view.findViewById(R.id.tv_circle_profile_name);
        tv_circle_profile_master = (TextView) view.findViewById(R.id.tv_circle_profile_master);
        tv_circle_profile_desc = (TextView) view.findViewById(R.id.tv_circle_profile_desc);
        btn_circle_profile_op = (Button) view.findViewById(R.id.btn_circle_profile_op);

//        box = new DynamicBox(this.getActivity(), rl_circle_profile);

//        circleId = "2";
        circleId = this.getActivity().getIntent().getStringExtra("circleId");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            //申请加入圈子
            case R.id.btn_circle_profile_op:
                ToastUtils.showToast(CircleProfileFragment.this.getActivity(),"加入圈子", Toast.LENGTH_SHORT);
                intent.setClass(CircleProfileFragment.this.getActivity(), JoinCircleActivity.class);
                startActivity(intent);
                break;
            //返回
            case R.id.titlebar_iv_left:
                CircleProfileFragment.this.getActivity().finish();
                break;
        }
    }

    private void initView() {
        new TitleBuilder(CircleProfileFragment.this.getActivity()).setTitleText("圈子简介").setLeftImage(R.drawable.navigationbar_back_sel);

    }


    private void initListener() {
        btn_circle_profile_op.setOnClickListener(this);
    }


    private void initData() {
        handler.sendEmptyMessage(0);
    }


    /**
     * 初始化加载中
     */
    private void initLoading() {
        box.showLoadingLayout();
    }

    /**
     * 多线程获取数据
     */
    private Thread thread = new Thread() {
        @Override
        public void run() {
            handler.sendEmptyMessage(0);
        }
    };

    /**
     * handler初始化网络数据
     */
    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //  获取网络数据
                    initOriginData(circleId);
                    break;
                case 1:
//                    box.hideAll();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 获取远程数据
     */
    private void initOriginData(String circleId) {
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        JsonObjectRequest objRequest = new JsonObjectRequest(ConstUtils.CIRCLE_PROFILE + "&circle_id=" + circleId, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject obj) {

                try {
                    JSONObject jsonObject = obj.getJSONObject("datas");
                    //  TODO 缓存获取到的结果
                    JSONObject circleInfo = jsonObject.getJSONObject("circle_info");
                    tv_circle_profile_name.setText(circleInfo.getString("circle_name"));
                    tv_circle_profile_master.setText(circleInfo.getString("circle_mastername"));
                    tv_circle_profile_desc.setText(circleInfo.getString("circle_desc"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CircleProfileFragment.this.getActivity(), "获取数据失败", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(objRequest);
        requestQueue.start();
    }

}
