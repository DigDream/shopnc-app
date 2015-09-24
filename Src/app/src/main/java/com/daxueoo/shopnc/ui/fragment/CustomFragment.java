package com.daxueoo.shopnc.ui.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.model.ExploreMessage;
import com.daxueoo.shopnc.utils.SharedPreferencesUtils;

/**
 * Created by guodont on 15/9/13.
 */
public class CustomFragment extends PreferenceFragment implements CheckBox.OnCheckedChangeListener {
    private View view = null;
    private CheckBox cb_explore_my_order,cb_explore_trade,cb_explore_my_message,cb_explore_my_task;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_custom, null);
        cb_explore_my_order = (CheckBox) view.findViewById(R.id.cb_explore_my_order);
        cb_explore_trade = (CheckBox) view.findViewById(R.id.cb_explore_trade);

        cb_explore_my_message = (CheckBox) view.findViewById(R.id.cb_explore_my_message);

        cb_explore_my_task = (CheckBox) view.findViewById(R.id.cb_explore_my_task);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cb_explore_my_order.setOnCheckedChangeListener(this);
        cb_explore_trade.setOnCheckedChangeListener(this);
        cb_explore_my_message.setOnCheckedChangeListener(this);
        cb_explore_my_task.setOnCheckedChangeListener(this);

        initCheckBox();
    }

    private void initCheckBox() {
        cb_explore_my_order.setChecked((Boolean) SharedPreferencesUtils.getParam(CustomFragment.this.getActivity(), "cb_explore_my_order", true));
        cb_explore_trade.setChecked((Boolean) SharedPreferencesUtils.getParam(CustomFragment.this.getActivity(), "cb_explore_trade", true));
        cb_explore_my_message.setChecked((Boolean) SharedPreferencesUtils.getParam(CustomFragment.this.getActivity(), "cb_explore_my_message", true));
        cb_explore_my_task.setChecked((Boolean) SharedPreferencesUtils.getParam(CustomFragment.this.getActivity(), "cb_explore_my_task", true));
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.cb_explore_my_order:
                saveCustom("cb_explore_my_order", cb_explore_my_order.isChecked());
                break;
            case R.id.cb_explore_trade:
                saveCustom("cb_explore_trade", cb_explore_trade.isChecked());
                break;

            case R.id.cb_explore_my_message:
                saveCustom("cb_explore_my_message", cb_explore_my_message.isChecked());
                break;

            case R.id.cb_explore_my_task:
                saveCustom("cb_explore_my_task", cb_explore_my_task.isChecked());
                break;

        }
    }

    public void saveCustom(String name, Boolean value) {
        SharedPreferencesUtils.setParam(CustomFragment.this.getActivity(), name, value);
    }
}
