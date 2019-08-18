package com.fenjin.data.bean;

/**
 * Author:kongguoguang
 * Date:2019/8/7
 * Time:11:03
 * Summary:
 */
public class ChartStatisticsItem {
    private String month;

    private float weight;

    private float money;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }


    @Override
    public String toString() {
        return "ChartStatisticsItem{" +
                "month='" + month + '\'' +
                ", weight=" + weight +
                ", money=" + money +
                '}';
    }
}
