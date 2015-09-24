package com.daxueoo.shopnc.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.ui.fragment.BaseFragment;
import com.daxueoo.shopnc.ui.fragment.CircleDetailFragment;
import com.daxueoo.shopnc.ui.fragment.CommonDetailFragment;
import com.daxueoo.shopnc.ui.fragment.ThemeDetailFragment;

/**
 * 详情的Activity，例如话题详情、圈子详情、资源详情、
 * 通过不同的
 * Created by user on 15-8-13.
 */
public class DetailActivity extends BaseFragmentActivity {

    //  话题、圈子
    public static final int DISPLAY_THEME = 0;
    public static final int DISPLAY_CIRCLE = 1;


    public static final String BUNDLE_KEY_DISPLAY_TYPE = "BUNDLE_KEY_DISPLAY_TYPE";
    private int displayType;
    private String detailId;

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getCircleId() {
        return circleId;
    }

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }

    private String circleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //  获取类型
        displayType = getIntent().getIntExtra(BUNDLE_KEY_DISPLAY_TYPE, DISPLAY_THEME);
        detailId = getIntent().getStringExtra("detailId");

        circleId = getIntent().getStringExtra("circleId");

        initView();
    }

    private void initView() {

        CommonDetailFragment fragment = null;
        int actionBarTitle = 0;
        switch (displayType) {
            case DISPLAY_THEME:
                //  设置标题
                fragment = new ThemeDetailFragment();
                break;
            case DISPLAY_CIRCLE:
                fragment = new CircleDetailFragment();
                break;
        }

        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.container, fragment);
        trans.commitAllowingStateLoss();

    }
}
