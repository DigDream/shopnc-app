package com.daxueoo.shopnc.adapter;

/**
 * Created by user on 15-9-9.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.api.FinishTask;
import com.daxueoo.shopnc.bean.Task;
import com.daxueoo.shopnc.cache.BitmapCache;
import com.daxueoo.shopnc.cache.ImageCacheManger;
import com.daxueoo.shopnc.model.TopicMessage;
import com.daxueoo.shopnc.network.ServiceGenerator;
import com.daxueoo.shopnc.ui.activity.DetailActivity;
import com.daxueoo.shopnc.ui.activity.ImagePreviewActivity;
import com.daxueoo.shopnc.ui.activity.TaskDetailActivity;
import com.daxueoo.shopnc.ui.activity.UserInfoActivity;
import com.daxueoo.shopnc.utils.ConstUtils;
import com.daxueoo.shopnc.utils.SharedPreferencesUtils;
import com.daxueoo.shopnc.utils.SystemUtils;
import com.daxueoo.shopnc.widgets.RoundedImageView;

import org.json.JSONObject;

import java.util.List;
import java.util.Random;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TaskAdapter extends BaseAdapter {
    /**
     * 上下文对象
     */
    private Context mContext = null;
    public List<Task> data;

    private ImageLoader mImageLoader;

    public static final String BUNDLE_KEY_DISPLAY_TYPE = "BUNDLE_KEY_DISPLAY_TYPE";

    public TaskAdapter(Context ctx, List<Task> data) {
        mContext = ctx;
        this.data = data;

        RequestQueue mQueue = Volley.newRequestQueue(ctx);
        mImageLoader = new ImageLoader(mQueue, new BitmapCache());
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_task, parent, false);
            holder = new ViewHolder();
            holder.taskList = (LinearLayout) convertView.findViewById(R.id.item_ll_task);

            holder.tv_task_name = (TextView) convertView.findViewById(R.id.titleProject);

            holder.tv_task_state = (TextView) convertView.findViewById(R.id.tv_task_state);

            holder.iv_head_icon = (View) convertView.findViewById(R.id.view_task_head);

            convertView.setTag(holder);
        } else {// 直接获得ViewHolder
            holder = (ViewHolder) convertView.getTag();
        }

        final Task task = data.get(position);

        holder.tv_task_name.setText(task.getArticleTitle());

        // 1:未完成 3:已完成 4:回收站
        if (task.getArticleState().equals("1")) {
            Color color = new Color();
            int colorInt = color.rgb(235,109,71);
            holder.tv_task_state.setText(R.string.tv_unfinished);
            holder.tv_task_state.setTextColor(colorInt);
            holder.iv_head_icon.setBackgroundResource(R.drawable.icon_likes);
        } else if (task.getArticleState().equals("3")) {
            Color color = new Color();
            int colorInt = color.rgb(53,181,88);
            holder.tv_task_state.setText(R.string.tv_finished);
            holder.tv_task_state.setTextColor(colorInt);
            holder.iv_head_icon.setBackgroundResource(R.drawable.icon_replies);
        } else {
            holder.tv_task_state.setText("回收站");
            holder.iv_head_icon.setBackgroundResource(R.drawable.icon_views);
        }

        holder.tv_task_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (task.getArticleState().equals("1")) {
                    FinishTask finishTask = ServiceGenerator.createService(FinishTask.class, ConstUtils.BASE_URL);
                    finishTask.finishTask((String) SharedPreferencesUtils.getParam(mContext, "key", "key"), task.getArticleId(), new Callback<JSONObject>() {
                        @Override
                        public void success(JSONObject jsonObject, Response response) {
                            Toast.makeText(mContext, "已完成此任务", Toast.LENGTH_SHORT);
                            Color color = new Color();
                            int colorInt = color.rgb(53,181,88);
                            holder.tv_task_state.setText(R.string.tv_finished);
                            holder.tv_task_state.setTextColor(colorInt);
                            holder.iv_head_icon.setBackgroundResource(R.drawable.icon_replies);
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {

        LinearLayout taskList;
        //  标题
        TextView tv_task_name;
        //  状态
        TextView tv_task_state;
        //  配图
        View iv_head_icon;
    }

}