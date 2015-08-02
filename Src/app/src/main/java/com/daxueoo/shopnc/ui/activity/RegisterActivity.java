package com.daxueoo.shopnc.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.daxueoo.shopnc.R;

/**
 * Created by user on 15-8-2.
 */
public class RegisterActivity extends BaseActivity {

    private EditText et_username;
    private EditText et_password;
    private Button btn_login;
    private String TAG = "RegisterActivity";
    private TextView tv_title;
    private EditText et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViewsById();
        initView();
    }

    private void initView() {
        tv_title.setText(R.string.btn_register);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, et_username.getText().toString());
                Log.e(TAG, et_password.getText().toString());
                Log.e(TAG, et_email.getText().toString());
                //发送post请求

                //跳转Activity
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this, MainTabActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void findViewsById() {
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_email = (EditText) findViewById(R.id.et_email);

        tv_title = (TextView) findViewById(R.id.titlebar_tv);
        btn_login = (Button) findViewById(R.id.btn_login);
    }
}
