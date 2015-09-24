package com.daxueoo.shopnc.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.daxueoo.shopnc.bean.Theme;
import com.daxueoo.shopnc.cache.ImageCacheManger;
import com.daxueoo.shopnc.model.TopicMessage;
import com.daxueoo.shopnc.ui.activity.ImagePreviewActivity;
import com.daxueoo.shopnc.ui.activity.DetailActivity;
import com.daxueoo.shopnc.cache.BitmapCache;
import com.daxueoo.shopnc.ui.activity.UserInfoActivity;
import com.daxueoo.shopnc.utils.DateUtils;
import com.daxueoo.shopnc.utils.ImageUtils;
import com.daxueoo.shopnc.utils.SystemUtils;
import com.daxueoo.shopnc.widgets.RoundedImageView;

import java.util.List;

/**
 * Created by user on 15-7-30.
 */

public class ThemeListAdapter extends BaseAdapter {
    /**
     * 上下文对象
     */
    private Context mContext = null;
    public List<Theme> data;

    public static final String BUNDLE_KEY_DISPLAY_TYPE = "BUNDLE_KEY_DISPLAY_TYPE";

    public ThemeListAdapter(Context ctx, List<Theme> data) {
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

            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_theme, parent, false);
            holder = new ViewHolder();
            holder.topiclist = (RelativeLayout) convertView.findViewById(R.id.themeList);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_topic_title);
            holder.tv_desc = (TextView) convertView.findViewById(R.id.tv_topic_content);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_topic_time);
            holder.iv_head_icon = (ImageView) convertView.findViewById(R.id.iv_topic_head);
            holder.iv_head_user_icon = (RoundedImageView) convertView.findViewById(R.id.iv_topic_user_head);
            holder.tv_user_name = (TextView) convertView.findViewById(R.id.tv_topic_user_name);
            holder.tv_views = (TextView) convertView.findViewById(R.id.tv_topic_views);
            holder.tv_replies = (TextView) convertView.findViewById(R.id.tv_topic_replies);
            holder.tv_likes = (TextView) convertView.findViewById(R.id.tv_topic_likes);

            convertView.setTag(holder);
        } else {// 直接获得ViewHolder
            holder = (ViewHolder) convertView.getTag();
        }

        final Theme theme = data.get(position);

        holder.tv_title.setText(theme.getThemeName());
        holder.tv_desc.setText(theme.getThemeContent());
        holder.tv_time.setText(DateUtils.getStandardDate(theme.getThemeAddtime()));
        holder.tv_user_name.setText(theme.getMemberName());
        holder.tv_views.setText(theme.getThemeBrowsecount());
        holder.tv_replies.setText(theme.getThemeCommentcount());
        holder.tv_likes.setText(theme.getThemeLikecount());

        final String themeImg = ImageUtils.getContentFirstImgUrl(theme.getThemeContent());
        final Intent intent = new Intent();
        //  图片的点击事件
        holder.iv_head_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(mContext, ImagePreviewActivity.class);
                intent.putExtra("pic_url", themeImg);
                mContext.startActivity(intent);
            }
        });

        //  标题的点击事件
        holder.tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(mContext, DetailActivity.class);
                intent.putExtra("BUNDLE_KEY_DISPLAY_TYPE", 0);
                intent.putExtra("detailId", theme.getThemeId());
                intent.putExtra("circleId", theme.getCircleId());
                mContext.startActivity(intent);
                Toast.makeText(mContext, theme.getThemeName(), Toast.LENGTH_LONG).show();
            }
        });

        //  内容的点击事件，和标题同理
        holder.tv_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.setClass(mContext, DetailActivity.class);
                intent.putExtra("BUNDLE_KEY_DISPLAY_TYPE", 0);
                intent.putExtra("detailId", theme.getThemeId());
                intent.putExtra("circleId", theme.getCircleId());
                mContext.startActivity(intent);
                Toast.makeText(mContext, theme.getThemeName(), Toast.LENGTH_LONG).show();
            }
        });

        //  用户头像的点击事件
        holder.iv_head_user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(mContext, UserInfoActivity.class);
                intent.putExtra("uid", theme.getMemberId());
                mContext.startActivity(intent);
            }
        });

        //  用户昵称的点击事件，和头像同理
        holder.tv_views.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(mContext, UserInfoActivity.class);
                intent.putExtra("uid", theme.getMemberId());
                mContext.startActivity(intent);
            }
        });

        if (themeImg == null || themeImg.equals("")) {
            holder.iv_head_icon.setVisibility(View.GONE);
        } else {
            ImageCacheManger.loadImage(themeImg, holder.iv_head_icon, SystemUtils.getBitmapFromResources((Activity) mContext, R.drawable.icon_img_normal), SystemUtils.getBitmapFromResources((Activity) mContext, R.drawable.icon_img_normal));
        }

        if (theme.getMemberAvatar() == null || theme.getMemberAvatar().equals("")) {
            holder.iv_head_user_icon.setVisibility(View.GONE);
        } else {
            ImageCacheManger.loadImage(theme.getMemberAvatar(), holder.iv_head_user_icon, SystemUtils.getBitmapFromResources((Activity) mContext, R.drawable.icon_img_normal), SystemUtils.getBitmapFromResources((Activity) mContext, R.drawable.icon_img_normal));
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
        //  浏览数
        TextView tv_views;
        //  回复数
        TextView tv_replies;
        //  喜欢数
        TextView tv_likes;
        //  配图
        ImageView iv_head_icon;
        //  用户头像
        RoundedImageView iv_head_user_icon;
    }

}