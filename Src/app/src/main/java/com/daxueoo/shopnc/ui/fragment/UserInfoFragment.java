package com.daxueoo.shopnc.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.cache.ImageCacheManger;
import com.daxueoo.shopnc.model.TopicMessage;
import com.daxueoo.shopnc.model.User;
import com.daxueoo.shopnc.ui.activity.UserFollowActivity;
import com.daxueoo.shopnc.ui.activity.UserInfoActivity;
import com.daxueoo.shopnc.utils.ConstUtils;
import com.daxueoo.shopnc.utils.SharedPreferencesUtils;
import com.daxueoo.shopnc.utils.SystemUtils;
import com.daxueoo.shopnc.utils.ToastUtils;
import com.daxueoo.shopnc.widgets.RoundedImageView;
import com.daxueoo.shopnc.widgets.pulltozoom.PullToZoomScrollViewEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mehdi.sakout.dynamicbox.DynamicBox;

public class UserInfoFragment extends Fragment implements View.OnClickListener{

    private View view;
    private PullToZoomScrollViewEx scrollView;
    private TextView mUsername;
    private TextView tv_followers_count;
    private TextView tv_point_count;
    private TextView tv_following_count;
    private RoundedImageView iv_user_head;
    private DynamicBox box;

    LinearLayout ll_user_followers,ll_user_following;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_info, container, false);

        LinearLayout ll_user_info = (LinearLayout) view.findViewById(R.id.ll_user_info);

        scrollView = (PullToZoomScrollViewEx) view.findViewById(R.id.scroll_view);

        box = new DynamicBox(this.getActivity(), ll_user_info);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initLoading();
        initPulltozoom();
    }

    private void initPulltozoom() {
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadViewForCode();

        int mScreenHeight = SystemUtils.getScreen(getActivity()).heightPixels;
        int mScreenWidth = SystemUtils.getScreen(getActivity()).widthPixels;
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 16.0F)));
        scrollView.setHeaderLayoutParams(localObject);
    }

    private void loadViewForCode() {
        View headView = LayoutInflater.from(this.getActivity()).inflate(R.layout.profile_head_view, null, false);
        View zoomView = LayoutInflater.from(this.getActivity()).inflate(R.layout.profile_zoom_view, null, false);
        View contentView = LayoutInflater.from(this.getActivity()).inflate(R.layout.profile_content_view, null, false);
        scrollView.setScrollContentView(contentView);
        scrollView.setHeaderView(headView);
        scrollView.setZoomView(zoomView);

        ll_user_followers = (LinearLayout)headView.findViewById(R.id.ll_user_followers);
        ll_user_following = (LinearLayout) headView.findViewById(R.id.ll_user_following);
        ll_user_following.setOnClickListener(this);
        ll_user_followers.setOnClickListener(this);

        //  个人信息
        //  用户名
        mUsername = (TextView) headView.findViewById(R.id.tv_user_name);
        mUsername.setText((String) SharedPreferencesUtils.getParam(this.getActivity(), "username", "未登录"));
        //  积分数
        tv_point_count = (TextView) headView.findViewById(R.id.tv_point_count);
        tv_point_count.setText((String) SharedPreferencesUtils.getParam(this.getActivity(), "points", "0"));
        //  粉丝数
        tv_followers_count = (TextView) headView.findViewById(R.id.tv_followers_count);
        tv_followers_count.setText((String) SharedPreferencesUtils.getParam(this.getActivity(), "follower_count", "0"));
        //  关注数
        tv_following_count = (TextView) headView.findViewById(R.id.tv_following_count);
        tv_following_count.setText((String) SharedPreferencesUtils.getParam(this.getActivity(), "following_count", "0"));
        //  头像，从sd卡获取
        iv_user_head = (RoundedImageView) headView.findViewById(R.id.iv_user_head);
        ImageCacheManger.loadImage((String) SharedPreferencesUtils.getParam(this.getActivity(), "icon_url", "0"), iv_user_head, getBitmapFromResources(this.getActivity(), R.mipmap.ic_no_user), getBitmapFromResources(this.getActivity(), R.mipmap.ic_no_user));
        //  异步获取数据
        //thread.start();
        handler.sendEmptyMessage(0);
    }

    /**
     * 初始化加载中
     */
    private void initLoading() {
        box.showLoadingLayout();
        box.setLoadingMessage("Loading the UserInfo ...");
    }

    /**
     * 多线程获取数据
     */
    private Thread thread = new Thread() {
        @Override
        public void run() {
            //  判断网络连接情况获取数据
//            if (isNetConnected()) {
            handler.sendEmptyMessage(0);
//            } else {
//                Toast.makeText(UserInfoFragment.this.getActivity(), R.string.network_fail, Toast.LENGTH_SHORT).show();
//            }
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
                    initOriginData(((UserInfoActivity) UserInfoFragment.this.getActivity()).getUserId());
                    break;
                case 1:
                    box.hideAll();
                    break;
                case 2:
                    //  获取最新数据
                    break;
                case 3:
//                    box.hideAll();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 获取远程数据
     */
    private void initOriginData(String uid) {
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        JsonObjectRequest objRequest = new JsonObjectRequest(ConstUtils.USER_PROFILES_API + "&uid=" + uid, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject obj) {

                try {
                    JSONObject jsonObject = obj.getJSONObject("datas");
                    //  缓存获取到的结果
                    JSONObject userInfo = jsonObject.getJSONObject("user_info");

                    mUsername.setText(userInfo.getString("member_name"));
                    tv_point_count.setText(userInfo.getString("member_points"));
                    tv_followers_count.setText(userInfo.getString("follower_count"));
                    tv_following_count.setText(userInfo.getString("following_count"));

                    ImageCacheManger.loadImage(userInfo.getString("member_avatar"), iv_user_head, getBitmapFromResources(UserInfoFragment.this.getActivity(), R.mipmap.ic_no_user), getBitmapFromResources(UserInfoFragment.this.getActivity(), R.mipmap.ic_no_user));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);
                //  Toast.makeText(HomeFragment.this.getActivity(), "获取数据成功。", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserInfoFragment.this.getActivity(), "获取数据失败，请重新获取。", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(objRequest);
        requestQueue.start();
    }

    /**
     * 从资源中取出Bitmap
     *
     * @param act
     * @param resId
     * @return
     */
    public static Bitmap getBitmapFromResources(Activity act, int resId) {
        Resources res = act.getResources();
        return BitmapFactory.decodeResource(res, resId);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.ll_user_followers:
                intent.setClass(UserInfoFragment.this.getActivity(), UserFollowActivity.class);
                intent.putExtra("Type", "followers");
                intent.putExtra("uid", ((UserInfoActivity) UserInfoFragment.this.getActivity()).getUserId());
                startActivity(intent);
                break;
            case R.id.ll_user_following:
                intent.setClass(UserInfoFragment.this.getActivity(), UserFollowActivity.class);
                intent.putExtra("Type", "followings");
                intent.putExtra("uid", ((UserInfoActivity) UserInfoFragment.this.getActivity()).getUserId());
                startActivity(intent);
                break;
        }
    }
}