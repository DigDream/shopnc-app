package com.daxueoo.shopnc.adapter;

/**
 * Created by user on 15-7-30.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.model.TopicMessage;
import com.daxueoo.shopnc.utils.BitmapCache;
import com.daxueoo.shopnc.widgets.RelativeTimeTextView;

import java.util.List;

/**
 * Created by user on 15-7-30.
 */

public class TopicAdapter extends BaseAdapter {
    /**
     * 上下文对象
     */
    private Context mContext = null;
    public List<TopicMessage> data;

    private ImageLoader mImageLoader;


    public TopicAdapter(Context ctx, List<TopicMessage> data) {
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

        ViewHolder holder;
        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_topic, parent, false);
            holder = new ViewHolder();
            holder.topiclist = (RelativeLayout) convertView.findViewById(R.id.topiclist);

            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_topic_title);
            holder.tv_desc = (TextView) convertView.findViewById(R.id.tv_topic_content);

            holder.iv_head_icon = (NetworkImageView) convertView.findViewById(R.id.iv_topic_head);

            holder.tv_views = (TextView) convertView.findViewById(R.id.tv_topic_views);
            holder.tv_time = (RelativeTimeTextView) convertView.findViewById(R.id.tv_topic_time);
            holder.tv_replies = (TextView) convertView.findViewById(R.id.tv_topic_replies);

            convertView.setTag(holder);
        } else {// 直接获得ViewHolder
            holder = (ViewHolder) convertView.getTag();
        }

        TopicMessage msg = data.get(position);

        holder.tv_title.setText(msg.getTopic_title());
        holder.tv_desc.setText(msg.getTopic_content());
        holder.tv_views.setText(msg.getTopic_views());
        holder.tv_time.setText(msg.getTopic_time());
        holder.tv_replies.setText(msg.getTopic_replies());

        if (msg.getIcon_url() == null && msg.getIcon_url().equals("")) {
            holder.iv_head_icon.setImageResource(R.mipmap.ic_launcher);
        } else {
            holder.iv_head_icon.setDefaultImageResId(android.R.drawable.ic_menu_rotate);
            holder.iv_head_icon.setErrorImageResId(R.mipmap.ic_launcher);
            holder.iv_head_icon.setImageUrl(msg.getIcon_url(), mImageLoader);
        }

        return convertView;
    }

    static class ViewHolder {

        RelativeLayout topiclist;
        TextView tv_title;
        TextView tv_desc;
        TextView tv_views;
        TextView tv_replies;
        RelativeTimeTextView tv_time;
        NetworkImageView iv_head_icon;
    }

}