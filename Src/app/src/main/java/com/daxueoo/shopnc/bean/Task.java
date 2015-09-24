package com.daxueoo.shopnc.bean;

/**
 * Created by user on 15-9-9.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Task implements Serializable {

    @SerializedName("article_id")
    @Expose
    private String articleId;
    @SerializedName("article_title")
    @Expose
    private String articleTitle;
    @SerializedName("article_content")
    @Expose
    private String articleContent;
    @SerializedName("article_tag")
    @Expose
    private String articleTag;
    @SerializedName("article_state")
    @Expose
    private String articleState;
    @SerializedName("article_publish_time")
    @Expose
    private String articlePublishTime;

    /**
     * @return The articleId
     */
    public String getArticleId() {
        return articleId;
    }

    /**
     * @param articleId The article_id
     */
    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    /**
     * @return The articleTitle
     */
    public String getArticleTitle() {
        return articleTitle;
    }

    /**
     * @param articleTitle The article_title
     */
    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    /**
     * @return The articleContent
     */
    public String getArticleContent() {
        return articleContent;
    }

    /**
     * @param articleContent The article_content
     */
    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    /**
     * @return The articleTag
     */
    public String getArticleTag() {
        return articleTag;
    }

    /**
     * @param articleTag The article_tag
     */
    public void setArticleTag(String articleTag) {
        this.articleTag = articleTag;
    }

    /**
     * @return The articleState
     */
    public String getArticleState() {
        return articleState;
    }

    /**
     * @param articleState The article_state
     */
    public void setArticleState(String articleState) {
        this.articleState = articleState;
    }

    /**
     * @return The articlePublishTime
     */
    public String getArticlePublishTime() {
        return articlePublishTime;
    }

    /**
     * @param articlePublishTime The article_publish_time
     */
    public void setArticlePublishTime(String articlePublishTime) {
        this.articlePublishTime = articlePublishTime;
    }

}