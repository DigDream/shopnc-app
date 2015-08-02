package com.daxueoo.shopnc.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.daxueoo.shopnc.ui.activity.MainTabActivity;

/**
 * Created by user on 15-8-2.
 */
public class BaseFragment extends Fragment {

    protected MainTabActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (MainTabActivity) getActivity();
    }

    protected void intent2Activity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(activity, tarActivity);
        startActivity(intent);
    }
}