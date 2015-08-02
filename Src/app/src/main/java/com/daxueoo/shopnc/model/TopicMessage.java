package com.daxueoo.shopnc.model;

/**
 * Created by user on 15-8-2.
 */
public class TopicMessage {

    String icon_url;
    String topic_title;
    String topic_content;
    String topic_views;
    String topic_replies;
    String topic_time;

    public TopicMessage() {

    }

    public TopicMessage(String topic_title, String topic_content, String topic_views, String topic_time, String topic_replies) {
        this.topic_title = topic_title;
        this.topic_content = topic_content;
        this.topic_views = topic_views;
        this.topic_replies = topic_replies;
        this.topic_time = topic_time;
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

}
