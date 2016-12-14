package com.choyri.entity;

public class User {
    private int id;
    private String account;
    private String uname;
    private String pwd;
    private int type;

    public User() {
    }

    public User(String account, String uname, String pwd, int type) {
        this.account = account;
        this.uname = uname;
        this.pwd = pwd;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", account=" + account + ", uname=" + uname + ", pwd=" + pwd + ", type=" + type + "]";
    }
}