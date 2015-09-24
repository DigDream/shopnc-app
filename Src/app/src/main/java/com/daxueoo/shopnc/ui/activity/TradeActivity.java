package com.daxueoo.shopnc.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.ui.fragment.TradeListFragment;
import com.daxueoo.shopnc.utils.TitleBuilder;

/**
 * Created by guodont on 15/9/10.
 */
public class TradeActivity extends BaseFragmentActivity implements View.OnClickListener {


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
        new TitleBuilder(this).setTitleText("交易列表")
                .setLeftImage(R.drawable.navigationbar_back_sel).setLeftOnClickListener(this);
        TradeListFragment fragment = new TradeListFragment();
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
