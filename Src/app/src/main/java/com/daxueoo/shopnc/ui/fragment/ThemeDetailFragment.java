package com.daxueoo.shopnc.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HeaderViewListAdapter;
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
import com.daxueoo.shopnc.adapter.ThemeAdapter;
import com.daxueoo.shopnc.adapter.ThemeCommentAdapter;
import com.daxueoo.shopnc.api.GetThemeComment;
import com.daxueoo.shopnc.api.PostComment;
import com.daxueoo.shopnc.bean.CommentList;
import com.daxueoo.shopnc.bean.Reply;
import com.daxueoo.shopnc.cache.ImageCacheManger;
import com.daxueoo.shopnc.model.Comment;
import com.daxueoo.shopnc.model.User;
import com.daxueoo.shopnc.network.ServiceGenerator;
import com.daxueoo.shopnc.scan.Intents;
import com.daxueoo.shopnc.sdk.Shopnc;
import com.daxueoo.shopnc.ui.activity.DetailActivity;
import com.daxueoo.shopnc.utils.ACache;
import com.daxueoo.shopnc.utils.ConstUtils;
import com.daxueoo.shopnc.utils.DateUtils;
import com.daxueoo.shopnc.utils.SharedPreferencesUtils;
import com.daxueoo.shopnc.utils.StringUtils;
import com.daxueoo.shopnc.utils.SystemUtils;
import com.daxueoo.shopnc.utils.TitleBuilder;
import com.daxueoo.shopnc.widgets.WrapHeightGridView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import mehdi.sakout.dynamicbox.DynamicBox;
import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * Created by user on 15-8-20.
 */
public class ThemeDetailFragment extends CommonDetailFragment implements View.OnClickListener {
    private static final String TAG = "ThemeDetailFragment";
    private TextView textView;

    private PtrClassicFrameLayout mPtrFrame;
    private ListView listView;

    private DynamicBox box;
    private ACache aCache;

    private List<Reply> comments = new ArrayList<Reply>();
    private ThemeCommentAdapter adapter;

    // bottom_control - 底部回复栏
    private LinearLayout ll_bottom_control;
    private LinearLayout ll_select_img_bottom;  //图片选择
    private EditText et_reply_content;  //回复内容
    private LinearLayout ll_select_emoticon_bottom; //表情选择
    private LinearLayout ll_send_bottom;    //回复发送

    // listView headerView - 话题详细信息
    private View topic_detail_info;
    private ImageView iv_avatar;
    private TextView tv_user_name;
    private TextView tv_time;
    private TextView tv_replys;
    private TextView tv_views;
    private TextView tv_likes;
    private ImageView iv_image;

    private TextView tv_title;
    private TextView tv_content;

    private View include_retweeted_status;

    //listView headerView -评论头部样式
    private View topic_detail_comment_head;
    private boolean is_success = false;
    private boolean is_detail_success = false;

