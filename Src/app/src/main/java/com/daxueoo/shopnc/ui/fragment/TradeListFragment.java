package com.daxueoo.shopnc.ui.fragment;

import android.app.Fragment;
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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.adapter.TradeAdapter;
import com.daxueoo.shopnc.adapter.TrendAdapter;
import com.daxueoo.shopnc.model.TradeMessage;
import com.daxueoo.shopnc.model.TradeMessage;
import com.daxueoo.shopnc.sdk.Shopnc;
import com.daxueoo.shopnc.ui.activity.TaskDetailActivity;
import com.daxueoo.shopnc.ui.activity.TradeActivity;
import com.daxueoo.shopnc.ui.activity.TradeDetailActivity;
import com.daxueoo.shopnc.utils.ACache;
import com.daxueoo.shopnc.utils.ConstUtils;
import com.daxueoo.shopnc.utils.ImageUtils;
import com.daxueoo.shopnc.utils.SharedPreferencesUtils;
import com.daxueoo.shopnc.utils.TitleBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
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
 * Created by guodont on 15/9/10.
 */
public class TradeListFragment extends CommonDetailFragment implements View.OnClickListener{
    
    private PtrClassicFrameLayout mPtrFrame;
    private ListView listView;
    private List<TradeMessage> data = new ArrayList<TradeMessage>();
    private TradeAdapter tradeAdapter;
    private ACache aCache;
    private DynamicBox box;
    private LoadMoreListViewContainer loadMoreListViewContainer;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private List<String> mPlanetTitles = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapter;
    private HashMap<String, String> classId;
    private String classNowId;

