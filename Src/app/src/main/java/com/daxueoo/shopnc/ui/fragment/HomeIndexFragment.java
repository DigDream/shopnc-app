package com.daxueoo.shopnc.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.adapter.HomeCustomAdapter;
import com.daxueoo.shopnc.adapter.ImagePagerAdapter;
import com.daxueoo.shopnc.adapter.TopicAdapter;
import com.daxueoo.shopnc.api.GetArticleList;
import com.daxueoo.shopnc.api.GetCircleThemeList;
import com.daxueoo.shopnc.bean.ArticleDatas;
import com.daxueoo.shopnc.bean.ArticleList;
import com.daxueoo.shopnc.bean.ArticleListData;
import com.daxueoo.shopnc.bean.CircleThemeList;
import com.daxueoo.shopnc.bean.Theme;
import com.daxueoo.shopnc.bean.ThemeListData;
import com.daxueoo.shopnc.model.ExploreMessage;
import com.daxueoo.shopnc.model.Notice;
import com.daxueoo.shopnc.model.TopicMessage;
import com.daxueoo.shopnc.network.ServiceGenerator;
import com.daxueoo.shopnc.scan.CaptureActivity;
import com.daxueoo.shopnc.ui.activity.DetailActivity;
import com.daxueoo.shopnc.ui.activity.QaActivity;
import com.daxueoo.shopnc.ui.activity.ThemeListActivity;
import com.daxueoo.shopnc.ui.activity.UserInfoActivity;
import com.daxueoo.shopnc.ui.activity.WebViewActivity;
import com.daxueoo.shopnc.utils.ACache;
import com.daxueoo.shopnc.utils.ConstUtils;
import com.daxueoo.shopnc.utils.ImageUtils;
import com.daxueoo.shopnc.utils.ListUtils;
import com.daxueoo.shopnc.utils.SharedPreferencesUtils;
import com.daxueoo.shopnc.utils.SystemUtils;
import com.daxueoo.shopnc.utils.ToastUtils;
import com.daxueoo.shopnc.widgets.ForScrollViewListView;
import com.daxueoo.shopnc.widgets.VerticalScrollTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
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
public class HomeIndexFragment extends BaseFragment implements View.OnClickListener {
    private String TAG = "HomeFragment";

    private int curpage = 1;

    //  自动滚动图片
    private AutoScrollViewPager viewPager;
    private List<Integer> imageIdList;

    //  Header标题
    private TextView tv_title;

    private ForScrollViewListView listView;

    private List<Theme> data = new ArrayList<Theme>();
    private TopicAdapter topicAdapter;

    private PtrClassicFrameLayout mPtrFrame;
    private ScrollView scrollView;
    private LoadMoreListViewContainer loadMoreListViewContainer;
    private ACache aCache;
    private DynamicBox box;
    private boolean isFirst = true;

    private RelativeLayout rl_qa_daren, rl_qa_zhuanjia;

    private VerticalScrollTextView vNotice;

    private TextView tv_notice2;

    //  网格
    private GridView gridView;
    private List<ExploreMessage> customData = new ArrayList<ExploreMessage>();

    private HomeCustomAdapter homeCustomAdapter;

