package com.daxueoo.shopnc.adapter;

import java.util.List;

import android.content.Context;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.daxueoo.shopnc.bean.Reply;
import com.daxueoo.shopnc.cache.BitmapCache;
import com.daxueoo.shopnc.model.Comment;
import com.daxueoo.shopnc.model.User;
import com.daxueoo.shopnc.utils.DateUtils;
import com.daxueoo.shopnc.utils.StringUtils;

public class ThemeCommentAdapter extends BaseAdapter {

    private Context context;
    private List<Reply> comments;

    private ImageLoader mImageLoader;

    public ThemeCommentAdapter(Context context, List<Reply> comments) {
        this.context = context;
        this.comments = comments;

        RequestQueue mQueue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(mQueue, new BitmapCache());
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Reply getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderList holder;
        if (convertView == null) {
            holder = new ViewHolderList();
            convertView = View.inflate(context, R.layout.item_comment, null);
            holder.ll_comments = (LinearLayout) convertView.findViewById(R.id.ll_comments);
            holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
            holder.tv_user_name = (TextView) convertView.findViewById(R.id.tv_user_name);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_comment = (TextView) convertView.findViewById(R.id.tv_comment);
            holder.tv_floor = (TextView) convertView.findViewById(R.id.tv_comment_floor);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderList) convertView.getTag();
        }

        final Reply reply = getItem(position);
        holder.tv_user_name.setText(reply.getMemberName());
        holder.tv_time.setText(DateUtils.getStandardDate(reply.getReplyAddtime()));
        holder.tv_floor.setText(String.valueOf(position + 1) + "楼");
        SpannableString commentContent = StringUtils.getWeiboContent(context, holder.tv_comment, reply.getReplyContent());
        holder.tv_comment.setText(commentContent);
//        holder.ll_comments.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //TODO
//                Toast.makeText(context, "TODO回复评论", Toast.LENGTH_SHORT).show();
//                // 返回id和名字
//
//            }
//        });

        if (reply.getMemberAvatar() == null || reply.getMemberAvatar().equals("")) {
            //没有头像数据
            holder.iv_avatar.setVisibility(View.GONE);
        } else {
            ImageLoader.ImageListener listener = ImageLoader.getImageListener(holder.iv_avatar, android.R.drawable.ic_menu_rotate, R.mipmap.ic_launcher);
            mImageLoader.get(reply.getMemberAvatar(), listener);
        }
        return convertView;
    }

    public static class ViewHolderList {
        public LinearLayout ll_comments;
        public ImageView iv_avatar;
        public TextView tv_user_name;
        public TextView tv_time;
        public TextView tv_comment;
        public TextView tv_floor;
    }

}
