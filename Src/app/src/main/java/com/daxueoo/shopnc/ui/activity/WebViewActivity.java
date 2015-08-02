package com.daxueoo.shopnc.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.utils.ConstUtils;

/**
 * Created by user on 15-8-2.
 */
public class WebViewActivity extends BaseActivity {
    private WebView webview;
    private String url;

    private String type;

    private String TAG = "WebViewActivity";
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        type = intent.getStringExtra("type");
        webview = (WebView) findViewById(R.id.webview);
        relativeLayout = (RelativeLayout) findViewById(R.id.rl_titlebar);
        initWebView();
    }

    //Web视图
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (url != null) {
                Log.e(TAG, view.getTitle());
                Log.e(TAG, url);
            }
        }
    }

    private void initWebView() {
        //设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的网页
        webview.loadUrl(url);
        //设置Web视图
        webview.setWebViewClient(new MyWebViewClient());

        switch (type) {
            case ConstUtils.web_type_mall:
                relativeLayout.setVisibility(View.GONE);
                Log.e(TAG, "test");
                break;
            case ConstUtils.web_type_circle:
                Log.e(TAG, type);
                break;
            default:

        }
    }

    @Override
    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        Intent intent = new Intent();
        intent.setClass(WebViewActivity.this, MainTabActivity.class);
        startActivity(intent);
        finish();
        return false;
    }
}
