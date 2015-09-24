package com.daxueoo.shopnc.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导的Activity
 * Created by user on 15-8-12.
 */
public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private List<View> views;

    // 底部小点图片
    private ImageView[] dots;

    // 记录当前选中位置
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_guide);

        //  初始化页面
        initViews();

        //  初始化底部小点
        initDots();
    }

    /**
     * 初始化视图
     */
    private void initViews() {
        LayoutInflater inflater = LayoutInflater.from(this);

        views = new ArrayList<View>();
        //  初始化引导图片列表
        views.add(inflater.inflate(R.layout.guide_one, null));
        views.add(inflater.inflate(R.layout.guide_two, null));
        views.add(inflater.inflate(R.layout.guide_three, null));
        views.add(inflater.inflate(R.layout.guide_four, null));
        views.add(inflater.inflate(R.layout.guide_five, null));

        //  初始化Adapter
        viewPagerAdapter = new ViewPagerAdapter(views, this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(viewPagerAdapter);
        //  绑定回调
        viewPager.addOnPageChangeListener(this);
    }

    /**
     * 初始化小点
     */
    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

        dots = new ImageView[views.size()];

        //  循环取得小点图片
        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(true);// 都设为灰色
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态
    }

    /**
     * 设定小点的选定状态
     * @param position
     */
    private void setCurrentDot(int position) {
        if (position < 0 || position > views.size() - 1 || currentIndex == position) {
            return;
        }

        dots[position].setEnabled(false);
        dots[currentIndex].setEnabled(true);

        currentIndex = position;
    }

    /**
     * 当滑动状态改变时调用
     *
     * @param arg0
     */
    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    /**
     * 当当前页面被滑动时调用
     *
     * @param arg0
     * @param arg1
     * @param arg2
     */
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    /**
     * 当新的页面被选中时调用
     *
     * @param arg0
     */
    @Override
    public void onPageSelected(int arg0) {
        // 设置底部小点选中状态
        setCurrentDot(arg0);
    }
}
