package com.fenjin.data.entity;

/**
 * Author:kongguoguang
 * Date:2018-08-02
 * Time:19:51
 * Summary:
 */
public class ChengZhongRecordListParam {
    private String start;

    private String endTime;

    private int pageNo;

    private int pageSize;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
