package com.daxueoo.shopnc.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.utils.TitleBuilder;
import com.daxueoo.shopnc.utils.ToastUtils;

public class TradeFragment extends BaseFragment {
	
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = View.inflate(activity, R.layout.fragment_trade, null);

		new TitleBuilder(view).setTitleText("交易").setRightImage(R.mipmap.ic_launcher).setRightOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ToastUtils.showToast(activity, "right click~", Toast.LENGTH_SHORT);
			}
		});

		return view;
	}
	
}
