package com.daxueoo.shopnc.ui.activity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.andreabaccega.formedittextvalidator.CreditCardValidator;
import com.andreabaccega.formedittextvalidator.EmailValidator;
import com.andreabaccega.formedittextvalidator.OrValidator;
import com.andreabaccega.widget.FormEditText;
import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.sdk.Shopnc;
import com.daxueoo.shopnc.utils.ConstUtils;

/**
 * Created by user on 15-8-2.
 */
public class LoginActivity extends BaseActivity {
    private FormEditText et_username;
    private FormEditText et_password;
    private Button btn_login;
    private String TAG = "LoginActivity";
    private TextView tv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewsById();
        initView();
    }

    private void initView() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormEditText[] allFields = {et_username, et_password};


                boolean allValid = true;
                for (FormEditText field : allFields) {
                    allValid = field.testValidity() && allValid;
                }

                if (allValid) {
                    // YAY
                    Log.e(TAG, "test" + et_username.getText().toString());
                    Log.e(TAG, "test" + et_password.getText().toString());
                    //发送post请求
                    Shopnc.login(LoginActivity.this, et_username.getText().toString(), et_password.getText().toString(), ConstUtils.CLIENT_TYPE);

                    //跳转Activity
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainTabActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e(TAG, et_username.getText().toString());
                    Log.e(TAG, et_password.getText().toString());
                    // EditText are going to appear with an exclamation mark and an explicative message.
                }

            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findViewsById() {
        et_username = (FormEditText) findViewById(R.id.et_username);
        et_password = (FormEditText) findViewById(R.id.et_password);

        tv_register = (TextView) findViewById(R.id.tv_register);
        btn_login = (Button) findViewById(R.id.btn_login);
    }

}
