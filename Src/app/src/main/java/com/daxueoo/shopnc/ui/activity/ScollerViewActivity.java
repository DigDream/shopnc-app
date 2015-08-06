package com.daxueoo.shopnc.ui.activity;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.utils.AnimationUtil;
import com.daxueoo.shopnc.widgets.scrollView.MyScrollView;
import com.daxueoo.shopnc.widgets.scrollView.OnScrollChangedListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScollerViewActivity extends Activity implements OnScrollChangedListener {
	private LinearLayout mLLAnim;
	private MyScrollView mSVmain;
	private int mScrollViewHeight;
	private int mStartAnimateTop;
	private boolean hasStart = false;
	private TextView tvInNew;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scrollview);
		tvInNew = (TextView) findViewById(R.id.tvInNew);
		mLLAnim = (LinearLayout) findViewById(R.id.ll_anim);
		initView();
		setView();
	}

	private void initView() {
		mSVmain = (MyScrollView) findViewById(R.id.sv_main);
		tvInNew.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(ScollerViewActivity.this,MainTabActivity.class));
//				AnimationUtil.finishActivityAnimation(ScollerViewActivity.this);
			}
		});
	}

	private void setView() {
		mSVmain.setOnScrollChangedListener(this);
		mLLAnim.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		mScrollViewHeight = mSVmain.getHeight();
		mStartAnimateTop = mScrollViewHeight / 5 * 4;
	}

	@Override
	public void onScrollChanged(int top, int oldTop) {
		int animTop = mLLAnim.getTop() - top;
		if (top > oldTop) {
			if (animTop < mStartAnimateTop && !hasStart) {
				Animation anim = AnimationUtils
						.loadAnimation(this, R.anim.show);
				mLLAnim.setVisibility(View.VISIBLE);
				mLLAnim.startAnimation(anim);
				hasStart = true;
			}
		} else {
			if (animTop > mStartAnimateTop && hasStart) {
				Animation anim = AnimationUtils.loadAnimation(this,
						R.anim.close);
				mLLAnim.setVisibility(View.INVISIBLE);
				mLLAnim.startAnimation(anim);
				hasStart = false;
			}
		}
	}

}
