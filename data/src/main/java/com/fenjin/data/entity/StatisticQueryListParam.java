package com.fenjin.data.entity;

/**
 * Author:kongguoguang
 * Date:2019/8/5
 * Time:16:51
 * Summary:获取统计查询列表参数
 */
public class StatisticQueryListParam {
    private int type;

    private String sandName;

    private String sh;

    private String startTime;

    private String endTime;

    private int pageNum;

    private int pageSize;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSandName() {
        return sandName;
    }

    public void setSandName(String sandName) {
        this.sandName = sandName;
    }

    public String getSh() {
        return sh;
    }

    public void setSh(String sh) {
        this.sh = sh;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
