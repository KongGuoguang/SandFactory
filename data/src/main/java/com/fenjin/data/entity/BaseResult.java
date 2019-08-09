package com.fenjin.data.entity;

/**
 * Author:kongguoguang
 * Date:2019/8/7
 * Time:9:06
 * Summary:
 */
public class BaseResult<T> {
    private int flag;

    private T result;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
