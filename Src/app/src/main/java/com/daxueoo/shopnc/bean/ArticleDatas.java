
package com.daxueoo.shopnc.bean;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by guodont on 15/9/19.
 */


public class ArticleDatas {

    @SerializedName("article_list")
    @Expose
    private List<ArticleList> articleList = new ArrayList<ArticleList>();
    @SerializedName("article_type_name")
    @Expose
    private String articleTypeName;

    /**
     *
     * @return
     * The articleList
     */
    public List<ArticleList> getArticleList() {
        return articleList;
    }

    /**
     *
     * @param articleList
     * The article_list
     */
    public void setArticleList(List<ArticleList> articleList) {
        this.articleList = articleList;
    }

    /**
     *
     * @return
     * The articleTypeName
     */
    public String getArticleTypeName() {
        return articleTypeName;
    }

    /**
     *
     * @param articleTypeName
     * The article_type_name
     */
    public void setArticleTypeName(String articleTypeName) {
        this.articleTypeName = articleTypeName;
    }

}