package com.daxueoo.shopnc.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.daxueoo.shopnc.MainActivity;
import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.utils.SharedPreferencesUtils;

/**
 * Created by user on 15-8-2.
 */
public class SplashActivity extends BaseActivity {

    Boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final View view = View.inflate(this, R.layout.activity_splash, null);
        setContentView(view);

        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(2000);
        view.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation arg0) {
                Intent intent;
                if ((Boolean) SharedPreferencesUtils.getParam(SplashActivity.this, "isFirst", isFirst)) {
                    intent = new Intent(SplashActivity.this, ScollerViewActivity.class);
                    startActivity(intent);
                    isFirst = false;
                    SharedPreferencesUtils.setParam(SplashActivity.this, "isFirst", isFirst);
                    finish();
                } else {
                    intent = new Intent(SplashActivity.this, MainTabActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationStart(Animation arg0) {
            }

        });
    }
}