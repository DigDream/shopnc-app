package com.daxueoo.shopnc.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArticleList {

    @SerializedName("article_id")
    @Expose
    private String articleId;
    @SerializedName("ac_id")
    @Expose
    private String acId;
    @SerializedName("article_url")
    @Expose
    private String articleUrl;
    @SerializedName("article_show")
    @Expose
    private String articleShow;
    @SerializedName("article_sort")
    @Expose
    private String articleSort;
    @SerializedName("article_title")
    @Expose
    private String articleTitle;
    @SerializedName("article_content")
    @Expose
    private String articleContent;
    @SerializedName("article_time")
    @Expose
    private String articleTime;

    /**
     *
     * @return
     * The articleId
     */
    public String getArticleId() {
        return articleId;
    }

    /**
     *
     * @param articleId
     * The article_id
     */
    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    /**
     *
     * @return
     * The acId
     */
    public String getAcId() {
        return acId;
    }

    /**
     *
     * @param acId
     * The ac_id
     */
    public void setAcId(String acId) {
        this.acId = acId;
    }

    /**
     *
     * @return
     * The articleUrl
     */
    public String getArticleUrl() {
        return articleUrl;
    }

    /**
     *
     * @param articleUrl
     * The article_url
     */
    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    /**
     *
     * @return
     * The articleShow
     */
    public String getArticleShow() {
        return articleShow;
    }

    /**
     *
     * @param articleShow
     * The article_show
     */
    public void setArticleShow(String articleShow) {
        this.articleShow = articleShow;
    }

    /**
     *
     * @return
     * The articleSort
     */
    public String getArticleSort() {
        return articleSort;
    }

    /**
     *
     * @param articleSort
     * The article_sort
     */
    public void setArticleSort(String articleSort) {
        this.articleSort = articleSort;
    }

    /**
     *
     * @return
     * The articleTitle
     */
    public String getArticleTitle() {
        return articleTitle;
    }

    /**
     *
     * @param articleTitle
     * The article_title
     */
    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    /**
     *
     * @return
     * The articleContent
     */
    public String getArticleContent() {
        return articleContent;
    }

    /**
     *
     * @param articleContent
     * The article_content
     */
    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    /**
     *
     * @return
     * The articleTime
     */
    public String getArticleTime() {
        return articleTime;
    }

    /**
     *
     * @param articleTime
     * The article_time
     */
    public void setArticleTime(String articleTime) {
        this.articleTime = articleTime;
    }

}