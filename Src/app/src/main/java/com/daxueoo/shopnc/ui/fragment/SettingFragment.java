package com.daxueoo.shopnc.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.ui.activity.AboutActivity;
import com.daxueoo.shopnc.ui.activity.LoginActivity;
import com.daxueoo.shopnc.utils.SharedPreferencesUtils;

/**
 * 设置的fragment
 * Created by user on 15-8-2.
 */
public class SettingFragment extends PreferenceFragment implements View.OnClickListener{

    private View view = null;
    private Button btn_exit;
    private RelativeLayout rl_about;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_setting, null);

        btn_exit = (Button)view.findViewById(R.id.loginOut);
        rl_about = (RelativeLayout) view.findViewById(R.id.about);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_exit.setOnClickListener(this);
        rl_about.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.loginOut:
                SharedPreferencesUtils.setParam(SettingFragment.this.getActivity(), "key", "");
                SharedPreferencesUtils.setParam(SettingFragment.this.getActivity(),"username","");
                intent.setClass(SettingFragment.this.getActivity(), LoginActivity.class);
                SettingFragment.this.getActivity().startActivity(intent);
                SettingFragment.this.getActivity().finish();
                break;
            case R.id.about:
                Log.e("ABOUT","enter");
                intent.setClass(SettingFragment.this.getActivity(), AboutActivity.class);
                SettingFragment.this.getActivity().startActivity(intent);
                break;
        }
    }
}
