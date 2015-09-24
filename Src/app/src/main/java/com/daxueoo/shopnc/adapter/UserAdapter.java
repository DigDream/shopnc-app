package com.daxueoo.shopnc.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.cache.ImageCacheManger;
import com.daxueoo.shopnc.model.UserMessage;
import com.daxueoo.shopnc.ui.activity.UserInfoActivity;
import com.daxueoo.shopnc.utils.SystemUtils;

import java.util.List;

/**
 * 用户关注粉丝的Adapter
 * Created by user on 15-9-1.
 */
public class UserAdapter extends BaseAdapter {

    private Context context;
    private List<UserMessage> userMessages;

    public UserAdapter(Context context, List<UserMessage> userMessages) {
        this.context = context;
        this.userMessages = userMessages;

    }

    @Override
    public int getCount() {
        return userMessages.size();
    }

    @Override
    public UserMessage getItem(int position) {
        return userMessages.get(position);
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
            convertView = View.inflate(context, R.layout.item_user_follow, null);
            holder.ll_users = (LinearLayout) convertView.findViewById(R.id.ll_users);
            holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
            holder.tv_user_name = (TextView) convertView.findViewById(R.id.tv_user_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderList) convertView.getTag();
        }

        final UserMessage userMessage = getItem(position);
        final Intent intent = new Intent();
        holder.tv_user_name.setText(userMessage.getName());
        holder.ll_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(context, UserInfoActivity.class);
                intent.putExtra("uid", userMessage.getId());
                context.startActivity(intent);
            }
        });

        if (userMessage.getAvatar() == null || userMessage.getAvatar().equals("")) {
            //没有头像数据
            holder.iv_avatar.setVisibility(View.GONE);
        } else {
            ImageCacheManger.loadImage(userMessage.getAvatar(), holder.iv_avatar, SystemUtils.getBitmapFromResources((Activity) context, R.mipmap.ic_no_user), SystemUtils.getBitmapFromResources((Activity) context, R.mipmap.ic_no_user));
        }
        return convertView;
    }

    public static class ViewHolderList {
        public LinearLayout ll_users;
        public ImageView iv_avatar;
        public TextView tv_user_name;
    }
}
