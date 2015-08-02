package com.daxueoo.shopnc.ui.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.daxueoo.shopnc.R;

/**
 * Created by user on 15-8-2.
 */
public class SettingFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
