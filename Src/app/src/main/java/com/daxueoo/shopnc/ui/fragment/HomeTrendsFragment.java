package com.daxueoo.shopnc.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.daxueoo.shopnc.adapter.TrendAdapter;
import com.daxueoo.shopnc.model.TrendMessage;
import com.daxueoo.shopnc.sdk.Shopnc;
import com.daxueoo.shopnc.ui.activity.LoginActivity;
import com.daxueoo.shopnc.utils.ACache;
import com.daxueoo.shopnc.utils.ConstUtils;
import com.daxueoo.shopnc.utils.ImageUtils;
import com.daxueoo.shopnc.utils.SharedPreferencesUtils;

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
public class HomeTrendsFragment extends CommonDetailFragment implements View.OnClickListener{

    private PtrClassicFrameLayout mPtrFrame;
    private ListView listView;
    private List<TrendMessage> data = new ArrayList<TrendMessage>();
    private TrendAdapter trendAdapter;
    private ACache aCache;
    private DynamicBox box;
    private LoadMoreListViewContainer loadMoreListViewContainer;

    private TextView tv_hint;
    private LinearLayout ll_circle_hint;
    private Button btn_all_circle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home_trends, container, false);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_home_trends);
        box = new DynamicBox(this.getActivity(), linearLayout);
        //  下拉刷新
        mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_web_view_frame);
        //  ListView
        listView = (ListView) view.findViewById(R.id.list_fragment_trend);

        tv_hint = (TextView) view.findViewById(R.id.tv_hint);
        ll_circle_hint = (LinearLayout) view.findViewById(R.id.ll_circle_hint);
        btn_all_circle = (Button) view.findViewById(R.id.btn_all_circle);

        loadMoreListViewContainer = (LoadMoreListViewContainer) view.findViewById(R.id.load_more_list_view_container);
        loadMoreListViewContainer.useDefaultHeader();
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        aCache = ACache.get(this.getActivity());
        initView();

        //  如果登录，获取数据
        if (!Shopnc.isHaveKey(HomeTrendsFragment.this.getActivity())){
//            if (aCache.getAsJSONArray("trendsList" + (String)SharedPreferencesUtils.getParam(HomeTrendsFragment.this.getActivity(),"uid","1")) == null) {
//                handler.sendEmptyMessage(0);
//            } else {
//                initData();
//            }
            initListView();
            initPtr();
            initLoadMore();
            initLoading();
            mPtrFrame.setVisibility(View.VISIBLE);
            ll_circle_hint.setVisibility(View.GONE);
            handler.sendEmptyMessage(0);
        }else {
            initNoLoginView();
        }
    }

    private void initNoLoginView() {
        mPtrFrame.setVisibility(View.GONE);
        ll_circle_hint.setVisibility(View.VISIBLE);
        tv_hint.setText("请登录后查看");
        btn_all_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳到登录
                Intent intent = new Intent();
                intent.setClass(HomeTrendsFragment.this.getActivity(), LoginActivity.class);
                HomeTrendsFragment.this.getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPtrFrame.autoRefresh();
    }

    /**
     * 未登录
     */
    private void initNoLogin() {
        box.showExceptionLayout();
        box.setOtherExceptionMessage("请登录后查看");
    }

    /**
     * 多线程
     */
    private Thread thread = new Thread() {
        @Override
        public void run() {
            //  TODO 判断网络连接情况获取数据
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
                    //if (aCache.getAsJSONArray("themeList") == null) {
                    initOriginData((String) SharedPreferencesUtils.getParam(HomeTrendsFragment.this.getActivity(), "uid", "1"));
                    //}
                    break;
                case 1:
                    trendAdapter.notifyDataSetChanged(); // 发送消息通知ListView更新
                    box.hideAll();
                    Toast.makeText(HomeTrendsFragment.this.getActivity(), "没有更多新数据了。。", Toast.LENGTH_LONG).show();
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


    private void initData() {
        try {
            JSONArray circleThemeList = aCache.getAsJSONArray("trendList" + (String) SharedPreferencesUtils.getParam(HomeTrendsFragment.this.getActivity(), "uid", "1"));
            for (int i = 0; i < circleThemeList.length(); i++) {
                JSONObject theme = circleThemeList.getJSONObject(i);
                TrendMessage msg = new TrendMessage(theme.getString("member_name"), theme.getString("theme_name"), theme.getString("theme_content"), theme.getString("circle_name"), theme.getString("theme_addtime"));
                String themeImg = ImageUtils.getContentFirstImgUrl(theme.getString("theme_content"));
                if (themeImg != null) {
                    msg.setTrend_img(themeImg);
                    msg.setTrend_content(ImageUtils.HideImageTag(theme.getString("theme_content")));
                } else {
                    msg.setTrend_img("");
                }
                msg.setTrend_id(theme.getString("theme_id"));
                msg.setTrend_user_avatar(theme.getString("member_avatar"));
                msg.setTrend_user_id(theme.getString("member_id"));
                data.add(msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        handler.sendEmptyMessage(1);
    }

    private void initLoadMore() {
        //TODO 加载更多
        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {

            }
        });
        loadMoreListViewContainer.setAutoLoadMore(true);
    }

    private void initLoading() {
        box.showLoadingLayout();
        box.setLoadingMessage("正在加载好友动态");
    }


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
                Toast.makeText(HomeTrendsFragment.this.getActivity(), "Str start", Toast.LENGTH_SHORT).show();
            }
        }, 100);
    }

    /**
     * 获取远程数据
     */
    private void initOriginData(final String uId) {
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        JsonObjectRequest objRequest = new JsonObjectRequest(ConstUtils.HOME_TRENDS + "&key=" + SharedPreferencesUtils.getParam(HomeTrendsFragment.this.getActivity(), "key", "test"), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject obj) {
                data.clear();
                try {
                    JSONObject jsonObject = obj.getJSONObject("datas");
                    JSONArray circleThemeList = jsonObject.getJSONArray("trends");
                    //  缓存获取到的结果
                    aCache.put("trendList" + uId, circleThemeList);
                    for (int i = 0; i < circleThemeList.length(); i++) {
                        JSONObject theme = circleThemeList.getJSONObject(i);
                        TrendMessage msg = new TrendMessage(theme.getString("member_name"), theme.getString("theme_name"), theme.getString("theme_content"), theme.getString("circle_name"), theme.getString("theme_addtime"));
                        String themeImg = ImageUtils.getContentFirstImgUrl(theme.getString("theme_content"));
                        if (themeImg != null) {
                            msg.setTrend_img(themeImg);
                            msg.setTrend_content(ImageUtils.HideImageTag(theme.getString("theme_content")));
                        } else {
                            msg.setTrend_img("");
                        }
                        msg.setTrend_id(theme.getString("theme_id"));
                        msg.setTrend_user_avatar(theme.getString("member_avatar"));
                        msg.setTrend_user_id(theme.getString("member_id"));
                        data.add(msg);
                        }
                    Log.e("sfs",String.valueOf(data.size()));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);
                  Toast.makeText(HomeTrendsFragment.this.getActivity(), "获取数据成功。", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeTrendsFragment.this.getActivity(), "获取数据失败，请重新获取。", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(objRequest);
        requestQueue.start();
    }

    private void initListView() {
        Log.e("TrendsListViewinit", "hello");
        trendAdapter = new TrendAdapter(HomeTrendsFragment.this.getActivity(), data);
        listView.setAdapter(trendAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void initView() {

    }

    @Override
    public void onClick(View view) {

    }
}
