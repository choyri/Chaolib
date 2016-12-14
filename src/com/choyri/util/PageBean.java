package com.choyri.util;

import java.util.List;

/**
 * 数据分页
 */
public class PageBean<T> {

    // 每页记录数
    private int perPage = 10;

    // 当前页
    private int currentPage;

    // 总页数
    private int totalPage;

    // 总记录数
    private int totalRecord;

    // 数据
    private List<T> pageData;

    public PageBean() {
    }

    public PageBean(int perPage) {
        this.perPage = perPage;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage() {
        // totalPage = (totalRecord % perPage == 0) ? (totalCount / perPage) : (totalCount / perPage + 1);
        totalPage = (totalRecord + perPage - 1) / perPage;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
        setTotalPage();
    }

    public List<T> getPageData() {
        return pageData;
    }

    public void setPageData(List<T> pageData) {
        this.pageData = pageData;
    }
}