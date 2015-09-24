package com.daxueoo.shopnc.model;

import android.app.Activity;

/**
 * Created by guodont on 15/9/13.
 */
public class ExploreMessage extends BaseMessage {
    int icon_res;
    String explore_name;
    Activity intent_activity;

    public ExploreMessage(int icon_res, String explore_name, Activity intent_activity) {
        this.icon_res = icon_res;
        this.explore_name = explore_name;
        this.intent_activity = intent_activity;
    }

    public ExploreMessage() {

    }

    public int getIcon_res() {
        return icon_res;
    }

    public void setIcon_res(int icon_res) {
        this.icon_res = icon_res;
    }

    public String getExplore_name() {
        return explore_name;
    }

    public void setExplore_name(String explore_name) {
        this.explore_name = explore_name;
    }

    public Activity getIntent_activity() {
        return intent_activity;
    }

    public void setIntent_activity(Activity intent_activity) {
        this.intent_activity = intent_activity;
    }
}
