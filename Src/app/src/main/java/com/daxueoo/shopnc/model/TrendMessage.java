package com.daxueoo.shopnc.model;

/**
 * Created by guodont on 15/9/9.
 */
public class TrendMessage extends BaseMessage {


    String trend_id;
    String trend_user_id;
    String trend_user_name;
    String trend_user_avatar;
    String trend_title;
    String trend_content;
    String trend_in_circle;
    String trend_time;
    String trend_img;

    public TrendMessage(){

    }

    public TrendMessage(String trend_user_name, String trend_title, String trend_content, String trend_in_circle, String trend_time) {
        this.trend_user_name = trend_user_name;
        this.trend_title = trend_title;
        this.trend_content = trend_content;
        this.trend_in_circle = trend_in_circle;
        this.trend_time = trend_time;
    }

    @Override
    public String toString() {
        return "TrendMessage{" +
                "trend_user_id='" + trend_user_id + '\'' +
                ", trend_user_name='" + trend_user_name + '\'' +
                ", trend_user_avatar='" + trend_user_avatar + '\'' +
                ", trend_title='" + trend_title + '\'' +
                ", trend_content='" + trend_content + '\'' +
                ", trend_in_circle='" + trend_in_circle + '\'' +
                ", trend_time='" + trend_time + '\'' +
                '}';
    }

    public String getTrend_id() {
        return trend_id;
    }

    public void setTrend_id(String trend_id) {
        this.trend_id = trend_id;
    }

    public String getTrend_user_id() {
        return trend_user_id;
    }

    public void setTrend_user_id(String trend_user_id) {
        this.trend_user_id = trend_user_id;
    }

    public String getTrend_user_name() {
        return trend_user_name;
    }

    public void setTrend_user_name(String trend_user_name) {
        this.trend_user_name = trend_user_name;
    }

    public String getTrend_user_avatar() {
        return trend_user_avatar;
    }

    public void setTrend_user_avatar(String trend_user_avatar) {
        this.trend_user_avatar = trend_user_avatar;
    }

    public String getTrend_title() {
        return trend_title;
    }

    public void setTrend_title(String trend_title) {
        this.trend_title = trend_title;
    }

    public String getTrend_content() {
        return trend_content;
    }

    public void setTrend_content(String trend_content) {
        this.trend_content = trend_content;
    }

    public String getTrend_in_circle() {
        return trend_in_circle;
    }

    public void setTrend_in_circle(String trend_in_circle) {
        this.trend_in_circle = trend_in_circle;
    }

    public String getTrend_time() {
        return trend_time;
    }

    public void setTrend_time(String trend_time) {
        this.trend_time = trend_time;
    }

    public String getTrend_img() {
        return trend_img;
    }

    public void setTrend_img(String trend_img) {
        this.trend_img = trend_img;
    }
}
