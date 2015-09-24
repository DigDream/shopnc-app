package com.daxueoo.shopnc.ui.fragment;

import android.content.Intent;
import android.media.MediaActionSound;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.adapter.GoodsListAdapter;
import com.daxueoo.shopnc.model.GoodsMessage;
import com.daxueoo.shopnc.scan.CaptureActivity;
import com.daxueoo.shopnc.ui.activity.CircleProfileActivity;
import com.daxueoo.shopnc.ui.activity.ThemeListActivity;
import com.daxueoo.shopnc.utils.ACache;
import com.daxueoo.shopnc.utils.ConstUtils;
import com.daxueoo.shopnc.utils.TitleBuilder;
import com.daxueoo.shopnc.utils.ToastUtils;

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
 * 这是商城的Fragment
 */

public class MallFragment extends BaseFragment implements View.OnClickListener{

    private PtrClassicFrameLayout mPtrFrame;
    private ListView listView;
    private List<GoodsMessage> data = new ArrayList<GoodsMessage>();
    private GoodsListAdapter goodsListAdapter;
    private ACache aCache;
    private DynamicBox box;
    private LinearLayout linearLayout;
    private LoadMoreListViewContainer loadMoreListViewContainer;

    private View shop_list_top_bar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_goods_list, container, false);

        linearLayout = (LinearLayout) view.findViewById(R.id.ll_goods_list);
        box = new DynamicBox(MallFragment.this.getActivity(), linearLayout);
        //  下拉刷新
        mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_web_view_frame);
        //  ListView
        listView = (ListView) view.findViewById(R.id.list_fragment_goods);
        loadMoreListViewContainer = (LoadMoreListViewContainer) view.findViewById(R.id.load_more_list_view_container);
        loadMoreListViewContainer.useDefaultHeader();
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        aCache = ACache.get(this.getActivity());
        initHeader();
        initView();
        initListView();
        initPtr();
        initLoadMore();
        initLoading();
        handler.sendEmptyMessage(0);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPtrFrame.autoRefresh();
    }


    /**
     * 初始化头部排序栏
     */
    private void initHeader() {
        shop_list_top_bar =  View.inflate(this.getActivity(), R.layout.include_shop_sort_bar, null);
    }


    private void initListView() {
        goodsListAdapter = new GoodsListAdapter(MallFragment.this.getActivity(), data);
        listView.addHeaderView(shop_list_top_bar,null,false);
        listView.setAdapter(goodsListAdapter);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MallFragment.this.getActivity(), "进入商品详情？。", Toast.LENGTH_LONG).show();
//            }
//        });
    }

    private void initView() {
        new TitleBuilder(MallFragment.this.getActivity()).setTitleText("圈圈商城").setRightImage(R.drawable.icon_scan_normal).setRightOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MallFragment.this.getActivity(), CaptureActivity.class);
                startActivity(intent);
            }
        });
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
//                    if (aCache.getAsJSONArray("goodsList") == null) {
                        initOriginData();
//                    } else {
//                        initData();
//                    }
                    break;
                case 1:
                    goodsListAdapter.notifyDataSetChanged(); // 发送消息通知ListView更新
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
        box.setLoadingMessage("正在加载商品");
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
                Toast.makeText(MallFragment.this.getActivity(), "Str start", Toast.LENGTH_SHORT).show();
            }
        }, 100);
    }

    /**
     * 获取缓存数据
     */
    private void initData() {
        try {
            JSONArray goodsList = aCache.getAsJSONArray("goodsList");
            for (int i = 0; i < goodsList.length(); i++) {
                JSONObject goods = goodsList.getJSONObject(i);
                GoodsMessage msg = new GoodsMessage(goods.getString("is_fcode"),
                        goods.getString("is_presell"), goods.getString("have_gift"), goods.getString("goods_name"),
                        goods.getString("goods_price"), goods.getString("goods_marketprice"), goods.getString("goods_salenum")
                        ,goods.getString("evaluation_good_star"), goods.getString("group_flag"), goods.getString("xianshi_flag"),
                        goods.getString("goods_image"), goods.getString("xianshi_flag"));
                data.add(msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        handler.sendEmptyMessage(1);
    }

    /**
     * 获取远程数据
     */
    private void initOriginData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        JsonObjectRequest objRequest = new JsonObjectRequest(ConstUtils.GOODS_LIST+"&gc_id=1", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject obj) {
                data.clear();
                try {
                    JSONObject jsonObject = obj.getJSONObject("datas");
                    JSONArray goodsList = jsonObject.getJSONArray("goods_list");
                    Log.e("GOODSLIST",goodsList.toString());
                    //  缓存获取到的结果
                    aCache.put("goodsList", goodsList);
                    for (int i = 0; i < goodsList.length(); i++) {
                        JSONObject goods = goodsList.getJSONObject(i);
                        GoodsMessage msg = new GoodsMessage(goods.getString("is_fcode"),
                                goods.getString("is_presell"), goods.getString("have_gift"), goods.getString("goods_name"),
                                goods.getString("goods_price"), goods.getString("goods_marketprice"), goods.getString("goods_salenum")
                                ,goods.getString("evaluation_good_star"), goods.getString("group_flag"), goods.getString("xianshi_flag"),
                                goods.getString("goods_image"), goods.getString("goods_id"));
                        msg.setGoods_image_url(goods.getString("goods_image_url"));
                        data.add(msg);
                    }
                    Log.e("sfs",String.valueOf(data.size()));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);
                Toast.makeText(MallFragment.this.getActivity(), "获取数据成功。", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MallFragment.this.getActivity(), "获取数据失败，请重新获取。", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(objRequest);
        requestQueue.start();
    }

    @Override
    public void onClick(View view) {

    }
}
