package com.daxueoo.shopnc.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.adapter.CircleAdapter;
import com.daxueoo.shopnc.model.CircleMessage;
import com.daxueoo.shopnc.model.TopicMessage;
import com.daxueoo.shopnc.sdk.Shopnc;
import com.daxueoo.shopnc.ui.activity.CreateCircleActivity;
import com.daxueoo.shopnc.ui.activity.LoginActivity;
import com.daxueoo.shopnc.utils.ACache;
import com.daxueoo.shopnc.utils.ConstUtils;
import com.daxueoo.shopnc.utils.SharedPreferencesUtils;
import com.daxueoo.shopnc.utils.TitleBuilder;
import com.daxueoo.shopnc.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import mehdi.sakout.dynamicbox.DynamicBox;

/**
 * 这是圈子的fragment
 * Created by user on 15-8-2.
 */
public class CircleFragment extends BaseFragment {
    private static final String TAG = "CircleFragment";
    //  视图
    private View view;
    //  适配器
    private CircleAdapter mAdapter;
    //  网格
    private GridView gridView;

    private List<CircleMessage> data = new ArrayList<CircleMessage>();
    private PtrClassicFrameLayout mPtrFrame;

    private Boolean isFirst = true;
    private ACache aCache;

    private TextView tv_hint;
    private LinearLayout ll_circle_hint, ll_content;
    private Button btn_all_circle;
    private boolean refresh = false;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    private List<String> mPlanetTitles = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapter;
    private DynamicBox box;
    private HashMap<String, String> classId;

    private String classNowId;
    private boolean myCircle = false;

    private TitleBuilder titleBuilder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = View.inflate(activity, R.layout.fragment_circle, null);


//        new TitleBuilder(view).setTitleText("圈子").setLeftImage(R.drawable.icon_circle_create).setLeftOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(CircleFragment.this.getActivity(), CreateCircleActivity.class);
//                startActivity(intent);
//            }
//        });

        mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_web_view_frame);

        gridView = (GridView) view.findViewById(R.id.gridview);

        tv_hint = (TextView) view.findViewById(R.id.tv_hint);

        ll_circle_hint = (LinearLayout) view.findViewById(R.id.ll_circle_hint);
        btn_all_circle = (Button) view.findViewById(R.id.btn_all_circle);
        ll_content = (LinearLayout) view.findViewById(R.id.ll_content);



        mDrawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) view.findViewById(R.id.left_drawer);

        box = new DynamicBox(this.getActivity(), ll_content);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //  判断是否登录
        if (!Shopnc.isHaveKey(CircleFragment.this.getActivity())) {
            //  登录
            initLoading();
            //  如果为空的时候。。
            // initTextView();

            initBtn();

            initDrawer(savedInstanceState);

            //  初始化GridView
            initGridView();
            aCache = ACache.get(this.getActivity());
            //  初始化数据
            if (isFirst) {
                isFirst = false;
            }
            //  判断是否有我的圈子缓存
            myCircle = true;
            if (aCache.getAsJSONArray("myCircleList") != null) {
                initData();
            } else {
                //  获取我的圈子
                handler.sendEmptyMessage(0);
            }

            titleBuilder = new TitleBuilder(view).setTitleText("我的圈子").setRightImage(R.drawable.icon_menu_normal).setRightOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    initLoading();
                    //   隐藏掉按钮
                    refresh = true;
                    //   发送message，获取所有圈子
                    handler.sendEmptyMessage(3);
                    //   构造侧边栏
                    titleBuilder.setTitleText("所有圈子")
                            .setLeftImage(R.drawable.navigationbar_back_sel).setLeftOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            titleBuilder.setTitleText("我的圈子").setLeftImage(0).setRightImage(0);
                            mDrawerLayout.setVisibility(View.GONE);
//                            initLoading();
//                            initBtn();
                            myCircle = true;
                            if (aCache.getAsJSONArray("myCircleList") != null) {
                                initData();
                            } else {
                                //  获取我的圈子
                                handler.sendEmptyMessage(0);
                            }

//                            handler.sendEmptyMessage(0);
                        }
                    })
                            .setRightImage(R.drawable.tilebar_right_btn).setRightOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //  打开侧滑菜单
                            mDrawerLayout.openDrawer(Gravity.RIGHT);
                        }
                    });

