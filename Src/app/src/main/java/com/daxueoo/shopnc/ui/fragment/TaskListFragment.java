package com.daxueoo.shopnc.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.adapter.TaskAdapter;
import com.daxueoo.shopnc.api.GetTaskList;
import com.daxueoo.shopnc.bean.Task;
import com.daxueoo.shopnc.bean.TaskList;
import com.daxueoo.shopnc.network.ServiceGenerator;
import com.daxueoo.shopnc.sdk.Shopnc;
import com.daxueoo.shopnc.ui.activity.AddTaskActivity;
import com.daxueoo.shopnc.ui.activity.TaskActivity;
import com.daxueoo.shopnc.ui.activity.TaskDetailActivity;
import com.daxueoo.shopnc.utils.ACache;
import com.daxueoo.shopnc.utils.ConstUtils;
import com.daxueoo.shopnc.utils.GestureUtils;
import com.daxueoo.shopnc.utils.SharedPreferencesUtils;
import com.daxueoo.shopnc.utils.SystemUtils;
import com.daxueoo.shopnc.utils.TitleBuilder;
import com.daxueoo.shopnc.widgets.ForScrollViewListView;
import com.melnykov.fab.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import mehdi.sakout.dynamicbox.DynamicBox;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by user on 15-9-9.
 */
public class TaskListFragment extends Fragment {

    SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日");
    SimpleDateFormat formatterMonth = new SimpleDateFormat("M");
    SimpleDateFormat formatterDay = new SimpleDateFormat("dd");
    SimpleDateFormat formatterWeek = new SimpleDateFormat("E");

    Long now = System.currentTimeMillis();

    int one_day = 24 * 60 * 60 * 1000;


    int date_diff = 0;

    //  Header标题
    private TextView tv_title;

    private ForScrollViewListView listView;

    private List<Task> data = new ArrayList<Task>();
    private TaskAdapter topicAdapter;

    private PtrClassicFrameLayout mPtrFrame;
    private ScrollView scrollView;

    private ACache aCache;
    private DynamicBox box;

    private FloatingActionButton fab;

    private GestureDetector gestureDetector;
    private GestureUtils.Screen screen;

    private TextView tv_today, tv_yesterday, tv_tomorrow;

    private TitleBuilder titleBuilder;

