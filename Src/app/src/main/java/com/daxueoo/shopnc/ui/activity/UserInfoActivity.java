package com.daxueoo.shopnc.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.ui.fragment.QaFragment;
import com.daxueoo.shopnc.ui.fragment.UserFragment;
import com.daxueoo.shopnc.ui.fragment.UserInfoFragment;
import com.daxueoo.shopnc.utils.TitleBuilder;

/**
 * Created by user on 15-8-31.
 */
public class UserInfoActivity extends BaseFragmentActivity implements View.OnClickListener {

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        UserId = getIntent().getStringExtra("uid");
        initView();

    }

    /**
     * 初始化视图
     */
    private void initView() {
        new TitleBuilder(this).setTitleText(UserId +"的个人资料").setLeftImage(R.drawable.navigationbar_back_sel).setLeftOnClickListener(this);

        UserInfoFragment fragment = new UserInfoFragment();
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.container, fragment);
        trans.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                this.finish();
                break;
        }
    }
}
