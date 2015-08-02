package com.daxueoo.shopnc.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.daxueoo.shopnc.R;

/**
 * Created by user on 15-8-2.
 */
public class SettingActivity extends Activity {
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        tv_title = (TextView) findViewById(R.id.titlebar_tv);
        tv_title.setText(R.string.tv_setting);
    }
}
