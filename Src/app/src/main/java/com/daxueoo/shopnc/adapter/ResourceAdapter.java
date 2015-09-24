package com.daxueoo.shopnc.adapter;

/**
 * Created by user on 15-9-9.
 */

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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.model.CircleMessage;
import com.daxueoo.shopnc.model.ExploreMessage;
import com.daxueoo.shopnc.scan.CaptureActivity;
import com.daxueoo.shopnc.ui.activity.NoticeListActivity;
import com.daxueoo.shopnc.ui.activity.QaActivity;
import com.daxueoo.shopnc.ui.activity.TaskActivity;
import com.daxueoo.shopnc.ui.activity.ThemeListActivity;
import com.daxueoo.shopnc.cache.BitmapCache;
import com.daxueoo.shopnc.ui.activity.TradeActivity;

import java.util.List;

/**
 * Created by user on 15-8-4.
 */

public class ResourceAdapter extends BaseAdapter {
    /**
     * 上下文对象
     */
    private Context mContext = null;
    public List<ExploreMessage> data;

    private ImageLoader mImageLoader;


    public ResourceAdapter(Context ctx, List<ExploreMessage> data) {
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

            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_resource, parent, false);
            holder = new ViewHolder();
            holder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.rl_explore);

            holder.iv_trade = (ImageView) convertView.findViewById(R.id.itemimageview);

            holder.tv_explore_name = (TextView) convertView.findViewById(R.id.tv_explore_name);
            convertView.setTag(holder);
        } else {//  直接获得ViewHolder
            holder = (ViewHolder) convertView.getTag();
        }

        final ExploreMessage msg = data.get(position);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Log.e("tag", String.valueOf(position));
                switch (position) {
                    case 0:
                        //  扫码识物
                        intent.setClass(mContext, CaptureActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 1:
                        //  任务管理
                        intent.setClass(mContext, TaskActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 2:
                        //  交易大厅
                        intent.setClass(mContext, TradeActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 6:
                        //  跳转到问专家界面
                        intent.setClass(mContext, QaActivity.class);
                        intent.putExtra("QaCircleId", "0");
                        intent.putExtra("QaType", "6");
                        mContext.startActivity(intent);
                        break;
                    case 5:
                        //  问达人
                        intent.setClass(mContext, QaActivity.class);
                        intent.putExtra("QaCircleId","0");
                        intent.putExtra("QaType", "5");
                        mContext.startActivity(intent);
                        break;
                    case 7:
                        //  资讯
                        intent.setClass(mContext, NoticeListActivity.class);
                        mContext.startActivity(intent);
                        break;
                    default:
                        break;
                }


            }
        });

        holder.tv_explore_name.setText(msg.getExplore_name());

        holder.iv_trade.setImageResource(msg.getIcon_res());


        return convertView;
    }

    static class ViewHolder {

        RelativeLayout relativeLayout;

        ImageView iv_trade;

        TextView tv_explore_name;
    }

}