    private boolean myCircle = false;
    private boolean refresh = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_trade_list, container, false);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_trade);
        box = new DynamicBox(this.getActivity(), linearLayout);
        //  下拉刷新
        mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_web_view_frame);
        //  ListView
        listView = (ListView) view.findViewById(R.id.list_fragment_trade);

        loadMoreListViewContainer = (LoadMoreListViewContainer) view.findViewById(R.id.load_more_list_view_container);
        loadMoreListViewContainer.useDefaultHeader();

        mDrawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) view.findViewById(R.id.left_drawer);

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        aCache = ACache.get(this.getActivity());
        initView();
        //initCategoryBar();
        //initDrawer(savedInstanceState);
        initListView();
        initPtr();
        initLoadMore();
        initLoading();
        handler.sendEmptyMessage(0);
        //handler.sendEmptyMessage(3);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPtrFrame.autoRefresh();
    }

    private void initCategoryBar() {
        new TitleBuilder(this.getActivity()).setRightImage(R.drawable.tilebar_right_btn).setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  打开侧滑菜单
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
        });

    }

    private void initDrawer(Bundle savedInstanceState) {

        mPlanetTitles.add("所有分类");
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        arrayAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.drawer_list_item, mPlanetTitles);
        mDrawerList.setAdapter(arrayAdapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.closeDrawer(Gravity.RIGHT);

        if (savedInstanceState == null) {
            if (!Shopnc.isHaveKey(TradeListFragment.this.getActivity())){
                myCircle = true;
            }
            selectItem(0);
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        mDrawerLayout.closeDrawer(Gravity.RIGHT);

        //  得到分类id
        if (position != 0) {
            classNowId = classId.get(mPlanetTitles.get(position));
            //  加载加载中
            initLoading();
            //
            handler.sendEmptyMessage(2);
        } else {
            if (!myCircle){
                handler.sendEmptyMessage(7);
            }
        }

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
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
                    Log.e("tradeList","加载数据中");
                    //  获取网络数据
                    //if (aCache.getAsJSONArray("tradeList") == null) {
                    initOriginData("0");
                    //}
                    break;
                case 1:
                    tradeAdapter.notifyDataSetChanged(); // 发送消息通知ListView更新
                    box.hideAll();
                    break;
                //  下拉刷新
                case 2:
                    //  获取最新数据
                    initOriginData(classNowId);
                    break;
                case 3:
                    initOriginData("0");
                    initClassData(ConstUtils.TRADE_CATEGORY);
                    break;
                case 4:
                    arrayAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 获取分类数据
     */
    private void initClassData(String url) {
        mPlanetTitles.clear();
        mPlanetTitles.add("所有分类");
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        JsonObjectRequest objRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject obj) {
                try {
                    JSONObject jsonObject = obj.getJSONObject("datas");
                    JSONArray tradeClass = jsonObject.getJSONArray("class_list");
                    //  缓存获取到的结果
                    aCache.put("trade_classes", tradeClass);
                    classId = new HashMap<String, String>();
                    for (int i = 0; i < tradeClass.length(); i++) {
                        JSONObject tclass = tradeClass.getJSONObject(i);
                        String tclassName = tclass.getString("gc_name");
                        String tclassId = tclass.getString("gc_id");
                        classId.put(tclassName, tclassId);
                        mPlanetTitles.add(tclassName);
                    }
                    refresh = false;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(4);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TradeListFragment.this.getActivity(), "获取数据失败，请重新获取。", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(objRequest);
        requestQueue.start();
    }
    private void initData() {
        try {
            JSONArray tradeList = aCache.getAsJSONArray("tradeList");
            for (int i = 0; i < tradeList.length(); i++) {
                JSONObject trade = tradeList.getJSONObject(i);
                TradeMessage msg = new TradeMessage(trade.getString("goods_name"), trade.getString("goods_description"), trade.getString("goods_store_price"), trade.getString("goods_click"), trade.getString("member_name"),trade.getString("goods_add_time"),trade.getString("flea_pname"),trade.getString("flea_pphone"));
                String tradeImg = trade.getString("goods_image");
                if (tradeImg != null) {
                    msg.setTrade_img(tradeImg);
                    msg.setTrade_desc(ImageUtils.HideImageTag(trade.getString("goods_description")));
                } else {
                    msg.setTrade_img("");
                }
                msg.setTrade_id(trade.getString("goods_id"));
                msg.setTrade_user_avatar(trade.getString("member_avatar"));
                msg.setTrade_id(trade.getString("member_id"));
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
        box.setLoadingMessage("正在加载交易列表");
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
                Toast.makeText(TradeListFragment.this.getActivity(), "Str start", Toast.LENGTH_SHORT).show();
            }
        }, 100);
    }

    /**
     * 获取远程数据
     */
    private void initOriginData(String classNowId) {
        String url = "";
        if (classNowId != "0"){
            url = ConstUtils.TRADE_ALL +"&page=50"+"&cid="+classNowId;
        }else{
            url = ConstUtils.TRADE_ALL +"&page=50";
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        JsonObjectRequest objRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject obj) {
                data.clear();
                try {
                    JSONObject jsonObject = obj.getJSONObject("datas");
                    JSONArray tradeList = jsonObject.getJSONArray("trade_list");
                    //  缓存获取到的结果
                    Log.e("tradeList",tradeList.toString());
                    aCache.put("tradeList", tradeList);
                    for (int i = 0; i < tradeList.length(); i++) {
                        JSONObject trade = tradeList.getJSONObject(i);
                        TradeMessage msg = new TradeMessage(trade.getString("goods_name"), trade.getString("goods_description"), trade.getString("goods_store_price"), trade.getString("goods_click"), trade.getString("member_name"),trade.getString("goods_add_time"),trade.getString("flea_pname"),trade.getString("flea_pphone"));
                        String tradeImg = trade.getString("goods_image");
                        if (tradeImg != null) {
                            msg.setTrade_img(tradeImg);
                            msg.setTrade_desc(ImageUtils.HideImageTag(trade.getString("goods_description")));
                        } else {
                            msg.setTrade_img("");
                        }
                        msg.setTrade_id(trade.getString("goods_id"));
                        msg.setTrade_user_avatar(trade.getString("member_avatar"));
                        msg.setTrade_user_id(trade.getString("member_id"));
                        msg.setTrade_username(trade.getString("member_name"));
                        data.add(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);
                Toast.makeText(TradeListFragment.this.getActivity(), "获取数据成功。", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TradeListFragment.this.getActivity(), "获取数据失败，请重新获取。", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(objRequest);
        requestQueue.start();
    }

    private void initListView() {
        tradeAdapter = new TradeAdapter(this.getActivity(), data);
        listView.setAdapter(tradeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("trade",data.get(position));
                intent.setClass(TradeListFragment.this.getActivity(), TradeDetailActivity.class);
                startActivityForResult(intent, 2);
            }
        });

    }

    private void initView() {

    }

    @Override
    public void onClick(View view) {

    }
}
