package com.daxueoo.shopnc.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daxueoo.shopnc.MainActivity;
import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.ui.cn.xs.tool.demo.somiao.scan.CaptureActivity;
import com.daxueoo.shopnc.utils.SharedPreferencesUtils;

/**
 * Created by user on 15-8-2.
 */
public class BaseActivity extends Activity {
    private Button btn_scan;
    private TextView tv_scan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_scan = (Button) findViewById(R.id.btn_scan);
        tv_scan = (TextView) findViewById(R.id.tv_scan);
        btn_scan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                tv_scan.setText("");
                Intent intent = new Intent(BaseActivity.this,
                        CaptureActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }
    }


