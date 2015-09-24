package com.daxueoo.shopnc.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.utils.TitleBuilder;

/**
 * Created by user on 15-8-2.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        new TitleBuilder(this).setTitleText("设置").setLeftImage(R.drawable.navigationbar_back_sel).setLeftOnClickListener(this);

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
