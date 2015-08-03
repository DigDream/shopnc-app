package com.daxueoo.shopnc.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.adapter.CircleAdapter;
import com.daxueoo.shopnc.model.CircleMessage;
import com.daxueoo.shopnc.model.TopicMessage;
import com.daxueoo.shopnc.utils.TitleBuilder;
import com.daxueoo.shopnc.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by user on 15-8-2.
 */
public class CircleFragment extends BaseFragment{
    private View view;
    private CircleAdapter mAdapter;
    private ListView mListView;

    private List<CircleMessage> data = new ArrayList<CircleMessage>();
    private PtrClassicFrameLayout mPtrFrame;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = View.inflate(activity, R.layout.fragment_circle, null);

        new TitleBuilder(view).setTitleText("圈子").setRightImage(R.mipmap.ic_launcher).setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(activity, "right click~", Toast.LENGTH_SHORT);
            }
        });

        mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_web_view_frame);

        mListView = (ListView) view.findViewById(R.id.list_fragment_circle);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListView();
        initData();
        initPtr();
    }

    private void initPtr() {
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mListView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.refreshComplete();
                    }
                }, 100);
            }
        });
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                //mPtrFrame.autoRefresh();
                Toast.makeText(CircleFragment.this.getActivity(), "Str start", Toast.LENGTH_SHORT).show();
            }
        }, 100);
    }

    private void initListView() {
        mAdapter = new CircleAdapter(this.getActivity(), data);
        mListView.setAdapter(mAdapter);
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            CircleMessage msg = new CircleMessage("http://pic1.nipic.com/2008-08-12/200881211331729_2.jpg","标题", "这是一个内容,tv_views,tv_viewsws", "http://pic1.nipic.com/2008-08-12/200881211331729_2.jpg", "12小时前", "5条回复");
            data.add(msg);
        }
    }
}
