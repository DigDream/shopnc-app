package com.daxueoo.shopnc.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.model.UserMessage;
import com.daxueoo.shopnc.ui.activity.SettingActivity;
import com.daxueoo.shopnc.utils.SharedPreferencesUtils;
import com.daxueoo.shopnc.utils.SystemUtils;
import com.daxueoo.shopnc.utils.TitleBuilder;
import com.daxueoo.shopnc.utils.ToastUtils;
import com.daxueoo.shopnc.widgets.pulltozoom.PullToZoomScrollViewEx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserFragment extends BaseFragment {

    private View view;
    private ListView mListView;
    private List<UserMessage> list = makeList();
    private PullToZoomScrollViewEx scrollView;
    private TextView mUsername;

    private List<UserMessage> makeList() {
        List<UserMessage> l = new ArrayList<UserMessage>();
        int i = 0;
        UserMessage p1 = new UserMessage();
        p1.setId("id" + i);
        i = i + 1;
        p1.setName("无线弱口令密码检测");
        l.add(p1);

        UserMessage p2 = new UserMessage();
        p2.setId("id" + i);
        i = i + 1;
        p2.setName("无线路由器管理地址检测");
        l.add(p2);

        UserMessage p3 = new UserMessage();
        p3.setId("id" + i);
        i = i + 1;
        p3.setName("路由器管理密码检测");
        l.add(p3);

        UserMessage p4 = new UserMessage();
        p4.setId("id" + i);
        i = i + 1;
        p4.setName("DNS服务器地址管理");
        l.add(p4);

        return l;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(activity, R.layout.fragment_user, null);

        new TitleBuilder(view).setTitleText("我的").setRightImage(R.mipmap.ic_launcher).setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(activity, "right click~", Toast.LENGTH_SHORT);
                Intent intent = new Intent();
                intent.setClass(UserFragment.this.getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });

        scrollView = (PullToZoomScrollViewEx) view.findViewById(R.id.scroll_view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initPulltozoom();
        initListView();
    }

    private void initPulltozoom() {
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadViewForCode();

        int mScreenHeight = SystemUtils.getScreen(activity).heightPixels;
        int mScreenWidth = SystemUtils.getScreen(activity).widthPixels;
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 16.0F)));
        scrollView.setHeaderLayoutParams(localObject);
    }

    private void loadViewForCode() {
        View headView = LayoutInflater.from(this.getActivity()).inflate(R.layout.profile_head_view, null, false);
        View zoomView = LayoutInflater.from(this.getActivity()).inflate(R.layout.profile_zoom_view, null, false);
        View contentView = LayoutInflater.from(this.getActivity()).inflate(R.layout.profile_content_view, null, false);
        scrollView.setScrollContentView(contentView);
        scrollView.setHeaderView(headView);
        scrollView.setZoomView(zoomView);

        mUsername = (TextView) headView.findViewById(R.id.tv_user_name);
        mUsername.setText((String) SharedPreferencesUtils.getParam(this.getActivity(), "username", "dubuqingfeng"));
        mListView = (ListView) contentView.findViewById(R.id.list_fragment_user);
    }

    private void initListView() {
        ArrayList<HashMap<String, String>> myArrayList = new ArrayList<HashMap<String, String>>();

        for (UserMessage p : list) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id", p.getId());
            map.put("name", p.getName());
            myArrayList.add(map);
        }

        SimpleAdapter mySimpleAdapter = new SimpleAdapter(this.getActivity(), myArrayList,// 数据源
                R.layout.item_list_user, new String[]{"id", "name"},// HashMap中的两个key值
                // itemTitle和itemContent
                new int[]{R.id.itemID, R.id.tv_left_title});

        mListView.setAdapter(mySimpleAdapter);

        SystemUtils.setListViewHeightBasedOnChildren(mListView);
    }
}
