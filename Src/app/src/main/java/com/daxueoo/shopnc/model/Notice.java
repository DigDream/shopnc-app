package com.daxueoo.shopnc.model;

/**
 * Created by guodont on 15/9/19.
 */
public class Notice {

    private int id;

    private String title;

    private String url;

    public Notice(int id ,String title) {
        this.title = title;
        this.id = id;
    }

    public Notice(int id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