    private ArrayList<Notice> noticeData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_index, container, false);

        mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_web_view_frame);
        //  滚动图片
        viewPager = (AutoScrollViewPager) view.findViewById(R.id.view_pager);

        listView = (ForScrollViewListView) view.findViewById(R.id.list_fragment_topic);

        box = new DynamicBox(this.getActivity(), listView);

        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        //  设置滚动到顶部
        scrollView.smoothScrollTo(0, 0);
        rl_qa_daren = (RelativeLayout) view.findViewById(R.id.rl_qa_daren);
        rl_qa_zhuanjia = (RelativeLayout) view.findViewById(R.id.rl_qa_zhuanjia);

        vNotice = (VerticalScrollTextView) view.findViewById(R.id.tv_notice);

        tv_notice2 = (TextView) view.findViewById(R.id.tv_notice_2);
        //GridView
        gridView = (GridView) view.findViewById(R.id.gv_home_custom);

        loadMoreListViewContainer = (LoadMoreListViewContainer) view.findViewById(R.id.load_more_list_view_container);
        loadMoreListViewContainer.useDefaultHeader();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        aCache = ACache.get(this.getActivity());
        //  初始化按钮
        initButton();
        //  初始化滚动图片
        initViewPager();
        //  初始化ListView，后期换成RecyclerView
        //  TODO    换成RecyclerView
        initListView();

        initLoading();

        //  初始化上拉加载
        initLoadMore();

        if (isFirst) {
            handler.sendEmptyMessage(0);
//            thread.start();
            isFirst = false;
            //  初始化数据
//            if (aCache.getAsJSONArray("themeList") != null) {
//                initData();
//            }
        }
        //  设置ScrollView嵌套ListView的滚动问题

        //  初始化下拉刷新
        initPtr();

        initGridViewData();
        initGridView();

        noticeData = new ArrayList<Notice>();
        SystemUtils.setGridViewHeightBasedOnChildren(gridView);

        handler.sendEmptyMessage(4);


    }

    private void initNoticeView() {
        tv_notice2.setText(noticeData.get(1).getTitle());

        tv_notice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(HomeIndexFragment.this.getActivity(), WebViewActivity.class);
                intent.putExtra("url", noticeData.get(1).getUrl());
                intent.putExtra("type", ConstUtils.WEB_TYPE_MALL);
                startActivity(intent);
            }
        });

    }

    private void initNoticeData() {

        noticeData.clear();
        GetArticleList getArticleList = ServiceGenerator.createService(GetArticleList.class, ConstUtils.BASE_URL);
        getArticleList.GetHomeNotice("1", new Callback<ArticleListData>() {
            @Override
            public void success(ArticleListData articleListData, retrofit.client.Response response) {
                List<ArticleList> articleDatas = articleListData.getDatas().getArticleList();
                for (int i = 0; i < articleDatas.size(); i++) {
                    Notice notice = new Notice(Integer.parseInt(articleDatas.get(i).getArticleId()), articleDatas.get(i).getArticleTitle(),
                            ConstUtils.NOTICE_URL + articleDatas.get(i).getArticleId());
                    noticeData.add(notice);
                }
                initNoticeView();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("NOTICE", error.toString());
            }
        });
    }

    private void initLoading() {
        box.showLoadingLayout();
        box.setLoadingMessage("Loading your theme ...");
    }

    private void initNoticeData2() {
        ArrayList lst = new ArrayList<Notice>();
        for (int i = 0; i < 3; i++) {
            if (i % 2 == 0) {
                Notice sen = new Notice(i, "大学圈圈网站即将上线");
                lst.add(i, sen);
            } else {
                Notice sen = new Notice(i, "第二期将推出手机商城");
                lst.add(i, sen);
            }
        }
        //给View传递数据
        vNotice.setList(lst);
        //更新View
        vNotice.updateUI();
    }

    private void initGridView() {
        homeCustomAdapter = new HomeCustomAdapter(this.getActivity(), customData);
        gridView.setAdapter(homeCustomAdapter);
    }

    private void initGridViewData() {
        customData.clear();

        ExploreMessage msg2 = new ExploreMessage();
        msg2.setExplore_name("我的待办");
        msg2.setIcon_res(R.drawable.home_task);
        if ((Boolean) SharedPreferencesUtils.getParam(HomeIndexFragment.this.getActivity(), "cb_explore_my_task", true)) {
            customData.add(msg2);
        }

        ExploreMessage msg3 = new ExploreMessage();
        msg3.setExplore_name("我的订单");
        msg3.setIcon_res(R.drawable.home_serve);
        if ((Boolean) SharedPreferencesUtils.getParam(HomeIndexFragment.this.getActivity(), "cb_explore_my_order", true)) {
            customData.add(msg3);
        }


        ExploreMessage msg4 = new ExploreMessage();
        msg4.setExplore_name("消息中心");
        msg4.setIcon_res(R.drawable.home_message);
        if ((Boolean) SharedPreferencesUtils.getParam(HomeIndexFragment.this.getActivity(), "cb_explore_my_message", true)) {
            customData.add(msg4);
        }

        ExploreMessage msg6 = new ExploreMessage();
        msg6.setExplore_name("交易大厅");
        msg6.setIcon_res(R.drawable.home_serve);
        if ((Boolean) SharedPreferencesUtils.getParam(HomeIndexFragment.this.getActivity(), "cb_explore_trade", true)) {
            customData.add(msg6);
        }

        ExploreMessage msg5 = new ExploreMessage();
        msg5.setExplore_name("发现更多");
        msg5.setIcon_res(R.drawable.home_more);
        customData.add(msg5);

        Log.e("CUSTOMEDATA",String.valueOf(customData.size())+gridView.getHeight());
    }

    /**
     * 初始化按钮
     */
    private void initButton() {
        rl_qa_zhuanjia.setOnClickListener(this);
        rl_qa_daren.setOnClickListener(this);
    }

    /**
     * 初始化加载更多
     */
    private void initLoadMore() {
        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                curpage = curpage + 1;
                handler.sendEmptyMessage(5);
                ToastUtils.showToast(HomeIndexFragment.this.getActivity(),"加载更多"+curpage,Toast.LENGTH_SHORT);
            }
        });
        loadMoreListViewContainer.setAutoLoadMore(false);
    }

    /**
     * 下拉刷新
     */
    private void initPtr() {
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, scrollView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.refreshComplete();
                        //  异步刷新数据
                        handler.sendEmptyMessage(1);
                    }
                }, 100);
            }
        });
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                //  mPtrFrame.autoRefresh();
                Toast.makeText(HomeIndexFragment.this.getActivity(), "Str start", Toast.LENGTH_SHORT).show();
            }
        }, 100);
    }

    /**
     * 多线程获取数据
     */
    private Thread thread = new Thread() {
        @Override
        public void run() {
            //  判断网络连接情况获取数据
            if (isNetConnected()) {
                handler.sendEmptyMessage(0);
            } else {
                Toast.makeText(HomeIndexFragment.this.getActivity(), R.string.network_fail, Toast.LENGTH_SHORT).show();
            }
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
//                    if (aCache.getAsJSONArray("themeList") == null) {
                        initOriginPageData("20", "1", false);
//                    }
                    break;
                case 1:
                    topicAdapter.notifyDataSetChanged(); // 发送消息通知ListView更新
                    SystemUtils.setListViewHeightBasedOnChildren(listView);
                    SystemUtils.setGridViewHeightBasedOnChildren(gridView);
                    box.hideAll();
                    break;
                //  下拉刷新
                case 2:
                    //  获取最新数据
                    curpage = 1;
                    initOriginPageData("20", "1", true);
                    break;
                case 3:
                    topicAdapter.notifyDataSetChanged(); // 发送消息通知ListView更新
                    box.hideAll();
                    break;
                case 4:
                    initNoticeData();
                    break;
                case 5:
                    initOriginPageData("20", String.valueOf(curpage), false);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 获取远程数据
     */
    private void initOriginPageData(final String page, final String curpage, boolean is_refresh) {
        if (is_refresh) {
            data.clear();
        }
        GetCircleThemeList getCircleThemeList = ServiceGenerator.createService(GetCircleThemeList.class, ConstUtils.BASE_URL);
        getCircleThemeList.GetHomeThemeListPage(curpage, page,new Callback<CircleThemeList>() {
            @Override
            public void success(CircleThemeList circleThemeList, retrofit.client.Response response) {
                if (curpage.equals("1")) {
                    aCache.put("themeList", circleThemeList.getDatas());
                }
                Log.e("usrl", response.getUrl());
                data.addAll(circleThemeList.getDatas().getThemes());
                Log.e("terwrrwrewrq", String.valueOf(circleThemeList.getDatas().getThemes().size()));
                if (!circleThemeList.getHasmore()) {
                    //  获取数据
                    Log.e("te123432st", circleThemeList.getDatas().getThemes().get(1).toString());
                    //  显示数据
                    if (circleThemeList.getDatas().getThemes() == null) {
                        loadMoreListViewContainer.loadMoreFinish(true, false);
                    } else {
                        loadMoreListViewContainer.loadMoreFinish(false, false);
                    }
                } else {
                    Log.e("te123t", circleThemeList.getDatas().getThemes().get(1).toString());
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
     * ListView
     */

    private void initListView() {
        topicAdapter = new TopicAdapter(this.getActivity(), data);
        listView.setAdapter(topicAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, view.getTag().toString());
                Intent intent = new Intent();
                intent.setClass(HomeIndexFragment.this.getActivity(), DetailActivity.class);
                intent.putExtra("BUNDLE_KEY_DISPLAY_TYPE", 0);
                intent.putExtra("detailId", data.get(position).getThemeId());
                intent.putExtra("circleId", "");
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化数据，从缓存里读入，ACache
     */
    private void initData() {
        ThemeListData themeListData = (ThemeListData) aCache.getAsObject("themeList");
        data.addAll(themeListData.getThemes());
        handler.sendEmptyMessage(1);
    }

    /**
     * 初始化ViewPager，即滚动的视图
     */
    private void initViewPager() {
        imageIdList = new ArrayList<Integer>();
        // TODO 添加网络图片，设置跳转链接
        imageIdList.add(R.mipmap.banner1);
        imageIdList.add(R.mipmap.banner2);
        imageIdList.add(R.mipmap.banner3);
        imageIdList.add(R.mipmap.banner4);

        //  调用SystemUtils的获取宽高方法
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (SystemUtils.getScreen(this.getActivity()).heightPixels / 4));
        viewPager.setLayoutParams(params);
        viewPager.setAdapter(new ImagePagerAdapter(this.getActivity(), imageIdList).setInfiniteLoop(true));

        //  setOnPageChangeListener过期方法，被addOnPageChangeListener、removeOnPageChangeListener代替
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());

        viewPager.setInterval(2000);
        viewPager.startAutoScroll();
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % ListUtils.getSize(imageIdList));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.rl_qa_zhuanjia:
                //  问达人
                intent.setClass(HomeIndexFragment.this.getActivity(), QaActivity.class);
                intent.putExtra("QaCircleId", "0");
                intent.putExtra("QaType", "5");
                startActivity(intent);
                break;
            case R.id.rl_qa_daren:
                //  问专家
                intent.setClass(HomeIndexFragment.this.getActivity(), QaActivity.class);
                intent.putExtra("QaCircleId", "0");
                intent.putExtra("QaType", "6");
                startActivity(intent);
                break;
        }
    }

    /**
     * 一个ViewPager的OnPageChangeListener类，可以重写onPageSelected方法去添加小圆点等
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mPtrFrame.autoRefresh();
    }
}
