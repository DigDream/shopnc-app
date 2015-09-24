package com.daxueoo.shopnc.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.ui.fragment.CircleDetailFragment;
import com.daxueoo.shopnc.ui.fragment.TaskListFragment;
import com.daxueoo.shopnc.utils.GestureUtils;
import com.daxueoo.shopnc.utils.TitleBuilder;

import java.util.ArrayList;

/**
 *
 * Created by user on 15-9-9.
 */
public class TaskActivity extends BaseFragmentActivity implements View.OnClickListener {

    private GestureDetector gestureDetector;
    private GestureUtils.Screen screen;

    public RelativeLayout getRl_titlebar() {
        return rl_titlebar;
    }

    private RelativeLayout rl_titlebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        rl_titlebar = (RelativeLayout) findViewById(R.id.rl_titlebar);
        initView();
        //TODO 以后可以将这类型的Activity封装
    }


    /**
     * 初始化视图
     */
    private void initView() {
        new TitleBuilder(this).setLeftImage(R.drawable.navigationbar_back_sel).setLeftOnClickListener(this);
        TaskListFragment fragment = new TaskListFragment();
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.container, fragment);
        trans.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.titlebar_iv_left:
                this.finish();
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        // TODO Auto-generated method stub
        for ( MyOntouchListener listener : touchListeners )
        {
            listener.onTouchEvent( event );
        }
        return super.onTouchEvent( event );
    }

    private ArrayList<MyOntouchListener> touchListeners = new ArrayList<TaskActivity.MyOntouchListener>();


    public void registerListener(MyOntouchListener listener)
    {
        touchListeners.add(listener);
    }


    public void unRegisterListener(MyOntouchListener listener)
    {
        touchListeners.remove( listener );
    }

    public interface MyOntouchListener
    {
        public void onTouchEvent(MotionEvent event);
    }


}
