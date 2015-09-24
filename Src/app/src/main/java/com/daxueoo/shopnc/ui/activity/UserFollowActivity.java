package com.daxueoo.shopnc.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.ui.fragment.UserFollowFragment;
import com.daxueoo.shopnc.utils.TitleBuilder;

/**
 * Created by user on 15-9-1.
 * 用户粉丝和关注的Activity
 */
public class UserFollowActivity extends BaseFragmentActivity implements View.OnClickListener{
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    String type;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        Intent intent = getIntent();
        type = intent.getStringExtra("Type");
        uid = intent.getStringExtra("uid");
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        new TitleBuilder(this).setTitleText(type).setLeftImage(R.drawable.navigationbar_back_sel).setLeftOnClickListener(this);

        UserFollowFragment fragment = new UserFollowFragment();
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
