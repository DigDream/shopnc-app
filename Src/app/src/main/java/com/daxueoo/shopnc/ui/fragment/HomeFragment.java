package com.daxueoo.shopnc.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.adapter.ImagePagerAdapter;
import com.daxueoo.shopnc.adapter.TopicAdapter;
import com.daxueoo.shopnc.model.TopicMessage;
import com.daxueoo.shopnc.utils.ListUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(activity, R.layout.fragment_home, null);

        //滚动图片
        viewPager = (AutoScrollViewPager) view.findViewById(R.id.view_pager);
        tv_title = (TextView) view.findViewById(R.id.titlebar_tv);

        mListView = (ListView) view.findViewById(R.id.list_fragment_topic);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_title.setText(R.string.tab_tv_index);
        initViewPager();
        initListView();
        initData();
    }

    private void initListView() {
        mAdapter = new TopicAdapter(this.getActivity(), data);
        mListView.setAdapter(mAdapter);
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            TopicMessage msg = new TopicMessage("标题", "这是一个内容,tv_views,tv_views,tv_views,tv_views,tv_views,tv_views","独步清风","12小时前","5条回复");
            msg.setIcon_url("https://cdn.v2ex.co/avatar/7b5a/e206/89357_large.png");
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
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (getScreen(this.getActivity()).heightPixels / 4));
        viewPager.setLayoutParams(params);
        viewPager.setAdapter(new ImagePagerAdapter(this.getActivity(), imageIdList).setInfiniteLoop(true));
        //setOnPageChangeListener过期方法，被addOnPageChangeListener、removeOnPageChangeListener代替
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());

        viewPager.setInterval(2000);
        viewPager.startAutoScroll();
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % ListUtils.getSize(imageIdList));

    }

    /**
     * 获取屏幕宽高
     *
     * @param activity
     * @return
     */
    public DisplayMetrics getScreen(Activity activity) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
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
