package com.daxueoo.shopnc.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.daxueoo.shopnc.ui.activity.QaActivity;
import com.daxueoo.shopnc.ui.activity.SettingActivity;
import com.daxueoo.shopnc.ui.activity.UserFollowActivity;
import com.daxueoo.shopnc.utils.ConstUtils;
import com.daxueoo.shopnc.utils.SharedPreferencesUtils;
import com.daxueoo.shopnc.utils.SystemUtils;
import com.daxueoo.shopnc.utils.TitleBuilder;
import com.daxueoo.shopnc.utils.ToastUtils;
import com.daxueoo.shopnc.widgets.RoundedImageView;
import com.daxueoo.shopnc.widgets.pulltozoom.PullToZoomScrollViewEx;

import org.json.JSONException;
import org.json.JSONObject;

public class UserFragment extends BaseFragment implements View.OnClickListener{

    private View view;
    private PullToZoomScrollViewEx scrollView;
    private TextView mUsername;

    LinearLayout ll_user_followers,ll_user_following;
    private boolean isFirst = true;
    private TextView tv_following_count;
    private TextView tv_point_count;
    private TextView tv_followers_count;
    private RoundedImageView iv_user_head;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(activity, R.layout.fragment_user, null);

        new TitleBuilder(view).setTitleText("我的").setRightImage(R.drawable.icon_setting).setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(getActivity(), "right click~", Toast.LENGTH_SHORT);
                Intent intent = new Intent();
                intent.setClass(UserFragment.this.getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });

        scrollView = (PullToZoomScrollViewEx) view.findViewById(R.id.scroll_view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initPulltozoom();
        //  更新信息
        if (isFirst){
            handler.sendEmptyMessage(0);
            isFirst = false;
        }
    }

    /**
     * handler初始化网络数据
     */
    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //  获取网络数据
                    initOriginData(UserFragment.this.getActivity(),(String)SharedPreferencesUtils.getParam(UserFragment.this.getActivity(), "uid", "0"));
                    break;
                default:
                    break;
            }
        }
    };

    private void initOriginData(final Context context,String uid) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest objRequest = new JsonObjectRequest(ConstUtils.USER_PROFILES_API + "&uid=" + uid, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject obj) {

                try {
                    JSONObject jsonObject = obj.getJSONObject("datas");
                    JSONObject userInfo = jsonObject.getJSONObject("user_info");
//                    getUserHeadIcon(context, obj.getString("avatar"));
                    SharedPreferencesUtils.setParam(context, "points", userInfo.getString("member_points"));
                    SharedPreferencesUtils.setParam(context, "email", userInfo.getString("member_email"));
                    SharedPreferencesUtils.setParam(context, "icon_url", userInfo.getString("member_avatar"));
                    SharedPreferencesUtils.setParam(context, "follower_count", userInfo.getString("follower_count"));
                    SharedPreferencesUtils.setParam(context, "following_count", userInfo.getString("following_count"));

                    tv_point_count.setText(userInfo.getString("member_points"));
                    //  粉丝数
                    tv_followers_count.setText(userInfo.getString("follower_count"));
                    //  关注数
                    tv_following_count.setText(userInfo.getString("following_count"));
                    //  头像，从sd卡获取
                    ImageCacheManger.loadImage(userInfo.getString("member_avatar"), iv_user_head, getBitmapFromResources(UserFragment.this.getActivity(), R.mipmap.ic_no_user), getBitmapFromResources(UserFragment.this.getActivity(), R.mipmap.ic_no_user));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "获取用户信息失败" + error.getMessage(), Toast.LENGTH_LONG).show();
                error.getMessage();
            }

        });
        requestQueue.add(objRequest);
        requestQueue.start();
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

        Log.e("UserFragment", (String) SharedPreferencesUtils.getParam(this.getActivity(), "icon_url", "0"));
    }

    /**
     * 从资源中取出Bitmap
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
                intent.setClass(UserFragment.this.getActivity(), UserFollowActivity.class);
                intent.putExtra("Type", "followers");
                intent.putExtra("uid",(String) SharedPreferencesUtils.getParam(UserFragment.this.getActivity(), "uid", "0"));
                startActivity(intent);
                break;
            case R.id.ll_user_following:
                intent.setClass(UserFragment.this.getActivity(), UserFollowActivity.class);
                intent.putExtra("Type", "followings");
                intent.putExtra("uid",(String) SharedPreferencesUtils.getParam(UserFragment.this.getActivity(), "uid", "0"));
                startActivity(intent);
                break;
        }
    }
}