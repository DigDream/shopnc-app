package com.daxueoo.shopnc.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.bean.Task;
import com.daxueoo.shopnc.cache.ImageCacheManger;
import com.daxueoo.shopnc.model.TradeMessage;
import com.daxueoo.shopnc.utils.SystemUtils;
import com.daxueoo.shopnc.utils.TitleBuilder;
import com.daxueoo.shopnc.utils.ToastUtils;

public class TradeDetailActivity extends BaseActivity implements View.OnClickListener{

    private TextView tv_trade_title;
    private TextView tv_trade_desc;
    private TextView tv_trade_username;
    private String trade_user_id;
    private ImageView iv_trade_img;
    private Button btn_tel;
    private TradeMessage trade;
    private String tradePhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_detail);
        new TitleBuilder(this).setTitleText("交易详情").setLeftImage(R.drawable.navigationbar_back_sel).setLeftOnClickListener(this);
        trade = (TradeMessage) getIntent().getSerializableExtra("trade");
        Log.e("tags", trade.getTrade_title());

        initView();
        initData();
    }

    private void initData() {
        tv_trade_title.setText(trade.getTrade_title());
        tv_trade_desc.setText(trade.getTrade_desc());
//        tv_trade_username.setText(trade.getTrade_username());
        trade_user_id = trade.getTrade_user_id();
        tradePhone = trade.getTrade_pphone();
        ImageCacheManger.loadImage(trade.getTrade_img(), iv_trade_img, SystemUtils.getBitmapFromResources(TradeDetailActivity.this, R.mipmap.ic_launcher), SystemUtils.getBitmapFromResources(TradeDetailActivity.this, R.mipmap.ic_launcher));

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void initView() {
        tv_trade_title = (TextView) findViewById(R.id.tv_trade_title);
        tv_trade_desc = (TextView) findViewById(R.id.tv_trade_desc);
        iv_trade_img = (ImageView) findViewById(R.id.iv_trade_img);
        btn_tel = (Button) findViewById(R.id.btn_tel);
        btn_tel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                this.finish();
                break;
            case R.id.btn_tel:
                Intent phoneIntent = new Intent(Intent.ACTION_CALL, Uri.parse(tradePhone));
                try{
                    startActivity(phoneIntent);
                }
                catch (android.content.ActivityNotFoundException ex){
                    ToastUtils.showToast(this,"启动拨号失败", Toast.LENGTH_SHORT);
                }
                break;
        }
    }
}
