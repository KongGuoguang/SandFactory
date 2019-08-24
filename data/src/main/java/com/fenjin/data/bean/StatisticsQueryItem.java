package com.fenjin.data.bean;

import android.databinding.ObservableField;

/**
 * Author:kongguoguang
 * Date:2019/8/24
 * Time:15:47
 * Summary:
 */
public class StatisticsQueryItem {

    private String sandName;// 沙场名称（站点）
    private String sh;// 客户单位名称（收货单位）
    private float weight;
    private int car;
    private float money;
    private float balance;

    public ObservableField<Boolean> checked = new ObservableField<>();

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

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getCar() {
        return car;
    }

    public void setCar(int car) {
        this.car = car;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
