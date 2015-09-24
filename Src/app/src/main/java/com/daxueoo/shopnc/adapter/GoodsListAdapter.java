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
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.cache.ImageCacheManger;
import com.daxueoo.shopnc.model.GoodsMessage;
import com.daxueoo.shopnc.ui.activity.ImagePreviewActivity;
import com.daxueoo.shopnc.ui.activity.WebViewActivity;
import com.daxueoo.shopnc.utils.ConstUtils;
import com.daxueoo.shopnc.utils.SystemUtils;

import java.util.List;

/**
 * Created by guodont on 15/9/11.
 */
public class GoodsListAdapter extends BaseAdapter {

    private Context mContext = null;
    public List<GoodsMessage> data;
    private ImageLoader mImageLoader;

    public GoodsListAdapter(Context mContext, List<GoodsMessage> data) {
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
    public View getView(int i, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_shop, parent, false);
            holder = new ViewHolder();
            holder.ll_item_shop = (LinearLayout) convertView.findViewById(R.id.ll_item_shop);
            holder.tv_shop_title = (TextView) convertView.findViewById(R.id.tv_shop_title);
//            holder.tv_shop_store = (TextView) convertView.findViewById(R.id.tv_shop_store);
            holder.tv_shop_price = (TextView) convertView.findViewById(R.id.tv_shop_price);
            holder.iv_shop_img = (ImageView) convertView.findViewById(R.id.iv_shop_img);
            holder.tv_shop_buy_count = (TextView) convertView.findViewById(R.id.tv_shop_buy_count);
            holder.iv_shop_star = (ImageView) convertView.findViewById(R.id.iv_shop_star);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        final GoodsMessage msg = data.get(i);

        holder.tv_shop_title.setText(msg.getGoods_name());
        holder.tv_shop_price.setText(msg.getGoods_price());
        holder.tv_shop_buy_count.setText(msg.getGoods_salenum());
        Log.e("GOODSMESSAGE",msg.toString());
        //设置商品星级显示
        if (msg.getEvaluation_good_star() == null || msg.getEvaluation_good_star().equals("")) {
            holder.iv_shop_star.setImageResource(R.drawable.icon_star_0);
        } else {
            switch (msg.getEvaluation_good_star()) {
                case "0":
                    holder.iv_shop_star.setImageResource(R.drawable.icon_star_0);
                    break;
                case "1":
                    holder.iv_shop_star.setImageResource(R.drawable.icon_star_1);
                    break;
                case "2":
                    holder.iv_shop_star.setImageResource(R.drawable.icon_star_2);
                    break;
                case "3":
                    holder.iv_shop_star.setImageResource(R.drawable.icon_star_3);
                    break;
                case "4":
                    holder.iv_shop_star.setImageResource(R.drawable.icon_star_4);
                    break;
                case "5":
                    holder.iv_shop_star.setImageResource(R.drawable.icon_star_5);
                    break;
                default:
                    holder.iv_shop_star.setImageResource(R.drawable.icon_star_0);
                    break;
            }
        }
        //设置商品图片
        if (msg.getGoods_image_url() == null || msg.getGoods_image_url().equals("")) {
            holder.iv_shop_img.setImageResource(R.drawable.icon_shopping_normal);
        } else {
            ImageCacheManger.loadImage(msg.getGoods_image_url(), holder.iv_shop_img, SystemUtils.getBitmapFromResources((Activity) mContext, R.drawable.icon_shopping_normal), SystemUtils.getBitmapFromResources((Activity) mContext, R.drawable.icon_shopping_normal));
        }

        final Intent intent = new Intent();
        //  图片的点击事件
        holder.iv_shop_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(mContext, ImagePreviewActivity.class);
                intent.putExtra("pic_url", msg.getGoods_image_url());
                mContext.startActivity(intent);
            }
        });

        //  item的点击事件 进入商品详情 webView
        holder.ll_item_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 进入商品详情 webView
                intent.setClass(mContext, WebViewActivity.class);
                intent.putExtra("type", ConstUtils.WEB_TYPE_MALL);
                intent.putExtra("url", ConstUtils.GOODS_DETAIL_WEB + msg.getGoods_id());
                Log.e("GOODSURL",ConstUtils.GOODS_DETAIL_WEB + msg.getGoods_id());
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        LinearLayout ll_item_shop;
        ImageView iv_shop_img;
        TextView tv_shop_title;
        TextView tv_shop_price;
        //        TextView tv_shop_store;
        TextView tv_shop_buy_count;
        ImageView iv_shop_star;
    }
}
