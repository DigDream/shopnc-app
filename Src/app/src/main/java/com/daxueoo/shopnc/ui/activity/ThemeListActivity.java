package com.daxueoo.shopnc.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.ui.fragment.CircleDetailFragment;
import com.daxueoo.shopnc.utils.TitleBuilder;
import com.daxueoo.shopnc.utils.ToastUtils;

/**
 * Created by user on 15-8-24.
 */
public class ThemeListActivity extends BaseFragmentActivity implements View.OnClickListener{

    public String getCircleId() {
        return circleId;
    }

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }

    String circleId;

    public String getCircleTitle() {
        return circleTitle;
    }

    public void setCircleTitle(String circleTitle) {
        this.circleTitle = circleTitle;
    }

    String circleTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        Intent intent = getIntent();
        circleId = intent.getStringExtra("CircleId");
        circleTitle = intent.getStringExtra("CircleName");
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {

        CircleDetailFragment fragment = new CircleDetailFragment();
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
