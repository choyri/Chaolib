package com.choyri.entity;

public class Comment {
    private int id;
    private int bid;
    private int uid;
    private String time;
    private String content;

    /**
     * 特殊属性 JSP 中使用
     * 图书名称
     */
    private String btitleForJSP;

    /**
     * 特殊属性 JSP 中使用
     * 用户昵称
     */
    private String unameForJSP;

    /**
     * 特殊属性 JSP 中使用
     * 图书 ISBN
     */
    private String isbnForJSP;

    public Comment() {
    }

    public Comment(int bid, int uid, String content) {
        this.bid = bid;
        this.uid = uid;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBtitleForJSP() {
        return btitleForJSP;
    }

    public void setBtitleForJSP(String btitleForJSP) {
        this.btitleForJSP = btitleForJSP;
    }

    public String getUnameForJSP() {
        return unameForJSP;
    }

    public void setUnameForJSP(String unameForJSP) {
        this.unameForJSP = unameForJSP;
    }

    public String getIsbnForJSP() {
        return isbnForJSP;
    }

    public void setIsbnForJSP(String isbnForJSP) {
        this.isbnForJSP = isbnForJSP;
    }

    @Override
    public String toString() {
        return "Comment [id=" + id + ", bid=" + bid + ", uid=" + uid + ", time=" + time + ", content=" + content
                + ", btitleForJSP=" + btitleForJSP + ", unameForJSP=" + unameForJSP + ", isbnForJSP=" + isbnForJSP
                + "]";
    }
}