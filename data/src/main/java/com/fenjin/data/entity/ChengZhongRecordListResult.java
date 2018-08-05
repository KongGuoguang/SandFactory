package com.fenjin.data.entity;

import java.util.List;

/**
 * Author:kongguoguang
 * Date:2018-08-02
 * Time:19:53
 * Summary:
 */
public class ChengZhongRecordListResult {

    private int flag;

    private String message;

    private Result result;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

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
}
