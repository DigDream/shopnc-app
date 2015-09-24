package com.daxueoo.shopnc.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.daxueoo.shopnc.cache.BitmapCache;
import com.daxueoo.shopnc.cache.ImageCacheManger;
import com.daxueoo.shopnc.model.TrendMessage;
import com.daxueoo.shopnc.ui.activity.DetailActivity;
import com.daxueoo.shopnc.ui.activity.ImagePreviewActivity;
import com.daxueoo.shopnc.ui.activity.UserInfoActivity;
import com.daxueoo.shopnc.utils.DateUtils;
import com.daxueoo.shopnc.utils.SystemUtils;
import com.daxueoo.shopnc.widgets.RoundedImageView;

import java.util.List;

/**
 * Created by guodont on 15/9/9.
 */
public class TrendAdapter extends BaseAdapter {

    /**
     * 上下文对象
     */
    private Context mContext = null;
    public List<TrendMessage> data;
    private ImageLoader mImageLoader;

    public TrendAdapter(Context ctx, List<TrendMessage> data) {
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
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("WrongViewCast")
    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        ViewHolder holder;
        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_trend, viewGroup, false);
            holder = new ViewHolder();
            holder.trendList = (RelativeLayout) convertView.findViewById(R.id.rl_trend);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_trend_title);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_trend_time);
            holder.tv_in_circle = (TextView) convertView.findViewById(R.id.tv_trend_in_circle);
            holder.tv_desc = (TextView) convertView.findViewById(R.id.tv_trend_content);
            holder.iv_head_icon = (ImageView) convertView.findViewById(R.id.iv_trend_head);
            holder.iv_head_user_icon = (RoundedImageView) convertView.findViewById(R.id.iv_trend_user_head);
            holder.tv_user_name = (TextView) convertView.findViewById(R.id.tv_trend_user_name);

            convertView.setTag(holder);
        } else {// 直接获得ViewHolder
            holder = (ViewHolder) convertView.getTag();
        }

        final TrendMessage msg = data.get(position);
        holder.tv_title.setText(msg.getTrend_title());
        holder.tv_desc.setText(msg.getTrend_content());
        holder.tv_user_name.setText(msg.getTrend_user_name());
        holder.tv_time.setText(DateUtils.getStandardDate(msg.getTrend_time()));
        holder.tv_in_circle.setText(msg.getTrend_in_circle());

        if (msg.getTrend_img() == null || msg.getTrend_img().equals("")) {
            holder.iv_head_icon.setVisibility(View.GONE);
        } else {
            ImageCacheManger.loadImage(msg.getTrend_img(), holder.iv_head_icon, SystemUtils.getBitmapFromResources((Activity) mContext, R.mipmap.ic_no_user), SystemUtils.getBitmapFromResources((Activity) mContext, R.mipmap.ic_no_user));
        }

        final Intent intent = new Intent();
        //  图片的点击事件
        holder.iv_head_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.setClass(mContext, ImagePreviewActivity.class);
                intent.putExtra("pic_url", msg.getTrend_img());
                mContext.startActivity(intent);
            }
        });

        //  标题的点击事件
        holder.tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(mContext, DetailActivity.class);
                intent.putExtra("BUNDLE_KEY_DISPLAY_TYPE", 0);
                intent.putExtra("detailId", msg.getTrend_id());
                mContext.startActivity(intent);
                Toast.makeText(mContext, msg.getTrend_title(), Toast.LENGTH_LONG).show();
            }
        });

        //  内容的点击事件，和标题同理
        holder.tv_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(mContext, DetailActivity.class);
                intent.putExtra("BUNDLE_KEY_DISPLAY_TYPE", 0);
                intent.putExtra("detailId", msg.getTrend_id());
                mContext.startActivity(intent);
                Toast.makeText(mContext, msg.getTrend_content(), Toast.LENGTH_LONG).show();
            }
        });

        //  用户头像的点击事件
        holder.iv_head_user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(mContext, UserInfoActivity.class);
                intent.putExtra("uid", msg.getTrend_user_name());
                mContext.startActivity(intent);
            }
        });

        //  用户昵称的点击事件，和头像同理
        holder.tv_user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(mContext, UserInfoActivity.class);
                intent.putExtra("uid", msg.getTrend_user_name());
                mContext.startActivity(intent);
            }
        });


        if (msg.getTrend_user_avatar() == null || msg.getTrend_user_avatar().equals("")) {
            holder.iv_head_user_icon.setVisibility(View.GONE);
        } else {
            ImageCacheManger.loadImage(msg.getTrend_user_avatar(), holder.iv_head_user_icon, SystemUtils.getBitmapFromResources((Activity) mContext, R.mipmap.ic_no_user), SystemUtils.getBitmapFromResources((Activity) mContext, R.mipmap.ic_no_user));
        }

        return convertView;
    }

    static class ViewHolder {

        RelativeLayout trendList;
        //  标题
        TextView tv_title;
        //  内容
        TextView tv_desc;
        //  时间
        TextView tv_time;
        //  所在圈子
        TextView tv_in_circle;
        //  昵称
        TextView tv_user_name;
        //  配图
        ImageView iv_head_icon;
        //  用户头像
        RoundedImageView iv_head_user_icon;
    }
}
