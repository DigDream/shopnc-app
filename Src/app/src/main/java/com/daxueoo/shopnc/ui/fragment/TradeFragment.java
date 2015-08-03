package com.daxueoo.shopnc.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.utils.TitleBuilder;
import com.daxueoo.shopnc.utils.ToastUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class TradeFragment extends BaseFragment {

    private View view;
    private SlidingMenu menu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(activity, R.layout.fragment_trade, null);

        new TitleBuilder(view).setTitleText("交易").setRightImage(R.mipmap.ic_launcher).setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(activity, "right click~", Toast.LENGTH_SHORT);
                menu.toggle();
            }
        });

        atachLeftMenu(getActivity());

        return view;
    }

    public void atachLeftMenu(Activity pActivity){
        menu = new SlidingMenu(pActivity);
        menu.setMode(SlidingMenu.RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.25f);
        menu.attachToActivity(pActivity, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.menu);
        // ( (ListView)(menu.findViewById(R.id.side_menu_list)) ).setAdapter(new leftMenuAdapter(pActivity));
        // ( (ListView)(menu.findViewById(R.id.side_menu_list)) ).setOnItemClickListener( new DrawerItemClickListener(pActivity));
        //Set menu options and values
    }

}
