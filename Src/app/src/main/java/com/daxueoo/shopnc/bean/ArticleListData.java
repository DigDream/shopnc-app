package com.daxueoo.shopnc.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArticleListData {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("datas")
    @Expose
    private ArticleDatas datas;

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
     * The datas
     */
    public ArticleDatas getDatas() {
        return datas;
    }

    /**
     *
     * @param datas
     * The datas
     */
    public void setDatas(ArticleDatas datas) {
        this.datas = datas;
    }

}