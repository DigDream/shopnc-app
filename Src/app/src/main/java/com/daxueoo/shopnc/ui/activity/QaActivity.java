package com.daxueoo.shopnc.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.ui.fragment.QaFragment;
import com.daxueoo.shopnc.utils.TitleBuilder;

/**
 * Created by user on 15-8-31.
 */
public class QaActivity extends BaseFragmentActivity implements View.OnClickListener{

    public String getCircleId() {
        return qaCircleId;
    }

    public void setCircleId(String circleId) {
        this.qaCircleId = circleId;
    }

    String qaCircleId;

    public String getCircleTitle() {
        return qaCircleTitle;
    }

    public void setCircleTitle(String circleTitle) {
        this.qaCircleTitle = circleTitle;
    }

    public String getQaType() {
        return qaType;
    }

    public void setQaType(String qaType) {
        this.qaType = qaType;
    }

    String qaCircleTitle;

    String qaType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        Intent intent = getIntent();
        qaCircleId = intent.getStringExtra("QaCircleId");
        qaCircleTitle = intent.getStringExtra("QaCircleName");
        qaType = intent.getStringExtra("QaType");
        initView();

    }

    /**
     * 初始化视图
     */
    private void initView() {
        new TitleBuilder(this).setTitleText(qaCircleTitle).setLeftImage(R.drawable.navigationbar_back_sel).setLeftOnClickListener(this);

        QaFragment fragment = new QaFragment();
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