    private LinearLayout ll_task_date_bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_web_view_frame);
        ll_task_date_bar = (LinearLayout) view.findViewById(R.id.ll_task_date_bar);
        listView = (ForScrollViewListView) view.findViewById(R.id.list_fragment_topic);
        box = new DynamicBox(this.getActivity(), listView);
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        //  设置滚动到顶部
        scrollView.smoothScrollTo(0, 0);
        fab = (FloatingActionButton) view.findViewById(R.id.normal_plus);
        tv_today = (TextView) view.findViewById(R.id.tv_today);
        tv_tomorrow = (TextView) view.findViewById(R.id.tv_tomorrow);
        tv_yesterday = (TextView) view.findViewById(R.id.tv_yesterday);
        titleBuilder = new TitleBuilder(TaskListFragment.this.getActivity());
        return view;
    }

    TaskActivity.MyOntouchListener listener;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleBuilder.setDateText(formatterWeek.format(now), formatterDay.format(now), formatterMonth.format(now));
        tv_today.setText(formatter.format(new Date(now)));
        tv_yesterday.setText(formatter.format(new Date(now - one_day)));
        tv_tomorrow.setText(formatter.format(new Date(now + one_day)));
        aCache = ACache.get(this.getActivity());
        //  初始化ListView，后期换成RecyclerView
        //  TODO    换成RecyclerView
        initListView();
        initLoading();
        initFab();
        if (!Shopnc.isHaveKey(TaskListFragment.this.getActivity())) {
            handler.sendEmptyMessage(0);
        } else {
            initNoLogin();
        }
        //  设置ScrollView嵌套ListView的滚动问题
        //  初始化下拉刷新
        initPtr();
        listener = new TaskActivity.MyOntouchListener() {

            @Override
            public void onTouchEvent(MotionEvent event) {
                gestureDetector.onTouchEvent(event);
            }
        };

        tv_yesterday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date_diff = date_diff - 1;
                Long toDate = now + date_diff * one_day;
                changeBackColor();
                slideRight();
                initLoading();
                initOriginData(toDate.toString().substring(0, 10));
            }
        });

        tv_tomorrow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                date_diff = date_diff + 1;
                Long toDate = now + date_diff * one_day;
                changeBackColor();
                slideRight();
                initLoading();
                initOriginData(toDate.toString().substring(0, 10));
            }
        });

        ((TaskActivity) this.getActivity()).registerListener(listener);
        gestureDetector = new GestureDetector(this.getActivity(), onGestureListener);
        //得到屏幕的大小
        screen = GestureUtils.getScreenPix(this.getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPtrFrame.autoRefresh();
    }

    private void initFab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TaskListFragment.this.getActivity(), AddTaskActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initNoLogin() {
        box.showExceptionLayout();
        box.setOtherExceptionMessage("请登录后查看");
    }

    private void initLoading() {
        box.showLoadingLayout();
        box.setLoadingMessage("Loading your task ...");
    }

    /**
     * 下拉刷新
     */
    private void initPtr() {
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, scrollView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.refreshComplete();
                        //  异步刷新数据
                        handler.sendEmptyMessage(2);
                    }
                }, 100);
            }
        });
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                //  mPtrFrame.autoRefresh();
                Toast.makeText(TaskListFragment.this.getActivity(), "Str start", Toast.LENGTH_SHORT).show();
            }
        }, 100);
    }

    /**
     * ListView
     */
    private void initListView() {
        topicAdapter = new TaskAdapter(this.getActivity(), data);
        listView.setAdapter(topicAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("task", data.get(position));
                intent.setClass(TaskListFragment.this.getActivity(), TaskDetailActivity.class);
                startActivityForResult(intent, 2);
            }
        });
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return false;
            }
        });
    }


    /**
     * handler初始化网络数据
     */
    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //  获取网络数据
                    initOriginData(now.toString().substring(0,10));
                    Log.e("KEY",SharedPreferencesUtils.getParam(TaskListFragment.this.getActivity(), "key", "key").toString());
                    break;
                case 1:
                    topicAdapter.notifyDataSetChanged(); // 发送消息通知ListView更新
                    SystemUtils.setListViewHeightBasedOnChildren(listView);
                    box.hideAll();
                    Toast.makeText(TaskListFragment.this.getActivity(), "没有更多新数据了。。", Toast.LENGTH_LONG).show();
                    break;
                //  下拉刷新
                case 2:
                    Long toDate = now + date_diff * one_day;
                    initOriginData(toDate.toString().substring(0,10));
                    break;
                case 3:
                    topicAdapter.notifyDataSetChanged(); // 发送消息通知ListView更新
                    box.hideAll();
                    break;
                default:
                    break;
            }
        }
    };

    private void initOriginData(String date) {
        data.clear();
        GetTaskList getTaskList = ServiceGenerator.createService(GetTaskList.class, ConstUtils.BASE_URL);
        getTaskList.GetTaskList("", date, SharedPreferencesUtils.getParam(TaskListFragment.this.getActivity(), "key", "key").toString(), "", "", new Callback<TaskList>() {
            @Override
            public void success(TaskList taskList, Response response) {
                data.addAll(taskList.getDatas().getTasks());
                Log.e("TASKS", taskList.getDatas().getTasks().toString());
                Log.e("URL",response.getUrl());
                handler.sendEmptyMessage(3);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("tag", error.toString());
            }
        });

    }

    GestureDetector.OnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            float x = e2.getX() - e1.getX();
            float y = e2.getY() - e1.getY();
            //限制必须得划过屏幕的1/4才能算划过
            float x_limit = screen.widthPixels / 4;
            float y_limit = screen.heightPixels / 4;
            float x_abs = Math.abs(x);
            float y_abs = Math.abs(y);
            if (x_abs >= y_abs) {
                //gesture left or right
                if (x > x_limit || x < -x_limit) {
                    if (x > 0) {
                        //right
                        date_diff = date_diff - 1;
                        Long toDate = now + date_diff * one_day;
                        slideRight();
                        initLoading();
                        initOriginData(toDate.toString().substring(0,10));
                        changeBackColor();
                        show("right");

                    } else if (x < 0) {
                        //left
                        date_diff = date_diff + 1;

                        Long toDate = now + date_diff * one_day;

                        slideRight();
                        initLoading();

                        initOriginData(toDate.toString().substring(0, 10));

                        show("left");
                        changeBackColor();
                    }
                }
            } else {
                //gesture down or up
                if (y > y_limit || y < -y_limit) {
                    if (y > 0) {
                        //down
                        show("down");
                    } else if (y < 0) {
                        //up
                        show("up");
                    }
                }
            }
            return true;
        }

    };

    private void changeBackColor() {

        ArrayList<Integer> colors = new ArrayList<Integer>();
        int colorRGBs[][] = {{235,109,71},{40,197,193},{97,184,29},{34,229,195},{229,93,34},{55,206,187}};

        for (int i = 0; i < colorRGBs.length; i++ ) {
            Color color = new Color();
            int colorInt = color.rgb(colorRGBs[i][0],colorRGBs[i][1],colorRGBs[i][2]);
            colors.add(colorInt);
        }

        int colorInt = colors.get(new Random().nextInt(6));
        ((TaskActivity) this.getActivity()).getRl_titlebar().setBackgroundColor(colorInt);
        ll_task_date_bar.setBackgroundColor(colorInt);
        mPtrFrame.setBackgroundColor(colorInt);
        fab.setColorNormal(colorInt);
        fab.setColorPressed(colorInt - 20);

    }

    private void show(String value) {
        Toast.makeText(this.getActivity(), value, Toast.LENGTH_SHORT).show();
    }

    private void slideLeft() {
        tv_yesterday.setText(formatter.format(new Date(now)));
        tv_today.setText(formatter.format(new Date(now)));
    }

    private void slideRight() {
        titleBuilder.setDateText(formatterWeek.format(now + date_diff * one_day), formatterDay.format(now + date_diff * one_day), formatterMonth.format(now + date_diff * one_day));
        tv_tomorrow.setText(formatter.format(new Date(now + (date_diff + 1) * one_day)));
        tv_today.setText(formatter.format(new Date(now + date_diff * one_day)));
        tv_yesterday.setText(formatter.format(new Date(now + (date_diff - 1) * one_day)));
    }

}
