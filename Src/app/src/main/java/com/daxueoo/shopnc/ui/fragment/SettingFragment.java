package com.daxueoo.shopnc.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.ui.activity.LoginActivity;
import com.daxueoo.shopnc.ui.activity.SettingActivity;
import com.daxueoo.shopnc.utils.SharedPreferencesUtils;

/**
 * Created by user on 15-8-2.
 */
public class SettingFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

    }
    //退出登录的design
    public  boolean  onPreferenceTreeClick (PreferenceScreen preferenceScreen,Preference preference){
        // 对控件进行操作
     if(preference.getKey().equals("tuichu")){

         // 创建一个新的Intent，
         // 函数如果返回true， 则跳转至该自定义的新的Intent ;
         // 函数如果返回false，则跳转至xml文件中配置的Intent ;
         Intent intent= new Intent();
         intent.setClass(getActivity(),LoginActivity.class);
    ;
         startActivity(intent);
         //修改工具类，添加了一个删除的方法，然后在这里调用即可
         SharedPreferencesUtils.clearParam(getActivity(),LoginActivity.class);


             return true;
     }
        return  false;
    }
}
