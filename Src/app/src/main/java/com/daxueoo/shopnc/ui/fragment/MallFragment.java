package com.daxueoo.shopnc.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.utils.ConstUtils;
import com.daxueoo.shopnc.utils.TitleBuilder;
import com.daxueoo.shopnc.utils.ToastUtils;

/**
 * 这是商城的Fragment，目前用WebView代替
 */

public class MallFragment extends BaseFragment {

    private String TAG = "MallFragment";
    private View view;

    WebView webView;

    private String url = "http://daxueoo.com:8080/wap";
    private TextView tv_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(activity, R.layout.fragment_mall, null);

        new TitleBuilder(view).setTitleText("Message").setRightImage(R.mipmap.ic_launcher).setRightOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(activity, "right click~", Toast.LENGTH_SHORT);
            }
        });

        tv_title = (TextView) view.findViewById(R.id.titlebar_tv);
        webView = (WebView) view.findViewById(R.id.webview);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_title.setText(R.string.tab_tv_mall);
        initWebView();

    }

    private void initWebView() {
        //设置WebView属性，能够执行Javascript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的网页
        webView.loadUrl(url);
        //设置Web视图
        webView.setWebViewClient(new MyWebViewClient());
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
                Log.e(TAG,url);
            }
        }
    }
}
