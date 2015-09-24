package com.daxueoo.shopnc.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.adapter.NoticeListAdapter;
import com.daxueoo.shopnc.adapter.UserAdapter;
import com.daxueoo.shopnc.api.GetArticleList;
import com.daxueoo.shopnc.bean.ArticleList;
import com.daxueoo.shopnc.bean.ArticleListData;
import com.daxueoo.shopnc.model.Notice;
import com.daxueoo.shopnc.model.UserMessage;
import com.daxueoo.shopnc.network.ServiceGenerator;
import com.daxueoo.shopnc.ui.activity.NoticeListActivity;
import com.daxueoo.shopnc.ui.activity.UserFollowActivity;
import com.daxueoo.shopnc.ui.activity.WebViewActivity;
import com.daxueoo.shopnc.utils.ACache;
import com.daxueoo.shopnc.utils.ConstUtils;

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
import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * author guodont
 */
public class NoticeListFragment extends Fragment {

    private DynamicBox box;
    PtrClassicFrameLayout mPtrFrame;
    ListView listView;
    String type;

    private ACache aCache;

    private List<ArticleList> noticeData = new ArrayList<ArticleList>();
    private NoticeListAdapter adapter;
    private String url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice_list, container, false);

        RelativeLayout rl_content = (RelativeLayout) view.findViewById(R.id.rl_content);
        box = new DynamicBox(this.getActivity(), rl_content);
        //  下拉刷新
        mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_web_view_frame);
        //  ListView
        listView = (ListView) view.findViewById(R.id.list_fragment_notice);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        aCache = ACache.get(this.getActivity());
        initLoading();
        initPtr();
        initListView();

        handler.sendEmptyMessage(0);

    }

    private void initLoading() {
        box.showLoadingLayout();
        box.setLoadingMessage("正在加载资讯");
    }

    /**
     * handler初始化网络数据
     */
    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    initOriginData();
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

    //  从服务器获取资讯数据
    private void initOriginData() {
        noticeData.clear();
        GetArticleList getArticleList = ServiceGenerator.createService(GetArticleList.class, ConstUtils.BASE_URL);
        getArticleList.GetHomeNotice("1", new Callback<ArticleListData>() {
            @Override
            public void success(ArticleListData articleListData, retrofit.client.Response response) {
                noticeData.addAll(articleListData.getDatas().getArticleList());
                handler.sendEmptyMessage(1);
            }
            @Override
            public void failure(RetrofitError error) {
                Log.e("NOTICE", error.toString());
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
//                        loadComments(1);
                    }
                }, 100);
            }
        });
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                //  mPtrFrame.autoRefresh();
                Toast.makeText(NoticeListFragment.this.getActivity(), "Str start", Toast.LENGTH_SHORT).show();
            }
        }, 100);
    }

    private void initListView() {
        adapter = new NoticeListAdapter(this.getActivity(), noticeData);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(NoticeListFragment.this.getActivity(), WebViewActivity.class);
                intent.putExtra("url",  ConstUtils.NOTICE_URL + noticeData.get(position).getArticleId());
                intent.putExtra("type", ConstUtils.WEB_TYPE_MALL);
                startActivity(intent);
            }
        });
    }

    private void initData() {


    }
}
