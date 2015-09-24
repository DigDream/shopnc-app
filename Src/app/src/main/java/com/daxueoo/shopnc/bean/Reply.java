package com.daxueoo.shopnc.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reply {

    @SerializedName("theme_id")
    @Expose
    private String themeId;
    @SerializedName("reply_id")
    @Expose
    private String replyId;
    @SerializedName("circle_id")
    @Expose
    private String circleId;
    @SerializedName("member_id")
    @Expose
    private String memberId;
    @SerializedName("member_name")
    @Expose
    private String memberName;
    @SerializedName("reply_content")
    @Expose
    private String replyContent;
    @SerializedName("reply_addtime")
    @Expose
    private String replyAddtime;
    @SerializedName("reply_replyid")
    @Expose
    private Object replyReplyid;
    @SerializedName("reply_replyname")
    @Expose
    private Object replyReplyname;
    @SerializedName("is_closed")
    @Expose
    private String isClosed;
    @SerializedName("reply_exp")
    @Expose
    private String replyExp;
    @SerializedName("member_avatar")
    @Expose
    private String memberAvatar;

    /**
     * @return The themeId
     */
    public String getThemeId() {
        return themeId;
    }

    /**
     * @param themeId The theme_id
     */
    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

    /**
     * @return The replyId
     */
    public String getReplyId() {
        return replyId;
    }

    /**
     * @param replyId The reply_id
     */
    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    /**
     * @return The circleId
     */
    public String getCircleId() {
        return circleId;
    }

    /**
     * @param circleId The circle_id
     */
    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }

    /**
     * @return The memberId
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * @param memberId The member_id
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    /**
     * @return The memberName
     */
    public String getMemberName() {
        return memberName;
    }

    /**
     * @param memberName The member_name
     */
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    /**
     * @return The replyContent
     */
    public String getReplyContent() {
        return replyContent;
    }

    /**
     * @param replyContent The reply_content
     */
    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    /**
     * @return The replyAddtime
     */
    public String getReplyAddtime() {
        return replyAddtime;
    }

    /**
     * @param replyAddtime The reply_addtime
     */
    public void setReplyAddtime(String replyAddtime) {
        this.replyAddtime = replyAddtime;
    }

    /**
     * @return The replyReplyid
     */
    public Object getReplyReplyid() {
        return replyReplyid;
    }

    /**
     * @param replyReplyid The reply_replyid
     */
    public void setReplyReplyid(Object replyReplyid) {
        this.replyReplyid = replyReplyid;
    }

    /**
     * @return The replyReplyname
     */
    public Object getReplyReplyname() {
        return replyReplyname;
    }

    /**
     * @param replyReplyname The reply_replyname
     */
    public void setReplyReplyname(Object replyReplyname) {
        this.replyReplyname = replyReplyname;
    }

    /**
     * @return The isClosed
     */
    public String getIsClosed() {
        return isClosed;
    }

    /**
     * @param isClosed The is_closed
     */
    public void setIsClosed(String isClosed) {
        this.isClosed = isClosed;
    }

    /**
     * @return The replyExp
     */
    public String getReplyExp() {
        return replyExp;
    }

    /**
     * @param replyExp The reply_exp
     */
    public void setReplyExp(String replyExp) {
        this.replyExp = replyExp;
    }

    /**
     * @return The memberAvatar
     */
    public String getMemberAvatar() {
        return memberAvatar;
    }

    /**
     * @param memberAvatar The member_avatar
     */
    public void setMemberAvatar(String memberAvatar) {
        this.memberAvatar = memberAvatar;
    }

}