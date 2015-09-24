package com.daxueoo.shopnc.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.bean.ArticleList;
import com.daxueoo.shopnc.bean.Theme;
import com.daxueoo.shopnc.cache.ImageCacheManger;
import com.daxueoo.shopnc.ui.activity.DetailActivity;
import com.daxueoo.shopnc.ui.activity.ImagePreviewActivity;
import com.daxueoo.shopnc.ui.activity.UserInfoActivity;
import com.daxueoo.shopnc.utils.DateUtils;
import com.daxueoo.shopnc.utils.ImageUtils;
import com.daxueoo.shopnc.utils.SystemUtils;
import com.daxueoo.shopnc.widgets.RoundedImageView;

import java.util.List;

/**
 * Created by user on 15-7-30.
 */

public class NoticeListAdapter extends BaseAdapter {
    /**
     * 上下文对象
     */
    private Context mContext = null;
    public List<ArticleList> data;

    public NoticeListAdapter(Context ctx, List<ArticleList> data) {
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

            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_notice, parent, false);
            holder = new ViewHolder();
            holder.noticelist = (LinearLayout) convertView.findViewById(R.id.noticelist);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_notice_title);
            holder.tv_desc = (TextView) convertView.findViewById(R.id.tv_notice_content);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_notice_time);

            convertView.setTag(holder);
        } else {// 直接获得ViewHolder
            holder = (ViewHolder) convertView.getTag();
        }

        final ArticleList notice = data.get(position);

        holder.tv_title.setText(notice.getArticleTitle());
        holder.tv_desc.setText(notice.getArticleContent());
        holder.tv_time.setText(DateUtils.getStandardDate(notice.getArticleTime()));


        return convertView;
    }

    static class ViewHolder {
        LinearLayout noticelist;
        //  标题
        TextView tv_title;
        //  内容
        TextView tv_desc;
        //  时间
        TextView tv_time;
    }

}