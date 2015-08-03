package com.daxueoo.shopnc.model;

import java.io.Serializable;

/**
 * Created by user on 15-8-3.
 */
public class UserMessage implements Serializable {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String id;
    String name;

}
