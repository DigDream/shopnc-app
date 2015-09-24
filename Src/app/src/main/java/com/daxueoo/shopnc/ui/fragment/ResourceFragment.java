package com.daxueoo.shopnc.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.adapter.ResourceAdapter;
import com.daxueoo.shopnc.model.CircleMessage;
import com.daxueoo.shopnc.model.ExploreMessage;
import com.daxueoo.shopnc.ui.activity.CustomActivity;
import com.daxueoo.shopnc.utils.TitleBuilder;
import com.daxueoo.shopnc.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ResourceFragment extends BaseFragment {

    private CharSequence mTitle;

    private View view;

    private ResourceAdapter mAdapter;

    private GridView gridView;
    private List<ExploreMessage> data = new ArrayList<ExploreMessage>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(activity, R.layout.fragment_trade, null);

        new TitleBuilder(view).setTitleText(getString(R.string.tab_tv_trade)).setRightImage(R.drawable.icon_menu_normal).setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(getActivity(), "自定义", Toast.LENGTH_SHORT);
                Intent intent = new Intent();
                intent.setClass(ResourceFragment.this.getActivity(), CustomActivity.class);
                startActivity(intent);

            }
        });
        //GridView
        gridView = (GridView) view.findViewById(R.id.gridview);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initGridView();
    }

    /**
     * 初始化数据
     */
    private void initData() {


        data.clear();

        ExploreMessage msg7 = new ExploreMessage();
        msg7.setExplore_name("扫码识物");
        msg7.setIcon_res(R.drawable.icon_explore_task_pressed);
        data.add(msg7);

        ExploreMessage msg2 = new ExploreMessage();
        msg2.setExplore_name("我的任务");
        msg2.setIcon_res(R.drawable.icon_explore_task_pressed);
        data.add(msg2);

        ExploreMessage msg = new ExploreMessage();
        msg.setExplore_name("交易大厅");
        msg.setIcon_res(R.drawable.icon_explore_trade_pressed);
        data.add(msg);

        ExploreMessage msg3 = new ExploreMessage();
        msg3.setExplore_name("我的订单");
        msg3.setIcon_res(R.drawable.icon_explore_order_pressed);
        data.add(msg3);

        ExploreMessage msg4 = new ExploreMessage();
        msg4.setExplore_name("消息中心");
        msg4.setIcon_res(R.drawable.icon_explore_message_pressed);
        data.add(msg4);

        ExploreMessage msg6 = new ExploreMessage();
        msg6.setExplore_name("问专家");
        msg6.setIcon_res(R.drawable.icon_explore_qa_zj);
        data.add(msg6);

        ExploreMessage msg5 = new ExploreMessage();
        msg5.setExplore_name("问达人");
        msg5.setIcon_res(R.drawable.icon_explore_qa_dr);
        data.add(msg5);

        ExploreMessage msg8 = new ExploreMessage();
        msg8.setExplore_name("资讯");
        msg8.setIcon_res(R.drawable.icon_explore_notice);
        data.add(msg8);


    }

    /**
     * 初始化GridView
     */
    private void initGridView() {
        mAdapter = new ResourceAdapter(this.getActivity(), data);
        gridView.setAdapter(mAdapter);
    }

    public void setTitle(CharSequence title) {
        mTitle = title;
    }


}
