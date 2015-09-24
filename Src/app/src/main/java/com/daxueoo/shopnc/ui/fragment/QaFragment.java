package com.daxueoo.shopnc.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.adapter.QaAdapter;
import com.daxueoo.shopnc.adapter.ThemeListAdapter;
import com.daxueoo.shopnc.model.TopicMessage;
import com.daxueoo.shopnc.ui.activity.CreateQaActivity;
import com.daxueoo.shopnc.ui.activity.CreateThemeActivity;
import com.daxueoo.shopnc.ui.activity.DetailActivity;
import com.daxueoo.shopnc.ui.activity.QaActivity;
import com.daxueoo.shopnc.ui.activity.TaskDetailActivity;
import com.daxueoo.shopnc.ui.activity.ThemeListActivity;
import com.daxueoo.shopnc.utils.ACache;
import com.daxueoo.shopnc.utils.ConstUtils;
import com.daxueoo.shopnc.utils.ImageUtils;
import com.daxueoo.shopnc.utils.TitleBuilder;
import com.melnykov.fab.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import mehdi.sakout.dynamicbox.DynamicBox;

/**
 * Created by user on 15-8-20.
 */
public class QaFragment extends Fragment implements View.OnClickListener {

    private String TAG = "CircleDetailFragment";
    private PtrClassicFrameLayout mPtrFrame;
    private ListView listView;

    private List<TopicMessage> data = new ArrayList<TopicMessage>();
    private QaAdapter topicAdapter;

    private FloatingActionButton fab;

    private ACache aCache;
    private DynamicBox box;

    private LoadMoreListViewContainer loadMoreListViewContainer;

    private TextView tv_title;

    private TitleBuilder titleBuilder;

    private int circleId;

