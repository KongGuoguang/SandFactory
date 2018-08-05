package com.fenjin.data.bean;

import com.fenjin.data.entity.ChengZhongRecord;

import java.util.List;

/**
 * Author:kongguoguang
 * Date:2018-08-02
 * Time:19:57
 * Summary:
 */
public class Result {

    private int total;

    private List<ChengZhongRecord> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ChengZhongRecord> getList() {
        return list;
    }

    public void setList(List<ChengZhongRecord> list) {
        this.list = list;
    }
}
