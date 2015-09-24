package com.daxueoo.shopnc.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.adapter.StickThemeListAdapter;
import com.daxueoo.shopnc.adapter.ThemeListAdapter;
import com.daxueoo.shopnc.api.GetCircleThemeList;
import com.daxueoo.shopnc.bean.CircleThemeList;
import com.daxueoo.shopnc.bean.Reply;
import com.daxueoo.shopnc.bean.Theme;
import com.daxueoo.shopnc.bean.ThemeListData;
import com.daxueoo.shopnc.model.ThemeMessage;
import com.daxueoo.shopnc.model.TopicMessage;
import com.daxueoo.shopnc.network.ServiceGenerator;
import com.daxueoo.shopnc.ui.activity.CircleProfileActivity;
import com.daxueoo.shopnc.ui.activity.CreateThemeActivity;
import com.daxueoo.shopnc.ui.activity.CustomActivity;
import com.daxueoo.shopnc.ui.activity.DetailActivity;
import com.daxueoo.shopnc.ui.activity.QaActivity;
import com.daxueoo.shopnc.ui.activity.TaskDetailActivity;
import com.daxueoo.shopnc.ui.activity.ThemeListActivity;
import com.daxueoo.shopnc.utils.ACache;
import com.daxueoo.shopnc.utils.ConstUtils;
import com.daxueoo.shopnc.utils.ImageUtils;
import com.daxueoo.shopnc.utils.TitleBuilder;
import com.daxueoo.shopnc.utils.ToastUtils;
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
import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * Created by user on 15-8-20.
 */
public class CircleDetailFragment extends CommonDetailFragment implements View.OnClickListener {

    private String TAG = "CircleDetailFragment";
    private int curpage = 1;

    private PtrClassicFrameLayout mPtrFrame;
    private ACache aCache;
    private DynamicBox box;
    private LoadMoreListViewContainer loadMoreListViewContainer;
    private FloatingActionButton fab;
    private DrawerLayout mDrawerLayout; //  圈子简介侧栏
    private ActionBarDrawerToggle mDrawerToggle;

    private ListView listView;
    private ListView stickListView;
    private View circle_head_qa;
    private View circleStickList;

    private List<Theme> data = new ArrayList<Theme>();
    private List<TopicMessage> stickData = new ArrayList<TopicMessage>();

    private ThemeListAdapter topicAdapter;
    private StickThemeListAdapter stickThemeListAdapter;

    private TitleBuilder titleBuilder;

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

//        stickListView = (ListView) view.findViewById(R.id.list_stick_theme);

        mDrawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        loadMoreListViewContainer = (LoadMoreListViewContainer) view.findViewById(R.id.load_more_list_view_container);
        loadMoreListViewContainer.useDefaultHeader();
        fab = (FloatingActionButton) view.findViewById(R.id.normal_plus);
        return view;

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        aCache = ACache.get(this.getActivity());
        inintHeader();
        initView();
        initListView();
        initPtr();
        initLoading();
        initLoadMore();
//        if (aCache.getAsJSONArray("themeList" + ((ThemeListActivity) CircleDetailFragment.this.getActivity()).getCircleId()) == null) {
        handler.sendEmptyMessage(0);
//        } else {
//            initData();
//        }
        initFab();

    }

    public void setListViewHeightBasedOnChildren(ListView listView) {

        //获取listview的适配器
        ListAdapter listAdapter = listView.getAdapter();
        //item的高度
        int itemHeight = 35;

        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            totalHeight += Dp2Px(CircleDetailFragment.this.getActivity(),itemHeight)+listView.getDividerHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;

        listView.setLayoutParams(params);
    }

    public int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private void inintHeader() {
        //置顶话题 视图
        circle_head_qa = View.inflate(this.getActivity(),R.layout.include_circle_qa_bar,null);
        circle_head_qa.setVisibility(View.VISIBLE);
        circleStickList = View.inflate(this.getActivity(), R.layout.include_stick_theme_list, null);
        stickListView = (ListView) circleStickList.findViewById(R.id.list_stick_theme);
        circle_head_qa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(CircleDetailFragment.this.getActivity(), QaActivity.class);
                intent.putExtra("QaCircleId", ((ThemeListActivity) CircleDetailFragment.this.getActivity()).getCircleId());
                Log.e("QACIRCLEID", ((ThemeListActivity) CircleDetailFragment.this.getActivity()).getCircleId());
                intent.putExtra("QaType", "5");
                startActivity(intent);
            }
        });
    }

    private void initDrawer() {
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
//        mDrawerLayout.addView();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.closeDrawer(Gravity.RIGHT);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPtrFrame.autoRefresh();
    }

    private void initFab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(CircleDetailFragment.this.getActivity(), CreateThemeActivity.class);
                intent.putExtra("circleId", ((ThemeListActivity) CircleDetailFragment.this.getActivity()).getCircleId());
                startActivityForResult(intent, 2);
            }
        });
    }

    /**
     * 初始化加载更多
     */
    private void initLoadMore() {
        //TODO 加载更多
        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                curpage = curpage + 1;
                handler.sendEmptyMessage(3);
                // 请求下一页数据
//                回调。
//                loadMoreListViewContainer.loadMoreFinish(mDataModel.getListPageInfo().isEmpty(), mDataModel.getListPageInfo().hasMore());
            }
        });
        loadMoreListViewContainer.setAutoLoadMore(true);
