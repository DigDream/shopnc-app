package com.daxueoo.shopnc.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.adapter.ImagePagerAdapter;
import com.daxueoo.shopnc.adapter.TopicAdapter;
import com.daxueoo.shopnc.model.TopicMessage;
import com.daxueoo.shopnc.utils.ListUtils;
import com.daxueoo.shopnc.utils.SystemUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;

/**
 * 这是首页的Fragment，主要有滚动图片，ListView，Button等
 */

public class HomeFragment extends BaseFragment {
    private String TAG = "HomeFragment";

    private AutoScrollViewPager viewPager;
    private List<Integer> imageIdList;

    private TextView tv_title;

    private ListView mListView;

    private List<TopicMessage> data = new ArrayList<TopicMessage>();
    private TopicAdapter mAdapter;

    private PtrClassicFrameLayout mPtrFrame;
    private ScrollView mScrollView;
    private LoadMoreListViewContainer loadMoreListViewContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(activity, R.layout.fragment_home, null);
        mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_web_view_frame);
        //滚动图片
        viewPager = (AutoScrollViewPager) view.findViewById(R.id.view_pager);
        tv_title = (TextView) view.findViewById(R.id.titlebar_tv);

        mListView = (ListView) view.findViewById(R.id.list_fragment_topic);

        mScrollView = (ScrollView) view.findViewById(R.id.scrollView);

        loadMoreListViewContainer = (LoadMoreListViewContainer) view.findViewById(R.id.load_more_list_view_container);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_title.setText(R.string.tab_tv_index);

        initViewPager();
        initListView();
        initData();
        SystemUtils.setListViewHeightBasedOnChildren(mListView);
        initPtr();
        initLoadMore();

    }

    private void initLoadMore() {
        // load more container
        loadMoreListViewContainer.useDefaultHeader();
        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                //mDataModel.queryNextPage();
            }
        });

//        // process data
//        EventCenter.bindContainerAndHandler(this, new DemoSimpleEventHandler() {
//
//            public void onEvent(ImageListDataEvent event) {
//
//                // ptr
//                mPtrFrameLayout.refreshComplete();
//
//                // load more
//                loadMoreListViewContainer.loadMoreFinish(mDataModel.getListPageInfo().isEmpty(), mDataModel.getListPageInfo().hasMore());
//
//                mAdapter.notifyDataSetChanged();
//            }
//
//            public void onEvent(ErrorMessageDataEvent event) {
//                loadMoreListViewContainer.loadMoreError(0, event.message);
//            }
//
//        }).tryToRegisterIfNot();
    }

    private void initPtr() {
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mScrollView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.refreshComplete();
                    }
                }, 100);
            }
        });
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                //mPtrFrame.autoRefresh();
                Toast.makeText(HomeFragment.this.getActivity(), "Str start", Toast.LENGTH_SHORT).show();
            }
        }, 100);
    }

    private void initListView() {
        mAdapter = new TopicAdapter(this.getActivity(), data);
        mListView.setAdapter(mAdapter);
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            TopicMessage msg = new TopicMessage("标题", "这是一个内容,tv_views,tv_views,tv_views,tv_views,tv_views,tv_views", "独步清风", "12小时前", "5条回复");
            msg.setIcon_url("http://pic1.nipic.com/2008-08-12/200881211331729_2.jpg");
            data.add(msg);
        }
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        imageIdList = new ArrayList<Integer>();
        imageIdList.add(R.mipmap.banner1);
        imageIdList.add(R.mipmap.banner2);
        imageIdList.add(R.mipmap.banner3);
        imageIdList.add(R.mipmap.banner4);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (SystemUtils.getScreen(this.getActivity()).heightPixels / 4));
        viewPager.setLayoutParams(params);
        viewPager.setAdapter(new ImagePagerAdapter(this.getActivity(), imageIdList).setInfiniteLoop(true));
        //setOnPageChangeListener过期方法，被addOnPageChangeListener、removeOnPageChangeListener代替
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());

        viewPager.setInterval(2000);
        viewPager.startAutoScroll();
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % ListUtils.getSize(imageIdList));
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

}
