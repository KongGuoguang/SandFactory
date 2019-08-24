package com.fenjin.data.entity;

/**
 * Author:kongguoguang
 * Date:2019/8/5
 * Time:16:36
 * Summary:获取统计查询总数参数
 */
public class StatisticQueryCountParam {

    private String sandName;

    private String sh;

    private String startTime;

    private String endTime;

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
}
