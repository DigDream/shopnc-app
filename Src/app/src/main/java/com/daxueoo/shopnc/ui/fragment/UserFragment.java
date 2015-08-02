package com.daxueoo.shopnc.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.ui.activity.SettingActivity;
import com.daxueoo.shopnc.utils.TitleBuilder;
import com.daxueoo.shopnc.utils.ToastUtils;

public class UserFragment extends BaseFragment {

    private View view;

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

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