//                    btn_create_circle.setVisibility(View.VISIBLE);
                    btn_all_circle.setText("创建圈子");
                    btn_all_circle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setClass(CircleFragment.this.getActivity(), CreateCircleActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            });

        } else {
            //  没有登录
            initNoLogin();
            initTextView();
        }

        //  初始化下拉刷新
        initPtr();
    }

    /**
     * 展示未登录。
     */
    private void initNoLogin() {
        tv_hint.setText("请登录后查看");
        titleBuilder = new TitleBuilder(view).setTitleText("圈子");
//        initLoading();
//        //   隐藏掉按钮
//        refresh = true;
//        //   发送message，获取所有圈子
//        handler.sendEmptyMessage(3);
//        //   构造侧边栏
//        new TitleBuilder(view).setTitleText("所有圈子")
//                .setRightImage(R.drawable.tilebar_right_btn).setRightOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //  打开侧滑菜单
//                mDrawerLayout.openDrawer(Gravity.RIGHT);
//                ToastUtils.showToast(getActivity(), "right click~", Toast.LENGTH_SHORT);
//            }
//        });
    }

    private void initLoading() {
        box.showLoadingLayout();
        box.setLoadingMessage("Loading your circle ...");
    }

    private void initDrawer(Bundle savedInstanceState) {

        mPlanetTitles.add("所有分类");
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        arrayAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.drawer_list_item, mPlanetTitles);
        mDrawerList.setAdapter(arrayAdapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.closeDrawer(Gravity.RIGHT);

        if (savedInstanceState == null) {
            if (!Shopnc.isHaveKey(CircleFragment.this.getActivity())){
                myCircle = true;
            }
            selectItem(0);
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        Log.e(TAG, String.valueOf(position));
        mDrawerLayout.closeDrawer(Gravity.RIGHT);


        //  得到分类id
        Log.e(TAG, mPlanetTitles.get(position));
        if (position != 0) {
            classNowId = classId.get(mPlanetTitles.get(position));
            //  加载加载中
            initLoading();
            //
            handler.sendEmptyMessage(2);
        } else {
            if (!myCircle){
                handler.sendEmptyMessage(7);
            }
        }

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
    }

    /**
     * Fragment that appears in the "content_frame", shows a planet
     */
    public static class PlanetFragment extends Fragment {
        public static final String ARG_PLANET_NUMBER = "planet_number";

        public PlanetFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_planet, container, false);
            int i = getArguments().getInt(ARG_PLANET_NUMBER);
            String planet = getResources().getStringArray(R.array.planets_array)[i];

            int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()), "drawable", getActivity().getPackageName());
            ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
            getActivity().setTitle(planet);
            return rootView;
        }
    }

    /**
     *
     */
    private void initBtn() {
        btn_all_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   设置正在加载中
                tv_hint.setText("正在加载中。。");
                //   隐藏掉按钮
                btn_all_circle.setVisibility(View.GONE);
                refresh = true;
                //   发送message，获取所有圈子
                handler.sendEmptyMessage(3);
                //   构造侧边栏
                new TitleBuilder(view).setTitleText("我的圈子").setRightImage(R.drawable.tilebar_right_btn).setRightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  打开侧滑菜单
//                        mDrawerLayout.openDrawer(Gravity.RIGHT);
//                        ToastUtils.showToast(getActivity(), "right click~", Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }

    private void initAllCircle() {
        initOriginData(ConstUtils.ALL_CIRCLE, "all");
    }

    /**
     *
     */
    private void initTextView() {
        mPtrFrame.setVisibility(View.GONE);
        ll_circle_hint.setVisibility(View.VISIBLE);
        if (Shopnc.isHaveKey(CircleFragment.this.getActivity())) {
            tv_hint.setText("请登录后查看");
        } else {
            tv_hint.setText("您没有加入任何圈子，点击右上角查看所有圈子。");
        }

        btn_all_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (refresh) {
//                    handler.sendEmptyMessage(3);
//                }
                //跳到登录
                Intent intent = new Intent();
                intent.setClass(CircleFragment.this.getActivity(), LoginActivity.class);
                CircleFragment.this.getActivity().startActivity(intent);
            }
        });
    }

    /**
     * 初始化下拉刷新
     */
    private void initPtr() {
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, gridView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.refreshComplete();
                        //  下拉刷新
                        Toast.makeText(CircleFragment.this.getActivity(), "没有更多新数据了。。", Toast.LENGTH_SHORT).show();
                        //  handler.sendEmptyMessage(2);
                    }
                }, 100);
            }
        });
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                //mPtrFrame.autoRefresh();
            }
        }, 100);
    }

    /**
     * 多线程获取数据
     */
    private Thread thread = new Thread() {
        @Override
        public void run() {
            //  判断网络连接情况获取数据
            if (isNetConnected()) {
                handler.sendEmptyMessage(0);
            } else {
                Toast.makeText(CircleFragment.this.getActivity(), R.string.network_fail, Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * handler初始化网络数据
     */
    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //  处理个人圈子
                case 0:
                    if (aCache.getAsJSONArray("myCircleList") == null) {
                        initCircleOriginData(ConstUtils.MY_CIRCLE, (String) SharedPreferencesUtils.getParam(CircleFragment.this.getActivity(), "uid", "1"));
                    }
                    break;
                case 1:
                    box.hideAll();
                    mAdapter.notifyDataSetChanged();
                    break;
                //  下拉刷新
                case 2:
                    initOriginData(ConstUtils.CIRCLE_SEARCH, classNowId);
                    //  获取最新数据
                    break;
                case 3:
                    initOriginData(ConstUtils.CIRCLE_SEARCH, "0");
                    initClassData(ConstUtils.CIRCLE_CLASS);
                    break;
                case 4:
                    arrayAdapter.notifyDataSetChanged();
                    break;
                //  获取完毕以后，更新界面
                case 5:
                    mPtrFrame.setVisibility(View.VISIBLE);
                    ll_circle_hint.setVisibility(View.GONE);
                    break;
                //  获取到我的圈子的回调。
                case 6:
                    mPtrFrame.setVisibility(View.VISIBLE);
                    ll_circle_hint.setVisibility(View.GONE);
                    btn_all_circle.setVisibility(View.VISIBLE);
                    break;
                case 7:
                    initOriginData(ConstUtils.CIRCLE_SEARCH, "0");
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 初始化GridView
     */
    private void initGridView() {
        mAdapter = new CircleAdapter(this.getActivity(), data);
        gridView.setAdapter(mAdapter);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        data.clear();
        JSONArray circles = aCache.getAsJSONArray("myCircleList");
        for (int i = 0; i < circles.length(); i++) {
            try {
                JSONObject circle = circles.getJSONObject(i);
                String circleId = circle.getString("circle_id");
                String circleTitle = circle.getString("circle_name");
                String circleContent = circle.getString("circle_desc");
                String circlePeople = circle.getString("circle_mcount") + "人";
                String circleUrl = circle.getString("circle_img");

                CircleMessage msg = new CircleMessage(circleUrl, circleTitle, circleContent, circlePeople);
                msg.setId(circleId);
                data.add(msg);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        handler.sendEmptyMessage(6);
        handler.sendEmptyMessage(1);
    }

    /**
     * 获取远程数据
     */
    private void initClassData(String url) {
        mPlanetTitles.clear();
        mPlanetTitles.add("所有分类");
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        JsonObjectRequest objRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject obj) {
                Log.e(TAG, obj.toString());

                try {
                    JSONObject jsonObject = obj.getJSONObject("datas");
                    JSONArray circleClasses = jsonObject.getJSONArray("circle_classes");
                    //  缓存获取到的结果
                    aCache.put("circle_classes", circleClasses);
                    classId = new HashMap<String, String>();
                    for (int i = 0; i < circleClasses.length(); i++) {
                        JSONObject circle = circleClasses.getJSONObject(i);
                        String circleTitle = circle.getString("class_name");
                        String circleContent = circle.getString("class_id");
                        classId.put(circleTitle, circleContent);
                        mPlanetTitles.add(circleTitle);
                    }
                    refresh = false;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(4);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CircleFragment.this.getActivity(), "获取数据失败，请重新获取。", Toast.LENGTH_LONG).show();
                Log.e(TAG, error.toString());
            }
        });

        requestQueue.add(objRequest);
        requestQueue.start();
    }

    /**
     * 获取远程数据
     */
    private void initOriginData(String url, final String classes) {
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        JsonObjectRequest objRequest = new JsonObjectRequest(url + "&class_id=" + classes, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject obj) {
                Log.e(TAG, obj.toString());
                data.clear();
                //  获取信息
                try {
                    JSONObject jsonObject = obj.getJSONObject("datas");
                    JSONArray circles = jsonObject.getJSONArray("circles");
                    //  缓存获取到的结果
                    aCache.put("CircleList", circles);
                    for (int i = 0; i < circles.length(); i++) {
                        JSONObject circle = circles.getJSONObject(i);
                        String circleId = circle.getString("circle_id");
                        String circleTitle = circle.getString("circle_name");
                        String circleContent = circle.getString("circle_desc");
                        String circlePeople = circle.getString("circle_mcount") + "人";
                        String circleUrl = circle.getString("circle_img");

                        CircleMessage msg = new CircleMessage(circleUrl, circleTitle, circleContent, circlePeople);
                        msg.setId(circleId);
                        data.add(msg);

                    }
                    Log.e(TAG, data.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //  显示界面

                //  回调

                handler.sendEmptyMessage(5);
                handler.sendEmptyMessage(1);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CircleFragment.this.getActivity(), "获取数据失败，请重新获取。", Toast.LENGTH_LONG).show();
                Log.e(TAG, error.toString());
            }
        });

        requestQueue.add(objRequest);
        requestQueue.start();
    }

    /**
     * 获取远程数据
     */
    private void initCircleOriginData(String url, final String uid) {
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        JsonObjectRequest objRequest = new JsonObjectRequest(url + "&uid=" + uid, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject obj) {
                Log.e(TAG, obj.toString());
                data.clear();

                //  获取信息
                try {
                    JSONObject jsonObject = obj.getJSONObject("datas");
                    JSONArray circles = jsonObject.getJSONArray("circle_list");
                    //  缓存获取到的结果
                    aCache.put("myCircleList", circles);
                    for (int i = 0; i < circles.length(); i++) {
                        JSONObject circle = circles.getJSONObject(i);
                        String circleId = circle.getString("circle_id");
                        String circleTitle = circle.getString("circle_name");
                        String circleContent = circle.getString("circle_desc");
                        String circlePeople = circle.getString("circle_mcount") + "人";
                        String circleUrl = circle.getString("circle_img");

                        CircleMessage msg = new CircleMessage(circleUrl, circleTitle, circleContent, circlePeople);
                        msg.setId(circleId);
                        data.add(msg);

                    }
                    Log.e(TAG, data.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //  显示界面

                //  回调

                handler.sendEmptyMessage(6);
                handler.sendEmptyMessage(1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CircleFragment.this.getActivity(), "获取数据失败，请重新获取。", Toast.LENGTH_LONG).show();
                Log.e(TAG, error.toString());
            }
        });

        requestQueue.add(objRequest);
        requestQueue.start();
    }
}
