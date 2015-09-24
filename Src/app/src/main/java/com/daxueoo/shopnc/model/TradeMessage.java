package com.daxueoo.shopnc.model;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by guodont on 15/9/10.
 */
public class TradeMessage extends BaseMessage implements Serializable {


    String trade_id;
    //  交易配图
    String trade_img;
    //  收藏按钮
    String trade_like;
    //  交易标题
    String trade_title;
    //  交易描述
    String trade_desc;
    //  交易价格
    String trade_price;
    //  浏览数
    String trade_views;
    //  用户名
    String trade_username;
    //  发布时间
    String trade_time;
    //  用户头像
    String trade_user_avatar;
    //联系人
    String trade_pname;
    //手机号
    String trade_pphone;

    //  用户id
    String trade_user_id;

    public TradeMessage() {

    }

    @Override
    public String toString() {
        return "TradeMessage{" +
                "trade_id='" + trade_id + '\'' +
                ", trade_img='" + trade_img + '\'' +
                ", trade_like='" + trade_like + '\'' +
                ", trade_title='" + trade_title + '\'' +
                ", trade_desc='" + trade_desc + '\'' +
                ", trade_price='" + trade_price + '\'' +
                ", trade_views='" + trade_views + '\'' +
                ", trade_username='" + trade_username + '\'' +
                ", trade_time='" + trade_time + '\'' +
                ", trade_user_avatar='" + trade_user_avatar + '\'' +
                ", trade_pname='" + trade_pname + '\'' +
                ", trade_pphone='" + trade_pphone + '\'' +
                ", trade_user_id='" + trade_user_id + '\'' +
                '}';
    }

    public TradeMessage(String trade_title, String trade_desc, String trade_price, String trade_views, String trade_username, String trade_time,String trade_pname,String trade_pphone) {
        this.trade_title = trade_title;
        this.trade_desc = trade_desc;
        this.trade_price = trade_price;
        this.trade_views = trade_views;
        this.trade_username = trade_username;
        this.trade_time = trade_time;
        this.trade_pphone = trade_pphone;
        this.trade_pname = trade_pname;
    }

    public String getTrade_img() {
        return trade_img;
    }

    public void setTrade_img(String trade_img) {
        this.trade_img = trade_img;
    }

    public String getTrade_like() {
        return trade_like;
    }

    public void setTrade_like(String trade_like) {
        this.trade_like = trade_like;
    }

    public String getTrade_title() {
        return trade_title;
    }

    public void setTrade_title(String trade_title) {
        this.trade_title = trade_title;
    }

    public String getTrade_desc() {
        return trade_desc;
    }

    public void setTrade_desc(String trade_desc) {
        this.trade_desc = trade_desc;
    }

    public String getTrade_price() {
        return trade_price;
    }

    public void setTrade_price(String trade_price) {
        this.trade_price = trade_price;
    }

    public String getTrade_views() {
        return trade_views;
    }

    public void setTrade_views(String trade_views) {
        this.trade_views = trade_views;
    }

    public String getTrade_username() {
        return trade_username;
    }

    public void setTrade_username(String trade_username) {
        this.trade_username = trade_username;
    }

    public String getTrade_time() {
        return trade_time;
    }

    public void setTrade_time(String trade_time) {
        this.trade_time = trade_time;
    }

    public String getTrade_user_avatar() {
        return trade_user_avatar;
    }

    public void setTrade_user_avatar(String trade_user_avatar) {
        this.trade_user_avatar = trade_user_avatar;
    }

    public String getTrade_id() {
        return trade_id;
    }

    public void setTrade_id(String trade_id) {
        this.trade_id = trade_id;
    }


    public String getTrade_user_id() {
        return trade_user_id;
    }

    public void setTrade_user_id(String trade_user_id) {
        this.trade_user_id = trade_user_id;
    }

    public String getTrade_pname() {
        return trade_pname;
    }

    public void setTrade_pname(String trade_pname) {
        this.trade_pname = trade_pname;
    }

    public String getTrade_pphone() {
        return trade_pphone;
    }

    public void setTrade_pphone(String trade_pphone) {
        this.trade_pphone = trade_pphone;
    }
}
