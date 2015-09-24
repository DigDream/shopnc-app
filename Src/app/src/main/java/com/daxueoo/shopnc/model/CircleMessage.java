package com.daxueoo.shopnc.model;

import com.daxueoo.shopnc.ui.fragment.CircleFragment;

/**
 * 圈子界面
 * Created by user on 15-8-4.
 */
public class CircleMessage extends BaseMessage {

    String id;
    String url;
    String title;
    String content;
    String people;

    public CircleMessage(String url, String title, String content, String people) {
        this.url = url;
        this.title = title;
        this.content = content;
        this.people = people;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
