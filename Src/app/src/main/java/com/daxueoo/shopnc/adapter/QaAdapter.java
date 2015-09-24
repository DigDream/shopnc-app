package com.daxueoo.shopnc.adapter;

/**
 * Created by user on 15-7-30.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.cache.ImageCacheManger;
import com.daxueoo.shopnc.model.TopicMessage;
import com.daxueoo.shopnc.ui.activity.ImagePreviewActivity;
import com.daxueoo.shopnc.ui.activity.DetailActivity;
import com.daxueoo.shopnc.cache.BitmapCache;
import com.daxueoo.shopnc.ui.activity.UserInfoActivity;
import com.daxueoo.shopnc.utils.DateUtils;
import com.daxueoo.shopnc.utils.SystemUtils;
import com.daxueoo.shopnc.widgets.RoundedImageView;

import java.util.List;

import in.srain.cube.image.drawable.TextDrawable;

/**
 * Created by user on 15-7-30.
 */

public class QaAdapter extends BaseAdapter {
    /**
     * 上下文对象
     */
    private Context mContext = null;
    public List<TopicMessage> data;

    public static final String BUNDLE_KEY_DISPLAY_TYPE = "BUNDLE_KEY_DISPLAY_TYPE";

    public QaAdapter(Context ctx, List<TopicMessage> data) {
        mContext = ctx;
        this.data = data;
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

        ViewHolder holder;
        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_qa, parent, false);
            holder = new ViewHolder();
            holder.topiclist = (RelativeLayout) convertView.findViewById(R.id.themeList);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_topic_title);
            holder.tv_desc = (TextView) convertView.findViewById(R.id.tv_topic_content);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_topic_time);
            holder.iv_head_icon = (ImageView) convertView.findViewById(R.id.iv_topic_head);
            holder.iv_head_user_icon = (RoundedImageView) convertView.findViewById(R.id.iv_topic_user_head);
            holder.tv_user_name = (TextView) convertView.findViewById(R.id.tv_topic_user_name);
//            holder.tv_views = (TextView) convertView.findViewById(R.id.tv_topic_views);
            holder.tv_qa_reply_count = (TextView) convertView.findViewById(R.id.tv_qa_reply_count);
            holder.tv_qa_reply_count_0 = (TextView) convertView.findViewById(R.id.tv_qa_reply_count_0);
//            holder.tv_likes = (TextView) convertView.findViewById(R.id.tv_topic_likes);

            convertView.setTag(holder);
        } else {// 直接获得ViewHolder
            holder = (ViewHolder) convertView.getTag();
        }

        final TopicMessage msg = data.get(position);

        holder.tv_title.setText(msg.getTopic_title());
        holder.tv_desc.setText(msg.getTopic_content());
        holder.tv_time.setText(DateUtils.getStandardDate(msg.getTopic_time()));
        holder.tv_user_name.setText(msg.getTopic_user_name());
//        holder.tv_views.setText(msg.getTopic_views());
        if (msg.getTopic_replies().equals("0")) {
            holder.tv_qa_reply_count.setText("");
            holder.tv_qa_reply_count_0.setVisibility(View.VISIBLE);
        } else {
            holder.tv_qa_reply_count.setText(msg.getTopic_replies());
        }

//        holder.tv_likes.setText(msg.getTopic_likes());

        final Intent intent = new Intent();
        //  图片的点击事件
        holder.iv_head_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(mContext, ImagePreviewActivity.class);
                intent.putExtra("pic_url", msg.getIcon_url());
                mContext.startActivity(intent);
            }
        });

        //  标题的点击事件
        holder.tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(mContext, DetailActivity.class);
                intent.putExtra("BUNDLE_KEY_DISPLAY_TYPE", 0);
                intent.putExtra("detailId", msg.getTopic_id());
                mContext.startActivity(intent);
                Toast.makeText(mContext, msg.getTopic_title(), Toast.LENGTH_LONG).show();
            }
        });

        //  内容的点击事件，和标题同理
        holder.tv_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.setClass(mContext, DetailActivity.class);
                intent.putExtra("BUNDLE_KEY_DISPLAY_TYPE", 0);
                intent.putExtra("detailId", msg.getTopic_id());
                mContext.startActivity(intent);
                Toast.makeText(mContext, msg.getTopic_content(), Toast.LENGTH_LONG).show();
            }
        });

        //  用户头像的点击事件
        holder.iv_head_user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(mContext, UserInfoActivity.class);
                intent.putExtra("uid", msg.getTopic_user_id());
                mContext.startActivity(intent);
            }
        });

        //  用户昵称的点击事件，和头像同理
        holder.tv_user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(mContext, UserInfoActivity.class);
                intent.putExtra("uid", msg.getTopic_user_id());
                mContext.startActivity(intent);
            }
        });

        if (msg.getIcon_url() == null || msg.getIcon_url().equals("")) {
            holder.iv_head_icon.setVisibility(View.GONE);
        } else {
            ImageCacheManger.loadImage(msg.getIcon_url(), holder.iv_head_icon, SystemUtils.getBitmapFromResources((Activity) mContext, R.mipmap.ic_no_user), SystemUtils.getBitmapFromResources((Activity) mContext, R.mipmap.ic_no_user));
        }

        if (msg.getTopic_user_icon() == null || msg.getTopic_user_icon().equals("")) {
            holder.iv_head_user_icon.setVisibility(View.GONE);
        } else {
            ImageCacheManger.loadImage(msg.getTopic_user_icon(), holder.iv_head_user_icon, SystemUtils.getBitmapFromResources((Activity) mContext, R.mipmap.ic_no_user), SystemUtils.getBitmapFromResources((Activity) mContext, R.mipmap.ic_no_user));
        }

        return convertView;
    }

    static class ViewHolder {
        RelativeLayout topiclist;
        //  标题
        TextView tv_title;
        //  内容
        TextView tv_desc;
        //  时间
        TextView tv_time;
        //  昵称
        TextView tv_user_name;

        TextView tv_qa_reply_count_0;

        TextView tv_qa_reply_count;
        //        //  浏览数
//        TextView tv_views;
//        //  回复数
//        TextView tv_replies;
//        //  喜欢数
//        TextView tv_likes;
        //  配图
        ImageView iv_head_icon;
        //  用户头像
        RoundedImageView iv_head_user_icon;
    }

}