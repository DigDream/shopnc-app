package com.daxueoo.shopnc.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.widget.RadioGroup;
import android.widget.TabHost;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.ui.fragment.CircleFragment;
import com.daxueoo.shopnc.ui.fragment.HomeFragment;
import com.daxueoo.shopnc.ui.fragment.MallFragment;
import com.daxueoo.shopnc.ui.fragment.TradeFragment;
import com.daxueoo.shopnc.ui.fragment.UserFragment;

/**
 * Created by user on 15-8-2.
 */
public class MainTabActivity extends FragmentActivity {

    private String TAG = "MainTabActivity";

    // 定义FragmentTabHost对象
    private FragmentTabHost mTabHost;
    private RadioGroup mTabRg;

    private final Class[] fragments = {HomeFragment.class, MallFragment.class, TradeFragment.class, CircleFragment.class, UserFragment.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        initView();
    }

    private void initView() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.fl_content);
        // 得到fragment的个数
        int count = fragments.length;
        for (int i = 0; i < count; i++) {
            // 为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(i + "").setIndicator(i + "");
            // 将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragments[i], null);
        }

        mTabRg = (RadioGroup) findViewById(R.id.rg_tab);
        mTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        mTabHost.setCurrentTab(0);
                        break;
                    case R.id.rb_mall:
                        mTabHost.setCurrentTab(1);
                        break;
                    case R.id.rb_circle:
                        mTabHost.setCurrentTab(2);
                        break;
                    case R.id.rb_trade:
                        mTabHost.setCurrentTab(3);
                        break;
                    case R.id.rb_user:
                        mTabHost.setCurrentTab(4);
                        break;

                    default:
                        break;
                }
            }
        });

        mTabHost.setCurrentTab(0);
    }
}
