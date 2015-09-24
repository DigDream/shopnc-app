package com.daxueoo.shopnc.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daxueoo.shopnc.R;

public class TitleBuilder {

	private View viewTitle;
	private TextView tvTitle;
	private ImageView ivLeft;
	private ImageView ivRight;
	private TextView tvLeft;
	private TextView tvRight;
	private RelativeLayout rl_titlebar;

	// 右侧日期
	private TextView tv_date_day;
	private TextView tv_date_week;
	private TextView tv_date_month;

	public TitleBuilder(Activity context) {
		viewTitle = context.findViewById(R.id.rl_titlebar);
		tvTitle = (TextView) viewTitle.findViewById(R.id.titlebar_tv);
		ivLeft = (ImageView) viewTitle.findViewById(R.id.titlebar_iv_left);
		ivRight = (ImageView) viewTitle.findViewById(R.id.titlebar_iv_right);
		tvLeft = (TextView) viewTitle.findViewById(R.id.titlebar_tv_left);
		tvRight = (TextView) viewTitle.findViewById(R.id.titlebar_tv_right);

		tv_date_day = (TextView) viewTitle.findViewById(R.id.titlebar_tv_date_day);
		tv_date_week = (TextView) viewTitle.findViewById(R.id.titlebar_tv_date_week);
		tv_date_month = (TextView) viewTitle.findViewById(R.id.titlebar_tv_month);

		rl_titlebar = (RelativeLayout) viewTitle.findViewById(R.id.rl_titlebar);
	}
	
	public TitleBuilder(View context) {
		viewTitle = context.findViewById(R.id.rl_titlebar);
		tvTitle = (TextView) viewTitle.findViewById(R.id.titlebar_tv);
		ivLeft = (ImageView) viewTitle.findViewById(R.id.titlebar_iv_left);
		ivRight = (ImageView) viewTitle.findViewById(R.id.titlebar_iv_right);
		tvLeft = (TextView) viewTitle.findViewById(R.id.titlebar_tv_left);
		tvRight = (TextView) viewTitle.findViewById(R.id.titlebar_tv_right);

		tv_date_day = (TextView) viewTitle.findViewById(R.id.titlebar_tv_date_day);
		tv_date_week = (TextView) viewTitle.findViewById(R.id.titlebar_tv_date_week);
		tv_date_month = (TextView) viewTitle.findViewById(R.id.titlebar_tv_month);
	}

	// title

	public TitleBuilder setTitleBgRes(int resid) {
		viewTitle.setBackgroundResource(resid);
		return this;
	}

	public TitleBuilder setTitleText(String text) {
		tvTitle.setVisibility(TextUtils.isEmpty(text) ? View.GONE
				: View.VISIBLE);
		tvTitle.setText(text);
		return this;
	}

	public TitleBuilder setTitleBackGround(int color) {
		rl_titlebar.setBackgroundColor(color);
		return this;
	}

	// left

	public TitleBuilder setLeftImage(int resId) {
		ivLeft.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
		ivLeft.setImageResource(resId);
		return this;
	}

	public TitleBuilder setLeftText(String text) {
		tvLeft.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
		tvLeft.setText(text);
		return this;
	}

	public TitleBuilder setLeftOnClickListener(OnClickListener listener) {
		if (ivLeft.getVisibility() == View.VISIBLE) {
			ivLeft.setOnClickListener(listener);
		} else if (tvLeft.getVisibility() == View.VISIBLE) {
			tvLeft.setOnClickListener(listener);
		}
		return this;
	}

	// right

	public TitleBuilder setRightImage(int resId) {
		ivRight.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
		ivRight.setImageResource(resId);
		return this;
	}

	public TitleBuilder setRightText(String text) {
		tvRight.setVisibility(TextUtils.isEmpty(text) ? View.GONE
				: View.VISIBLE);
		tvRight.setText(text);
		return this;
	}

	public TitleBuilder setRightOnClickListener(OnClickListener listener) {
		if (ivRight.getVisibility() == View.VISIBLE) {
			ivRight.setOnClickListener(listener);
		} else if (tvRight.getVisibility() == View.VISIBLE) {
			tvRight.setOnClickListener(listener);
		}
		return this;
	}


	public TitleBuilder setDateText(String week,String day,String month) {
		tv_date_day.setVisibility(View.VISIBLE);
		tv_date_week.setVisibility(View.VISIBLE);
		tv_date_month.setVisibility(View.VISIBLE);
		tv_date_day.setText(day);
		tv_date_month.setText("/"+month+"月");
		tv_date_week.setText(week);
		return this;
	}

	public View build() {
		return viewTitle;
	}

}
