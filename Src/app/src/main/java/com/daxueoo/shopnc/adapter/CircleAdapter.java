package com.daxueoo.shopnc.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.daxueoo.shopnc.model.CircleMessage;
import com.daxueoo.shopnc.ui.activity.ThemeListActivity;
import com.daxueoo.shopnc.cache.BitmapCache;

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
            holder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.rl_circle);

            holder.rl_text = (RelativeLayout) convertView.findViewById(R.id.rl_text);
            holder.circle_title = (TextView) convertView.findViewById(R.id.tv_circle_title);
            holder.circle_desc = (TextView) convertView.findViewById(R.id.tv_circle_desc);
            holder.circle_people = (TextView) convertView.findViewById(R.id.tv_circle_people);

            holder.iv_circle = (NetworkImageView) convertView.findViewById(R.id.itemimageview);

            convertView.setTag(holder);
        } else {//  直接获得ViewHolder
            holder = (ViewHolder) convertView.getTag();
        }



        final CircleMessage msg = data.get(position);

        holder.circle_title.setText(msg.getTitle());
        holder.circle_desc.setText(msg.getContent());
        holder.circle_people.setText(msg.getPeople());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  跳转到话题列表activity
                Intent intent = new Intent();
                intent.putExtra("CircleId", msg.getId());
                intent.putExtra("CircleName",msg.getTitle());
                intent.setClass(mContext, ThemeListActivity.class);
                mContext.startActivity(intent);
                Log.e("tag", msg.getId());
            }
        });
        if (msg.getUrl() == null || msg.getUrl().equals("")) {
            //  holder.iv_circle.setVisibility(View.GONE);
            holder.iv_circle.setImageResource(R.drawable.default_group_logo);
        } else {
            holder.iv_circle.setDefaultImageResId(R.drawable.default_group_logo);
            holder.iv_circle.setErrorImageResId(R.drawable.default_group_logo);
            holder.iv_circle.setImageUrl(msg.getUrl(), mImageLoader);
        }
        holder.rl_text.setAlpha(155);

//        holder.iv_circle.setImageAlpha(155);
//        holder.iv_circle.setAlpha(155);

        return convertView;
    }

    static class ViewHolder {

        RelativeLayout relativeLayout;
        RelativeLayout rl_text;
        TextView circle_title;
        TextView circle_desc;
        TextView circle_people;

        NetworkImageView iv_circle;
    }

}