//        loadMoreListViewContainer.setLoadMoreView();
    }

    private void initData() {

        ThemeListData themeListData = (ThemeListData) aCache.getAsObject("themeList" + ((ThemeListActivity) CircleDetailFragment.this.getActivity()).getCircleId());
        data.addAll(themeListData.getThemes());
        handler.sendEmptyMessage(1);
    }

    private void initLoading() {
        box.showLoadingLayout();
        box.setLoadingMessage("Loading your circle ...");
    }

    /**
     * 初始化ListView
     */
    private void initListView() {
        topicAdapter = new ThemeListAdapter(this.getActivity(), data);

        stickThemeListAdapter = new StickThemeListAdapter(this.getActivity(), stickData);
        stickListView.setAdapter(stickThemeListAdapter);

//        setListViewHeightBasedOnChildren(stickListView);
        stickListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent = new Intent();

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Theme theme = data.get(i);
                intent.setClass(CircleDetailFragment.this.getActivity(), DetailActivity.class);
                intent.putExtra("BUNDLE_KEY_DISPLAY_TYPE", 0);
                intent.putExtra("detailId", theme.getThemeId());
                intent.putExtra("circleId", theme.getCircleId());
                startActivity(intent);
            }
        });
        listView.addHeaderView(circle_head_qa,null,false);
        listView.addHeaderView(circleStickList,null,false);
        listView.setAdapter(topicAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Theme theme = (Theme) parent.getAdapter().getItem(position);
                Theme theme = data.get(position);
                Intent intent = new Intent();
                intent.setClass(CircleDetailFragment.this.getActivity(), DetailActivity.class);
                intent.putExtra("BUNDLE_KEY_DISPLAY_TYPE", 0);
                intent.putExtra("detailId", theme.getThemeId());
                intent.putExtra("circleId", theme.getCircleId());
                startActivityForResult(intent, 2);
            }
        });
    }

    /**
     * 初始化视图
     */
    private void initView() {

        titleBuilder = new TitleBuilder(CircleDetailFragment.this.getActivity()).setTitleText("圈子详情").setRightImage(R.drawable.icon_menu_normal).setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(CircleDetailFragment.this.getActivity(), "圈子简介", Toast.LENGTH_SHORT);
//                mDrawerLayout.openDrawer(Gravity.RIGHT);
                //打开圈子简介
                Intent intent = new Intent();
                intent.putExtra("circleId", ((ThemeListActivity) CircleDetailFragment.this.getActivity()).getCircleId());
                intent.setClass(CircleDetailFragment.this.getActivity(), CircleProfileActivity.class);
                startActivity(intent);

            }
        }).setLeftImage(R.drawable.navigationbar_back_sel).setLeftOnClickListener(this);
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
                    //if (aCache.getAsJSONArray("themeList") == null) {
                    initOriginStickData(((ThemeListActivity) CircleDetailFragment.this.getActivity()).getCircleId());
                    initOriginPageData(((ThemeListActivity) CircleDetailFragment.this.getActivity()).getCircleId(), "20", "1", false);
                    titleBuilder.setTitleText(((ThemeListActivity) CircleDetailFragment.this.getActivity()).getCircleTitle());
                    //}
                    break;
                case 1:
                    topicAdapter.notifyDataSetChanged(); // 发送消息通知ListView更新
                    stickThemeListAdapter.notifyDataSetChanged();
                    box.hideAll();
                    Toast.makeText(CircleDetailFragment.this.getActivity(), "没有更多新数据了。。", Toast.LENGTH_LONG).show();
                    break;
                //  下拉刷新
                case 2:
                    initOriginStickData(((ThemeListActivity) CircleDetailFragment.this.getActivity()).getCircleId());
                    curpage = 1;
                    initOriginPageData(((ThemeListActivity) CircleDetailFragment.this.getActivity()).getCircleId(), "20", "1", true);
                    //  获取最新数据
                    break;
                case 3:
                    initOriginPageData(((ThemeListActivity) CircleDetailFragment.this.getActivity()).getCircleId(), "20", String.valueOf(curpage), false);
                    break;
                default:
                    break;
            }
        }
    };

    private void initOriginPageData(final String circleId, final String page, final String curpage, boolean is_refresh) {
        //
        if (is_refresh) {
            data.clear();
        }
        GetCircleThemeList getCircleThemeList = ServiceGenerator.createService(GetCircleThemeList.class, ConstUtils.BASE_URL);
        getCircleThemeList.GetThemeListPage(curpage, page, circleId, new Callback<CircleThemeList>() {
            @Override
            public void success(CircleThemeList circleThemeList, retrofit.client.Response response) {
                if (curpage.equals("1")) {
                    aCache.put("themeList" + circleId, circleThemeList.getDatas());
                }
                data.addAll(circleThemeList.getDatas().getThemes());
                if (!circleThemeList.getHasmore()) {
                    //  获取数据
                    //  显示数据
                    if (circleThemeList.getDatas().getThemes() == null) {
                        loadMoreListViewContainer.loadMoreFinish(true, false);
                    } else {
                        loadMoreListViewContainer.loadMoreFinish(false, false);
                    }
                } else {
                    loadMoreListViewContainer.loadMoreFinish(false, true);
                }
                handler.sendEmptyMessage(1);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("tag", error.toString());
            }
        });
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
                        handler.sendEmptyMessage(2);
                    }
                }, 100);
            }
        });
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                //  mPtrFrame.autoRefresh();
                Toast.makeText(CircleDetailFragment.this.getActivity(), "Str start", Toast.LENGTH_SHORT).show();
            }
        }, 100);
    }

    /**
     * 获取置顶话题 网络数据
     */
    private void initOriginStickData(final String circleId) {
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        JsonObjectRequest objectRequestStickTheme = new JsonObjectRequest(ConstUtils.CIRCLE_STICK_THEMES + "&circle_id=" + circleId, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                stickData.clear();
                try {
                    JSONObject jsonObject = response.getJSONObject("datas");
                    JSONArray circleThemeList = jsonObject.getJSONArray("themes");
                    //  缓存获取到的结果
                    aCache.put("stickThemeList" + circleId, circleThemeList);
                    for (int i = 0; i < circleThemeList.length(); i++){
                        JSONObject theme = circleThemeList.getJSONObject(i);
                        TopicMessage mtheme = new TopicMessage();
                        mtheme.setTopic_id(theme.getString("theme_id"));
                        mtheme.setTopic_title(theme.getString("theme_name"));
                        stickData.add(mtheme);
                    }
                    Log.e("stickData", String.valueOf(stickData.size()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CircleDetailFragment.this.getActivity(), "获取置顶数据失败", Toast.LENGTH_LONG).show();
            }
        });
        //加入到网络请求队列
        requestQueue.add(objectRequestStickTheme);
        requestQueue.start();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                CircleDetailFragment.this.getActivity().finish();
                break;

        }
    }
}