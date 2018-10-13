package com.fenjin.data.entity;

public class TodayCountResult {

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

        private int todayNo;

        private float todayWeight;

        public int getTodayNo() {
            return todayNo;
        }

        public void setTodayNo(int todayNo) {
            this.todayNo = todayNo;
        }

        public float getTodayWeight() {
            return todayWeight;
        }

        public void setTodayWeight(float todayWeight) {
            this.todayWeight = todayWeight;
        }
    }

}
