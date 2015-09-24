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
import com.daxueoo.shopnc.utils.TitleBuilder;

/**
 * 关于WebView的Activity，商城，圈子等
 * Created by user on 15-8-2.
 */
public class WebViewActivity extends BaseActivity implements View.OnClickListener{

    private String TAG = "WebViewActivity";
    private WebView webview;
    private String url;
    private String type;
    TitleBuilder titleBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        titleBuilder = new TitleBuilder(WebViewActivity.this).setLeftImage(R.drawable.navigationbar_back_sel).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewActivity.this.finish();
            }
        });
        Intent intent = getIntent();
        //  获取传入的参数url和类型
        url = intent.getStringExtra("url");
        type = intent.getStringExtra("type");

        webview = (WebView) findViewById(R.id.webview);
        initWebView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                WebViewActivity.this.finish();
                break;
        }
    }

    /**
     * WebViewClient
     */
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
                titleBuilder.setTitleText(view.getTitle());
            }
        }
    }

    /**
     * 初始化WebView视图
     */
    private void initWebView() {
        //  设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        //  加载需要显示的网页
        webview.loadUrl(url);
        //  设置Web视图
        webview.setWebViewClient(new MyWebViewClient());

        //  判断类型
        switch (type) {
            case ConstUtils.WEB_TYPE_MALL:

                break;
            case ConstUtils.WEB_TYPE_CIRCLE:
                Log.e(TAG, type);
                break;
            default:

        }
    }

    @Override
    /**
     * 设置回退,覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        WebViewActivity.this.finish();
        return false;
    }
}
