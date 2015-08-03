package com.daxueoo.shopnc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.model.CircleMessage;
import com.daxueoo.shopnc.utils.BitmapCache;
import com.daxueoo.shopnc.widgets.RelativeTimeTextView;

import java.util.List;

/**
 * Created by user on 15-8-4.
 */

public class CircleAdapter extends BaseAdapter {
    /**
     * 上下文对象
     */
    private Context mContext = null;
    public List<CircleMessage> data;

    private ImageLoader mImageLoader;


    public CircleAdapter(Context ctx, List<CircleMessage> data) {
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

            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_circle, parent, false);
            holder = new ViewHolder();
            holder.circlelist = (LinearLayout) convertView.findViewById(R.id.circlelist);

            holder.tv_left_title = (TextView) convertView.findViewById(R.id.tv_left_title);
            holder.tv_left_content = (TextView) convertView.findViewById(R.id.tv_left_content);
            holder.tv_right_title = (TextView) convertView.findViewById(R.id.tv_right_title);
            holder.tv_right_content = (TextView) convertView.findViewById(R.id.tv_right_content);

            holder.iv_left_pic = (NetworkImageView) convertView.findViewById(R.id.iv_left_image);
            holder.iv_right_pic = (NetworkImageView) convertView.findViewById(R.id.iv_right_image);

            convertView.setTag(holder);
        } else {// 直接获得ViewHolder
            holder = (ViewHolder) convertView.getTag();
        }

        CircleMessage msg = data.get(position);

        holder.tv_left_title.setText(msg.getLeft_title());
        holder.tv_left_content.setText(msg.getLeft_content());
        holder.tv_right_title.setText(msg.getRight_title());
        holder.tv_right_content.setText(msg.getRight_content());

        if (msg.getLeft_url() == null && msg.getLeft_url().equals("")) {
            holder.iv_left_pic.setImageResource(R.mipmap.ic_launcher);
        } else {
            holder.iv_left_pic.setDefaultImageResId(android.R.drawable.ic_menu_rotate);
            holder.iv_left_pic.setErrorImageResId(R.mipmap.ic_launcher);
            holder.iv_left_pic.setImageUrl(msg.getLeft_url(), mImageLoader);
        }

        if (msg.getRight_url() == null && msg.getRight_url().equals("")) {
            holder.iv_right_pic.setImageResource(R.mipmap.ic_launcher);
        } else {
            holder.iv_right_pic.setDefaultImageResId(android.R.drawable.ic_menu_rotate);
            holder.iv_right_pic.setErrorImageResId(R.mipmap.ic_launcher);
            holder.iv_right_pic.setImageUrl(msg.getRight_url(), mImageLoader);
        }

        return convertView;
    }

    static class ViewHolder {

        LinearLayout circlelist;
        TextView tv_left_title;
        TextView tv_left_content;
        TextView tv_right_title;
        TextView tv_right_content;

        NetworkImageView iv_left_pic;
        NetworkImageView iv_right_pic;
    }

}