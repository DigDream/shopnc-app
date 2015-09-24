package com.daxueoo.shopnc.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.ui.fragment.NoticeListFragment;
import com.daxueoo.shopnc.ui.fragment.UserFollowFragment;
import com.daxueoo.shopnc.utils.TitleBuilder;

/**
 * Created by guodont on 15/9/22.
 */
public class NoticeListActivity extends BaseFragmentActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        new TitleBuilder(this).setTitleText("公告资讯").setLeftImage(R.drawable.navigationbar_back_sel).setLeftOnClickListener(this);

        NoticeListFragment fragment = new NoticeListFragment();
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.container, fragment);
        trans.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.titlebar_iv_left:
                this.finish();
                break;
        }
    }
}
