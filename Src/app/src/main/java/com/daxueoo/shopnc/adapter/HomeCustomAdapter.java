package com.daxueoo.shopnc.adapter;

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

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.model.ExploreMessage;
import com.daxueoo.shopnc.scan.CaptureActivity;
import com.daxueoo.shopnc.ui.activity.CustomActivity;
import com.daxueoo.shopnc.ui.activity.QaActivity;
import com.daxueoo.shopnc.ui.activity.TaskActivity;
import com.daxueoo.shopnc.ui.activity.TradeActivity;

import java.util.List;

/**
 * Created by guodont on 15/9/19.
 */
public class HomeCustomAdapter extends BaseAdapter {


    private Context mContext = null;
    public List<ExploreMessage> data;

    public HomeCustomAdapter(Context mContext, List<ExploreMessage> data) {
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
    public View getView(final int i, View convertView, ViewGroup viewGroup) {

        ViewHolder holder;

        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gv_home_custum, viewGroup, false);
            holder = new ViewHolder();
            holder.rl_explore = (RelativeLayout) convertView.findViewById(R.id.rl_explore);
            holder.iv_explore = (ImageView) convertView.findViewById(R.id.itemimageview);
            holder.tv_explore_name = (TextView) convertView.findViewById(R.id.tv_explore_name);
            convertView.setTag(holder);
        } else {//  直接获得ViewHolder
            holder = (ViewHolder) convertView.getTag();
        }

        final ExploreMessage msg = data.get(i);

        holder.rl_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                switch (msg.getExplore_name()) {
                    case "我的待办":
                        intent.setClass(mContext, TaskActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case "交易大厅":
                        intent.setClass(mContext, TradeActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case "消息中心":
                        intent.setClass(mContext, TradeActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case "发现更多":
                        intent.setClass(mContext, CustomActivity.class);
                        mContext.startActivity(intent);
                        break;
                    default:
                        break;
                }


            }
        });


        holder.tv_explore_name.setText(msg.getExplore_name());

        holder.iv_explore.setImageResource(msg.getIcon_res());

        return convertView;
    }


    static class ViewHolder {

        RelativeLayout rl_explore;

        ImageView iv_explore;

        TextView tv_explore_name;
    }
}
