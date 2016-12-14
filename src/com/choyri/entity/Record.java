package com.choyri.entity;

public class Record {
    private int id;
    private int bid;
    private int uid;
    private String time;
    private int isreturn;

    /**
     * 特殊属性 JSP 中使用
     * 图书名称
     */
    private String btitleForJSP;

    /**
     * 特殊属性 JSP 中使用
     * 用户名称
     */
    private String unameForJSP;

    /**
     * 特殊属性 JSP 中使用
     * 图书 ISBN
     */
    private String isbnForJSP;

    public Record() {
    }

    public Record(int bid, int uid) {
        this.bid = bid;
        this.uid = uid;
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

    public int getIsreturn() {
        return isreturn;
    }

    public void setIsreturn(int isreturn) {
        this.isreturn = isreturn;
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
        return "Record [id=" + id + ", bid=" + bid + ", uid=" + uid + ", time=" + time + ", isreturn=" + isreturn
                + ", btitleForJSP=" + btitleForJSP + ", unameForJSP=" + unameForJSP + ", isbnForJSP=" + isbnForJSP
                + "]";
    }
}