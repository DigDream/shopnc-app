package com.daxueoo.shopnc.model;

/**
 * 话题的模型类
 * Created by user on 15-8-2.
 */
public class TopicMessage extends BaseMessage {

    String icon_url;
    String topic_id;
    String topic_title;
    String topic_content;
    String topic_user_name;
    String topic_views;
    String topic_replies;
    String topic_likes;
    String topic_time;
    String topic_user_icon;
    String topic_user_id;

    public String getTopic_circle_id() {
        return topic_circle_id;
    }

    public void setTopic_circle_id(String topic_circle_id) {
        this.topic_circle_id = topic_circle_id;
    }

    String topic_circle_id;

    public TopicMessage() {

    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    @Override
    public String toString() {
        return this.topic_title + this.topic_content + this.topic_views + this.topic_replies + this.topic_time + this.icon_url + this.topic_likes +this.topic_user_name;
    }

    public TopicMessage(String topic_title, String topic_content, String topic_user_name, String topic_views, String topic_time, String topic_replies, String topic_likes) {
        this.topic_title = topic_title;
        this.topic_content = topic_content;
        this.topic_views = topic_views;
        this.topic_user_name = topic_user_name;
        this.topic_replies = topic_replies;
        this.topic_likes = topic_likes;
        this.topic_time = topic_time;
    }

    public String getTopic_user_name() {
        return topic_user_name;
    }

    public void setTopic_user_name(String topic_user_name) {
        this.topic_user_name = topic_user_name;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getTopic_title() {
        return topic_title;
    }

    public void setTopic_title(String topic_title) {
        this.topic_title = topic_title;
    }

    public String getTopic_content() {
        return topic_content;
    }

    public void setTopic_content(String topic_content) {
        this.topic_content = topic_content;
    }

    public String getTopic_views() {
        return topic_views;
    }

    public void setTopic_views(String topic_views) {
        this.topic_views = topic_views;
    }

    public String getTopic_replies() {
        return topic_replies;
    }

    public void setTopic_replies(String topic_replies) {
        this.topic_replies = topic_replies;
    }

    public String getTopic_time() {
        return topic_time;
    }

    public void setTopic_time(String topic_time) {
        this.topic_time = topic_time;
    }

    public String getTopic_user_icon() {
        return topic_user_icon;
    }

    public void setTopic_user_icon(String topic_user_icon) {
        this.topic_user_icon = topic_user_icon;
    }

    public String getTopic_likes() {
        return topic_likes;
    }

    public void setTopic_likes(String topic_likes) {
        this.topic_likes = topic_likes;
    }

    public String getTopic_user_id() {
        return topic_user_id;
    }

    public void setTopic_user_id(String topic_user_id) {
        this.topic_user_id = topic_user_id;
    }


}