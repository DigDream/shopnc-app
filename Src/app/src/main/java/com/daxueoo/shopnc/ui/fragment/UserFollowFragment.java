package com.daxueoo.shopnc.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.adapter.ThemeCommentAdapter;
import com.daxueoo.shopnc.adapter.UserAdapter;
import com.daxueoo.shopnc.cache.ImageCacheManger;
import com.daxueoo.shopnc.model.Comment;
import com.daxueoo.shopnc.model.User;
import com.daxueoo.shopnc.model.UserMessage;
import com.daxueoo.shopnc.ui.activity.DetailActivity;
import com.daxueoo.shopnc.ui.activity.UserFollowActivity;
import com.daxueoo.shopnc.utils.ACache;
import com.daxueoo.shopnc.utils.ConstUtils;
import com.daxueoo.shopnc.utils.DateUtils;
import com.daxueoo.shopnc.utils.SharedPreferencesUtils;
import com.daxueoo.shopnc.utils.StringUtils;
import com.daxueoo.shopnc.utils.SystemUtils;
import com.daxueoo.shopnc.utils.TitleBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import mehdi.sakout.dynamicbox.DynamicBox;

/**
 * Created by user on 15-9-1.
 */
public class UserFollowFragment extends Fragment {
    private DynamicBox box;
    PtrClassicFrameLayout mPtrFrame;
    ListView listView;
    String type;

    private ACache aCache;

    private List<UserMessage> userMessageList = new ArrayList<UserMessage>();
    private UserAdapter adapter;
    private String url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_follow, container, false);

        RelativeLayout rl_content = (RelativeLayout) view.findViewById(R.id.rl_content);
        box = new DynamicBox(this.getActivity(), rl_content);
        //  下拉刷新
        mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_web_view_frame);
        //  ListView
        listView = (ListView) view.findViewById(R.id.list_fragment_user);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        aCache = ACache.get(this.getActivity());
        initLoading();
        initPtr();
        initListView();
        //  读取缓存
        type = ((UserFollowActivity) this.getActivity()).getType();

        if (aCache.getAsJSONArray(type) != null) {
            //
            initData();
        } else {
            handler.sendEmptyMessage(0);
        }
    }

    private void initLoading() {
        box.showLoadingLayout();
        box.setLoadingMessage("Loading your theme ...");
    }

    /**
     * handler初始化网络数据
     */
    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    initOriginData(((UserFollowActivity)UserFollowFragment.this.getActivity()).getUid(), type);
                    //  获取网络数据
                    //if (aCache.getAsJSONArray("themeList") == null) {
                    //}
                    break;
                case 1:
                    adapter.notifyDataSetChanged(); // 发送消息通知ListView更新
                    box.hideAll();
                    break;
                //  下拉刷新
                case 2:
                    //  获取最新数据
                    break;
                default:
                    break;
            }
        }
    };

    private void initOriginData(String uid, final String type) {
        if (type.equals("followers")) {
            url = ConstUtils.USER_FOLLERS;
        } else {
            url = ConstUtils.USER_FOLLERING;
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        Log.e("taf",url + "&uid=" + uid);
        JsonObjectRequest objRequest = new JsonObjectRequest(url + "&uid=" + uid, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject obj) {
                try {
                    JSONObject jsonObject = obj.getJSONObject("datas");
                    JSONArray jsonArray = jsonObject.getJSONArray(type);
                    for (int i = 0; i< jsonArray.length() ;i++){
                        JSONObject user = jsonArray.getJSONObject(i);
                        UserMessage userMessage = new UserMessage();
                        userMessage.setName(user.getString("member_name"));
                        userMessage.setId(user.getString("member_id"));
                        userMessage.setAvatar(user.getString("member_avatar"));
                        userMessageList.add(userMessage);
                    }
                    //  缓存获取到的结果
//                    aCache.put("themeList" + circleId, circleThemeList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);
                //  Toast.makeText(HomeFragment.this.getActivity(), "获取数据成功。", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserFollowFragment.this.getActivity(), "获取数据失败，请重新获取。", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(objRequest);
        requestQueue.start();

    }

    /**
     * 下拉刷新
     */
    private void initPtr() {
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, listView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.refreshComplete();
                        //  异步刷新数据
//                        loadComments(1);
                    }
                }, 100);
            }
        });
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                //  mPtrFrame.autoRefresh();
                Toast.makeText(UserFollowFragment.this.getActivity(), "Str start", Toast.LENGTH_SHORT).show();
            }
        }, 100);
    }

    private void initListView() {
        adapter = new UserAdapter(this.getActivity(), userMessageList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("fdssff", view.getTag().toString());
            }
        });
    }

    private void initData() {


    }
}
