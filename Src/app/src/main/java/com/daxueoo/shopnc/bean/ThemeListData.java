package com.daxueoo.shopnc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class ThemeListData implements Serializable {

    @Expose
    private List<Theme> themes = new ArrayList<Theme>();

    /**
     * @return The themes
     */
    public List<Theme> getThemes() {
        return themes;
    }

    /**
     * @param themes The themes
     */
    public void setThemes(List<Theme> themes) {
        this.themes = themes;
    }

}