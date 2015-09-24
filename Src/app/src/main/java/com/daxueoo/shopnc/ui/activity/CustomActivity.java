package com.daxueoo.shopnc.ui.activity;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.utils.TitleBuilder;

public class CustomActivity extends BaseFragmentActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        new TitleBuilder(this).setTitleText("在首页显示").setLeftImage(R.drawable.navigationbar_back_sel).setLeftOnClickListener(this);

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
