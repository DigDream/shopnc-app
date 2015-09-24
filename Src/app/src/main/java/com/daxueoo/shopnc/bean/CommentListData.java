package com.daxueoo.shopnc.bean;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentListData {

    @Expose
    private List<Reply> replys = new ArrayList<Reply>();
    @SerializedName("member_list")
    @Expose
    private List<Object> memberList = new ArrayList<Object>();
    @SerializedName("theme_nolike")
    @Expose
    private Integer themeNolike;

    /**
     * @return The replys
     */
    public List<Reply> getReplys() {
        return replys;
    }

    /**
     * @param replys The replys
     */
    public void setReplys(List<Reply> replys) {
        this.replys = replys;
    }

    /**
     * @return The memberList
     */
    public List<Object> getMemberList() {
        return memberList;
    }

    /**
     * @param memberList The member_list
     */
    public void setMemberList(List<Object> memberList) {
        this.memberList = memberList;
    }

    /**
     * @return The themeNolike
     */
    public Integer getThemeNolike() {
        return themeNolike;
    }

    /**
     * @param themeNolike The theme_nolike
     */
    public void setThemeNolike(Integer themeNolike) {
        this.themeNolike = themeNolike;
    }

}