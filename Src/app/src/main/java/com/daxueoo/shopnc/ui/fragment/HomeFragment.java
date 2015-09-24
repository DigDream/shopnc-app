package com.daxueoo.shopnc.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daxueoo.shopnc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是首页的Fragment，主要有滚动图片，ListView，Button等
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private String TAG = "HomeFragment";

    //  Header标题
    private TextView tv_title;


    private ViewPager viewPager;

    private List<Fragment> mFragmentList = new ArrayList<Fragment>();

    /**
     * Fragment
     */
    private HomeIndexFragment homeIndexFragment = new HomeIndexFragment();
    private HomeTrendsFragment homeTrendsFragment = new HomeTrendsFragment();

    /**
     * ViewPager的当前选中页
     */
    private int currentIndex;
    private boolean isFirst = true;
    private ImageView iv_center;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = View.inflate(activity, R.layout.fragment_home, null);

        tv_title = (TextView) view.findViewById(R.id.titlebar_tv);
        iv_center = (ImageView) view.findViewById(R.id.titlebar_iv_center);

        viewPager = (ViewPager) view.findViewById(R.id.id_page_vp);

        Log.e(TAG, "onCreateView");

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_title.setVisibility(View.GONE);
        iv_center.setImageResource(R.mipmap.top_home_02);
        iv_center.setVisibility(View.VISIBLE);

        init();
        initImageView();

//        if (isFirst) {
//            init();
//            isFirst = false;
//        }

    }

    private void initImageView() {
        iv_center.setOnClickListener(this);
    }

    private void init() {
        Log.e(TAG, "init");
        FragmentManager fm = getChildFragmentManager();

        viewPager.setAdapter(new FragmentStatePagerAdapter(fm) {


            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return 2;
            }

            @Override
            public Fragment getItem(int pos) {
                // TODO Auto-generated method stub
                Fragment fragment;
                switch (pos) {

                    case 0:
                        fragment = new HomeIndexFragment();
                        return fragment;
                    case 1:
                        fragment = new HomeTrendsFragment();
                        return fragment;
                    default:
                        fragment = new HomeIndexFragment();
                        return fragment;
                }

            }
        });
        viewPager.setCurrentItem(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
             */
            @Override
            public void onPageScrollStateChanged(int state) {
            }

            /**
             * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比
             * offsetPixels:当前页面偏移的像素位置
             */
            @Override
            public void onPageScrolled(int position, float offset, int offsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        iv_center.setImageResource(R.mipmap.top_home_02);
                        break;
                    case 1:
                        iv_center.setImageResource(R.mipmap.top_home_01);
                        break;
                    case 2:
                        break;
                }
                currentIndex = position;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.titlebar_iv_center:
                if (viewPager.getCurrentItem() == 0){
                    viewPager.setCurrentItem(1);
                }else {
                    viewPager.setCurrentItem(0);
                }

                break;

        }


    }
}
