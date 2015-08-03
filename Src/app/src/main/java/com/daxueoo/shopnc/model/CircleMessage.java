package com.daxueoo.shopnc.model;

import com.daxueoo.shopnc.ui.fragment.CircleFragment;

/**
 * Created by user on 15-8-4.
 */
public class CircleMessage {
    String left_url;
    String left_title;
    String left_content;
    String right_url;
    String right_title;
    String right_content;

    public CircleMessage(String left_url, String left_title, String left_content, String right_url, String right_title, String right_content) {
        this.left_url = left_url;
        this.left_title = left_title;
        this.left_content = left_content;
        this.right_url = right_url;
        this.right_title = right_title;
        this.right_content = right_content;
    }

    public String getLeft_url() {
        return left_url;
    }

    public void setLeft_url(String left_url) {
        this.left_url = left_url;
    }

    public String getLeft_title() {
        return left_title;
    }

    public void setLeft_title(String left_title) {
        this.left_title = left_title;
    }

    public String getLeft_content() {
        return left_content;
    }

    public void setLeft_content(String left_content) {
        this.left_content = left_content;
    }

    public String getRight_url() {
        return right_url;
    }

    public void setRight_url(String right_url) {
        this.right_url = right_url;
    }

    public String getRight_title() {
        return right_title;
    }

    public void setRight_title(String right_title) {
        this.right_title = right_title;
    }

    public String getRight_content() {
        return right_content;
    }

    public void setRight_content(String right_content) {
        this.right_content = right_content;
    }
}