    private String qaType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_circle_detail, container, false);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_circle_detail);
        box = new DynamicBox(this.getActivity(), linearLayout);
        //  下拉刷新
        mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_web_view_frame);
        //  ListView
        listView = (ListView) view.findViewById(R.id.list_fragment_topic);

        loadMoreListViewContainer = (LoadMoreListViewContainer) view.findViewById(R.id.load_more_list_view_container);
        loadMoreListViewContainer.useDefaultHeader();

        fab = (FloatingActionButton) view.findViewById(R.id.normal_plus);

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        aCache = ACache.get(this.getActivity());

        circleId = Integer.parseInt(((QaActivity) QaFragment.this.getActivity()).getCircleId());

        qaType = ((QaActivity) QaFragment.this.getActivity()).getQaType();

        initView();
        initListView();
        initPtr();
        initLoading();
        initLoadMore();
        if (aCache.getAsJSONArray("qaList" + ((QaActivity) QaFragment.this.getActivity()).getCircleId()) == null) {
            handler.sendEmptyMessage(0);
        } else {
            //initData();
        }
        initFab();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult");
        mPtrFrame.autoRefresh();
    }

    private void initFab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(QaFragment.this.getActivity(), CreateQaActivity.class);
                intent.putExtra("circleId", circleId);
                intent.putExtra("QaType", qaType);
                startActivityForResult(intent, 2);
            }
        });
    }

    /**
     * 初始化加载更多
     */
    private void initLoadMore() {
//        //TODO 加载更多
        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                Log.e(TAG, "loadmore");
            }
        });
        loadMoreListViewContainer.setAutoLoadMore(true);
    }

    private void initData() {
        try {
            JSONArray circleThemeList = aCache.getAsJSONArray("qaList" + ((QaActivity) QaFragment.this.getActivity()).getCircleId());
            for (int i = 0; i < circleThemeList.length(); i++) {
                JSONObject theme = circleThemeList.getJSONObject(i);
                TopicMessage msg = new TopicMessage(theme.getString("theme_name"), theme.getString("theme_content"), theme.getString("member_name"), theme.getString("theme_browsecount"),  theme.getString("theme_addtime"),theme.getString("theme_commentcount"), theme.getString("theme_likecount"));
                String themeImg = ImageUtils.getContentFirstImgUrl(theme.getString("theme_content"));
                if (themeImg != null) {
                    msg.setIcon_url(themeImg);
                    msg.setTopic_content(ImageUtils.HideImageTag(theme.getString("theme_content")));
                } else {
                    msg.setIcon_url("");
                }
                msg.setTopic_id(theme.getString("theme_id"));
                msg.setTopic_user_icon(theme.getString("member_avatar"));
                msg.setTopic_user_id(theme.getString("member_id"));
                data.add(msg);
            }
            Log.e(TAG, data.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        handler.sendEmptyMessage(1);
    }

    private void initLoading() {
        box.showLoadingLayout();
        box.setLoadingMessage("Loading your questions ...");
    }

    /**
     * 初始化ListView
     */
    private void initListView() {
        topicAdapter = new QaAdapter(this.getActivity(), data);
        listView.setAdapter(topicAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(QaFragment.this.getActivity(), DetailActivity.class);
                intent.putExtra("BUNDLE_KEY_DISPLAY_TYPE", 0);
                intent.putExtra("detailId", data.get(position).getTopic_id());
                startActivityForResult(intent, 2);
            }
        });
    }

    /**
     * 初始化视图
     */
    private void initView() {
        titleBuilder = new TitleBuilder(QaFragment.this.getActivity());

        if (circleId > 0) {
            titleBuilder.setTitleText("本圈问答");
        } else {
            switch (qaType) {
                case "6" :
                    titleBuilder.setTitleText("问达人");
                    break;
                case "5":
                    titleBuilder.setTitleText("问专家");
                    break;
            }
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
                    initOriginData(((QaActivity) QaFragment.this.getActivity()).getCircleId(), ((QaActivity) QaFragment.this.getActivity()).getQaType());
                    break;
                case 1:
                    Log.e(TAG, "test");
                    topicAdapter.notifyDataSetChanged(); // 发送消息通知ListView更新
                    box.hideAll();
                    Toast.makeText(QaFragment.this.getActivity(), "没有更多新数据了。。", Toast.LENGTH_LONG).show();
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
                        handler.sendEmptyMessage(0);
                    }
                }, 100);
            }
        });
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                //  mPtrFrame.autoRefresh();
                Toast.makeText(QaFragment.this.getActivity(), "Str start", Toast.LENGTH_SHORT).show();
            }
        }, 100);
    }

    /**
     * 获取远程数据
     */
    private void initOriginData(final String circleId,String type) {
        Log.e(TAG, ConstUtils.CIRCLE_THEMES + "&c_id=" + circleId);
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        JsonObjectRequest objRequest = new JsonObjectRequest(ConstUtils.QA_LIST + "&type=" + type + "&c_id=" + circleId, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject obj) {
                Log.e(TAG, obj.toString());

                data.clear();
                try {
                    JSONObject jsonObject = obj.getJSONObject("datas");
                    JSONArray circleThemeList = jsonObject.getJSONArray("questions");
                    //  缓存获取到的结果
                    aCache.put("themeList" + circleId, circleThemeList);
                    for (int i = 0; i < circleThemeList.length(); i++) {
                        JSONObject theme = circleThemeList.getJSONObject(i);
                        TopicMessage msg = new TopicMessage(theme.getString("theme_name"), theme.getString("theme_content"), theme.getString("member_name"), theme.getString("theme_browsecount"),  theme.getString("theme_addtime"),theme.getString("theme_commentcount"), theme.getString("theme_likecount"));
                        String themeImg = ImageUtils.getContentFirstImgUrl(theme.getString("theme_content"));
                        if (themeImg != null) {
                            msg.setIcon_url(themeImg);
                            msg.setTopic_content(ImageUtils.HideImageTag(theme.getString("theme_content")));
                        } else {
                            msg.setIcon_url("");
                        }
                        msg.setTopic_id(theme.getString("theme_id"));
                        msg.setTopic_user_icon(theme.getString("member_avatar"));
                        msg.setTopic_user_id(theme.getString("member_id"));
                        data.add(msg);
                    }
                    Log.e(TAG, data.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);
                //  Toast.makeText(HomeFragment.this.getActivity(), "获取数据成功。", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(QaFragment.this.getActivity(), "获取数据失败，请重新获取。", Toast.LENGTH_LONG).show();
                Log.e(TAG, error.toString());
            }
        });

        requestQueue.add(objRequest);
        requestQueue.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}