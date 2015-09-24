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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.cache.BitmapCache;
import com.daxueoo.shopnc.cache.ImageCacheManger;
import com.daxueoo.shopnc.model.TradeMessage;
import com.daxueoo.shopnc.ui.activity.DetailActivity;
import com.daxueoo.shopnc.ui.activity.ImagePreviewActivity;
import com.daxueoo.shopnc.ui.activity.UserInfoActivity;
import com.daxueoo.shopnc.utils.DateUtils;
import com.daxueoo.shopnc.utils.SystemUtils;

import java.util.List;

/**
 * Created by guodont on 15/9/10.
 */
public class TradeAdapter extends BaseAdapter {

    private Context mContext = null;
    public List<TradeMessage> data;

    private ImageLoader mImageLoader;

    public static final String BUNDLE_KEY_DISPLAY_TYPE = "BUNDLE_KEY_DISPLAY_TYPE";

    public TradeAdapter(Context ctx, List<TradeMessage> data) {
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

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_trade, viewGroup, false);
            holder = new ViewHolder();

            holder.ll_item_trade = (LinearLayout) convertView.findViewById(R.id.ll_item_trade);

            holder.tv_trade_title = (TextView) convertView.findViewById(R.id.tv_trade_title);

            holder.tv_trade_desc = (TextView) convertView.findViewById(R.id.tv_trade_desc);

            holder.tv_trade_price = (TextView) convertView.findViewById(R.id.tv_trade_price);

            holder.tv_trade_views = (TextView) convertView.findViewById(R.id.tv_trade_views);

            holder.tv_trade_username = (TextView) convertView.findViewById(R.id.tv_trade_username);

            holder.tv_trade_time = (TextView) convertView.findViewById(R.id.tv_trade_time);

            holder.iv_trade_img = (ImageView) convertView.findViewById(R.id.iv_trade_img);

            holder.iv_trade_like = (ImageView) convertView.findViewById(R.id.iv_trade_like);

            holder.iv_trade_user_avatar = (ImageView) convertView.findViewById(R.id.iv_trade_user_avatar);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        final TradeMessage msg = data.get(i);
        holder.tv_trade_title.setText(msg.getTrade_title());
        holder.tv_trade_desc.setText(msg.getTrade_desc());
        holder.tv_trade_price.setText(msg.getTrade_price());
        holder.tv_trade_username.setText(msg.getTrade_username());
        holder.tv_trade_time.setText(DateUtils.getStandardDate(msg.getTrade_time()));
        holder.tv_trade_views.setText(msg.getTrade_views());

        if (msg.getTrade_img() == null || msg.getTrade_img().equals("")) {
            holder.iv_trade_img.setImageResource(R.drawable.icon_img_normal);
        } else {
            ImageCacheManger.loadImage(msg.getTrade_img(), holder.iv_trade_img, SystemUtils.getBitmapFromResources((Activity) mContext, R.drawable.icon_img_normal), SystemUtils.getBitmapFromResources((Activity) mContext, R.drawable.icon_img_normal));
        }



        if (msg.getTrade_user_avatar() == null || msg.getTrade_user_avatar().equals("")) {
            holder.iv_trade_user_avatar.setImageResource(R.drawable.ic_img_user_default);
        } else {
            ImageCacheManger.loadImage(msg.getTrade_user_avatar(), holder.iv_trade_user_avatar, SystemUtils.getBitmapFromResources((Activity) mContext, R.mipmap.ic_no_user), SystemUtils.getBitmapFromResources((Activity) mContext, R.mipmap.ic_no_user));
        }


        final Intent intent = new Intent();
        //  图片的点击事件
        holder.iv_trade_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.setClass(mContext, ImagePreviewActivity.class);
                intent.putExtra("pic_url", msg.getTrade_img());
                mContext.startActivity(intent);
            }
        });

        //  标题的点击事件
        holder.tv_trade_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(mContext, DetailActivity.class);
                intent.putExtra("BUNDLE_KEY_DISPLAY_TYPE", 0);
                intent.putExtra("detailId", msg.getTrade_id());
                mContext.startActivity(intent);
                Toast.makeText(mContext, msg.getTrade_title(), Toast.LENGTH_LONG).show();
            }
        });

        //  内容的点击事件，和标题同理
        holder.tv_trade_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(mContext, DetailActivity.class);
                intent.putExtra("BUNDLE_KEY_DISPLAY_TYPE", 0);
                intent.putExtra("detailId", msg.getTrade_id());
                mContext.startActivity(intent);
                Toast.makeText(mContext, msg.getTrade_title(), Toast.LENGTH_LONG).show();
            }
        });

        //  用户头像的点击事件
        holder.iv_trade_user_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(mContext, UserInfoActivity.class);
                intent.putExtra("uid", msg.getTrade_username());
                mContext.startActivity(intent);
            }
        });

        //  用户昵称的点击事件，和头像同理
        holder.tv_trade_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(mContext, UserInfoActivity.class);
                intent.putExtra("uid", msg.getTrade_username());
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    static class ViewHolder{

        LinearLayout ll_item_trade;
        //  交易配图
        ImageView iv_trade_img;
        //  收藏按钮
        ImageView iv_trade_like;
        //  交易标题
        TextView tv_trade_title;
        //  交易描述
        TextView tv_trade_desc;
        //  交易价格
        TextView tv_trade_price;
        //  浏览数
        TextView tv_trade_views;
        //  用户名
        TextView tv_trade_username;
        //  发布时间
        TextView tv_trade_time;
        //  用户头像
        ImageView iv_trade_user_avatar;
    }
}
