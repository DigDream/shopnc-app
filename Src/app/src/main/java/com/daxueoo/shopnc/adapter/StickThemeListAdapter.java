package com.daxueoo.shopnc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.bean.Theme;
import com.daxueoo.shopnc.model.TopicMessage;
import com.daxueoo.shopnc.widgets.RoundedImageView;

import java.util.List;

/**
 * Created by guodont on 15/9/15.
 */
public class StickThemeListAdapter extends BaseAdapter {

    private Context mContext = null;
    public List<TopicMessage> data;

    public StickThemeListAdapter(Context mContext, List<TopicMessage> data) {
        this.mContext = mContext;
        this.data = data;
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {

            view = LayoutInflater.from(mContext).inflate(R.layout.item_stick_theme, viewGroup, false);
            holder = new ViewHolder();
            holder.ll_stick_theme = (LinearLayout) view.findViewById(R.id.ll_stick_theme);
            holder.tv_stick_themt_title = (TextView) view.findViewById(R.id.tv_stick_themt_title);
//            holder.tv_replies = (TextView) view.findViewById(R.id.tv_topic_replies);
            view.setTag(holder);
        } else {// 直接获得ViewHolder
            holder = (ViewHolder) view.getTag();
        }

        final TopicMessage theme = data.get(i);
//        holder.tv_title.setText(theme.getThemeName());
        holder.tv_stick_themt_title.setText(theme.getTopic_title());
        return view;
    }

    static class ViewHolder {
        LinearLayout ll_stick_theme;
        //  标题
        TextView tv_stick_themt_title;
        //  回复数
//        TextView tv_replies;
    }
}