    private String floor_user_name;
    private String floor_user_id = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_theme_detail, container, false);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_theme_detail);
        box = new DynamicBox(this.getActivity(), linearLayout);
        //  下拉刷新
        mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_web_view_frame);
        //  ListView
        listView = (ListView) view.findViewById(R.id.list_fragment_comments);
        ll_bottom_control = (LinearLayout) view.findViewById(R.id.theme_reply_bar);
        ll_select_img_bottom = (LinearLayout) view.findViewById(R.id.ll_select_img_bottom);
        ll_select_emoticon_bottom = (LinearLayout) view.findViewById(R.id.ll_select_emoticon_bottom);
        ll_send_bottom = (LinearLayout) view.findViewById(R.id.ll_send_bottom);
        et_reply_content = (EditText) view.findViewById(R.id.et_reply_content);
        new TitleBuilder(view).setTitleText("话题详情").setLeftImage(R.drawable.navigationbar_back_sel).setLeftOnClickListener(this);

        return view;
    }

    private void initReplyBar() {
        ll_bottom_control.setBackgroundResource(R.color.white);
        ll_select_img_bottom.setOnClickListener(this);
        ll_select_emoticon_bottom.setOnClickListener(this);
        ll_send_bottom.setOnClickListener(this);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        aCache = ACache.get(this.getActivity());
        initLoading();
        initPtr();
        initHeader();
        initListView();
        initReplyBar();

        //  如果登录，获取数据
        if (!Shopnc.isHaveKey(ThemeDetailFragment.this.getActivity())) {
            handler.sendEmptyMessage(0);
        } else {
            initNoLogin();
        }

    }

    private void initNoLogin() {
        box.showExceptionLayout();
        box.setOtherExceptionMessage("请登录后查看");
    }

    private void initHeader() {
        topic_detail_info = View.inflate(this.getActivity(), R.layout.topic_detail_head, null);
        topic_detail_info.setBackgroundResource(R.color.white);
//        topic_detail_info.findViewById(R.id.ll_bottom_topic_op_bar).setVisibility(View.GONE);
        iv_avatar = (ImageView) topic_detail_info.findViewById(R.id.iv_avatar);
        tv_user_name = (TextView) topic_detail_info.findViewById(R.id.tv_user_name);
        tv_time = (TextView) topic_detail_info.findViewById(R.id.tv_time);
        tv_replys = (TextView) topic_detail_info.findViewById(R.id.tv_topic_replies);
        tv_views = (TextView) topic_detail_info.findViewById(R.id.tv_topic_views);
        tv_likes = (TextView) topic_detail_info.findViewById(R.id.tv_topic_likes);
        iv_image = (ImageView) topic_detail_info.findViewById(R.id.iv_image);
        tv_content = (TextView) topic_detail_info.findViewById(R.id.tv_topic_content);
        tv_title = (TextView) topic_detail_info.findViewById(R.id.tv_topic_title);
        include_retweeted_status = topic_detail_info.findViewById(R.id.include_retweeted_status);
        iv_image.setOnClickListener(this);
        topic_detail_comment_head = View.inflate(this.getActivity(), R.layout.topic_detail_comment_head, null);
    }

    private void initListView() {
        adapter = new ThemeCommentAdapter(this.getActivity(), comments);
        listView.addHeaderView(topic_detail_info, null, false);
        listView.addHeaderView(topic_detail_comment_head, null, false);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 获取到楼层的id和名字
                Reply reply = (Reply) parent.getAdapter().getItem(position);
                floor_user_name = reply.getMemberName();
                floor_user_id = reply.getMemberId();
                et_reply_content.setText("@" + floor_user_name + et_reply_content.getText().toString());
            }
        });
    }


    private void initLoading() {
        box.showLoadingLayout();
        box.setLoadingMessage("Loading your theme ...");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                ThemeDetailFragment.this.getActivity().finish();
                break;
            case R.id.ll_send_bottom:
                if (!et_reply_content.getText().toString().equals("")) {
                    Log.e("sdfdsf", et_reply_content.getText().toString());
                    //  发送评论
                    PostComment postComment = ServiceGenerator.createService(PostComment.class, ConstUtils.BASE_URL);
                    postComment.AddComment((String) SharedPreferencesUtils.getParam(ThemeDetailFragment.this.getActivity(), "key", "key"), (((DetailActivity) ThemeDetailFragment.this.getActivity()).getCircleId()), (((DetailActivity) ThemeDetailFragment.this.getActivity()).getDetailId()), et_reply_content.getText().toString(), floor_user_id, new Callback<JSONObject>() {
                        @Override
                        public void success(JSONObject jsonObject, retrofit.client.Response response) {
                            Log.e("tag", jsonObject.toString());
                            Log.e("fsfs", response.getUrl());
                            Log.e("fdsfs", (String) SharedPreferencesUtils.getParam(ThemeDetailFragment.this.getActivity(), "key", "key") + "==cid==" + (((DetailActivity) ThemeDetailFragment.this.getActivity()).getCircleId()) + "==tid==" + (((DetailActivity) ThemeDetailFragment.this.getActivity()).getDetailId()) + et_reply_content.getText().toString());
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            //TODO 处理失败情况。。
                            Log.e("ta", error.toString());
                        }
                    });
                    et_reply_content.setText("");
                    Toast.makeText(ThemeDetailFragment.this.getActivity(), "success!", Toast.LENGTH_SHORT).show();
                    handler.sendEmptyMessage(4);

                } else {
                    Toast.makeText(ThemeDetailFragment.this.getActivity(), "empty", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 下拉刷新
     */
    private void initPtr() {
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, listView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.refreshComplete();
                        //  异步刷新数据
                        handler.sendEmptyMessage(0);
                    }
                }, 100);
            }
        });
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ThemeDetailFragment.this.getActivity(), "Str start", Toast.LENGTH_SHORT).show();
            }
        }, 100);
    }

    /**
     * handler初始化网络数据
     */
    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    initDatatest((((DetailActivity) ThemeDetailFragment.this.getActivity()).getDetailId()), (((DetailActivity) ThemeDetailFragment.this.getActivity()).getCircleId()));
                    initOriginData(((DetailActivity) ThemeDetailFragment.this.getActivity()).getDetailId());
                    //  获取网络数据
                    //if (aCache.getAsJSONArray("themeList") == null) {
                    //}
                    break;
                case 1:
                    Log.e(TAG, String.valueOf(comments.size()));
                    adapter.notifyDataSetChanged(); // 发送消息通知ListView更新
                    if (is_success) {
                        box.hideAll();
                    }
                    break;
                //  下拉刷新
                case 2:
                    //  获取最新数据
                    break;
                case 3:
                    adapter.notifyDataSetChanged(); // 发送消息通知ListView更新
                    if (is_detail_success) {
                        box.hideAll();
                    }
                    break;
                case 4:
                    initDatatest((((DetailActivity) ThemeDetailFragment.this.getActivity()).getDetailId()), (((DetailActivity) ThemeDetailFragment.this.getActivity()).getCircleId()));
                    break;
                default:
                    break;
            }
        }
    };

    private void initOriginData(String themeId) {
        Log.e(TAG, ConstUtils.THEME_DETAIL + "&t_id=" + themeId + "&key=" + SharedPreferencesUtils.getParam(ThemeDetailFragment.this.getActivity(), "key", "test"));
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        JsonObjectRequest objRequest = new JsonObjectRequest(ConstUtils.THEME_DETAIL + "&t_id=" + themeId + "&key=" + SharedPreferencesUtils.getParam(ThemeDetailFragment.this.getActivity(), "key", "test"), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject obj) {
                Log.e(TAG, obj.toString());
                try {
                    JSONObject jsonObject = obj.getJSONObject("datas");
                    JSONObject circleThemeInfo = jsonObject.getJSONObject("theme_info");
                    //  缓存获取到的结果
//                    aCache.put("themeList" + circleId, circleThemeList);
                    String themeId = circleThemeInfo.getString("theme_id");
                    String themeTitle = circleThemeInfo.getString("theme_name");
                    String themeContent = circleThemeInfo.getString("theme_content");
                    String themeCreateAt = circleThemeInfo.getString("theme_addtime");

                    ImageCacheManger.loadImage(circleThemeInfo.getString("member_avatar"), iv_avatar, SystemUtils.getBitmapFromResources(ThemeDetailFragment.this.getActivity(), R.mipmap.ic_no_user), SystemUtils.getBitmapFromResources(ThemeDetailFragment.this.getActivity(), R.mipmap.ic_no_user));
                    tv_user_name.setText(circleThemeInfo.getString("member_name"));
                    tv_time.setText(DateUtils.getStandardDate(themeCreateAt));
                    tv_title.setText(themeTitle);
                    tv_replys.setText(circleThemeInfo.getString("theme_commentcount"));
                    tv_likes.setText(circleThemeInfo.getString("theme_likecount"));
                    tv_views.setText(circleThemeInfo.getString("theme_browsecount"));
                    SpannableString weiboContent = StringUtils.getWeiboContent(ThemeDetailFragment.this.getActivity(), tv_content, themeContent);
                    tv_content.setVisibility(View.VISIBLE);
                    tv_content.setText(weiboContent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                is_detail_success = true;
                handler.sendEmptyMessage(1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ThemeDetailFragment.this.getActivity(), "获取数据失败，请重新获取。", Toast.LENGTH_LONG).show();
                Log.e(TAG, error.toString());
            }
        });

        requestQueue.add(objRequest);
        requestQueue.start();

    }

    private void initDatatest(String theme_id, String circle_id) {
        comments.clear();
        GetThemeComment getThemeComment = ServiceGenerator.createService(GetThemeComment.class, ConstUtils.BASE_URL);
        getThemeComment.GetThemeComment(circle_id, theme_id, (String) SharedPreferencesUtils.getParam(ThemeDetailFragment.this.getActivity(), "key", "key"), "", new Callback<CommentList>() {
            @Override
            public void success(CommentList commentList, retrofit.client.Response response) {
                comments.addAll(commentList.getDatas().getReplys());
                is_success = true;
                handler.sendEmptyMessage(3);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

}