package com.manage.model;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {

    //用于显示页码的属性。我们的需求是页面上最多只显示9个页码。当前页在允许的情况下永远居中
    private int beginPageNum;
    private int endPageNum;

    private int currentPageNum = 1;//当前页
    private int pageSize = 20;//每页显示的条数
    private int totalRecords;//总记录条数
    private int startIndex;//查询的开始记录索引
    private int totalPageNum;//总页数
    private int prePageNum;//上一页
    private int nextPageNum;//下一页

    private List<T> records;//


    public static Page build(int currentPageNum, int totalRecords){
        return new Page(currentPageNum, totalRecords);

    }

    public Page(){
    }

    public void buildPage(int totalRecords){
        buildPage(this.currentPageNum,totalRecords);
    }
    public void buildPage(int currentPageNum, int totalRecords){
        this.currentPageNum = currentPageNum;
        this.totalRecords = totalRecords;

        //计算开始记录索引
        startIndex = (currentPageNum - 1) * pageSize;

        //计算总页数
        totalPageNum = totalRecords % pageSize == 0 ? totalRecords / pageSize : totalRecords / pageSize + 1;

        //计算页号
        if (totalPageNum < 9) {
            beginPageNum = 1;
            endPageNum = totalPageNum;
        } else {
            beginPageNum = currentPageNum - 4;
            endPageNum = currentPageNum + 4;
            if (beginPageNum < 1) {
                beginPageNum = 1;
                endPageNum = beginPageNum + 8;
            }
            if (endPageNum > totalPageNum) {
                endPageNum = totalPageNum;
                beginPageNum = endPageNum - 8;
            }
        }
    }
    /**
     * 要想使用此类，必须提供2个参数
     *
     * @param currentPageNum 当前页
     * @param totalRecords   总记录条数
     */
    public Page(int currentPageNum, int totalRecords) {
        buildPage(currentPageNum,totalRecords);

    }

    public int getPrePageNum() {
        //计算上一页
        prePageNum = currentPageNum - 1;
        //如果当前页是第一页，上一页还是第一页
        return prePageNum < 1 ? 1 : prePageNum;
    }

    public int getNextPageNum() {
        //计算下一页
        nextPageNum = currentPageNum + 1;
        //如果当前页是最后一页，下一页还是最后一页
        return nextPageNum > totalPageNum ? totalPageNum : nextPageNum;
    }

    //-----------------------------------------------------


    public int getBeginPageNum() {
        return beginPageNum;
    }

    public void setBeginPageNum(int beginPageNum) {
        this.beginPageNum = beginPageNum;
    }

    public int getEndPageNum() {
        return endPageNum;
    }

    public void setEndPageNum(int endPageNum) {
        this.endPageNum = endPageNum;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "Page{" +
                "currentPageNum=" + currentPageNum +
                ", pageSize=" + pageSize +
                ", totalRecords=" + totalRecords +
                ", startIndex=" + startIndex +
                ", totalPageNum=" + totalPageNum +
                ", prePageNum=" + prePageNum +
                ", nextPageNum=" + nextPageNum +
                ", records=" + records +
                ", beginPageNum=" + beginPageNum +
                ", endPageNum=" + endPageNum +
                '}';
    }
}
