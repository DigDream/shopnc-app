package com.daxueoo.shopnc.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CircleThemeList {

    @Expose
    private Integer code;
    @Expose
    private Boolean hasmore;
    @SerializedName("page_total")
    @Expose
    private Integer pageTotal;
    @Expose
    private ThemeListData datas;

    /**
     *
     * @return
     * The code
     */
    public Integer getCode() {
        return code;
    }

    /**
     *
     * @param code
     * The code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     *
     * @return
     * The hasmore
     */
    public Boolean getHasmore() {
        return hasmore;
    }

    /**
     *
     * @param hasmore
     * The hasmore
     */
    public void setHasmore(Boolean hasmore) {
        this.hasmore = hasmore;
    }

    /**
     *
     * @return
     * The pageTotal
     */
    public Integer getPageTotal() {
        return pageTotal;
    }

    /**
     *
     * @param pageTotal
     * The page_total
     */
    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    /**
     *
     * @return
     * The datas
     */
    public ThemeListData getDatas() {
        return datas;
    }

    /**
     *
     * @param datas
     * The datas
     */
    public void setDatas(ThemeListData datas) {
        this.datas = datas;
    }

}