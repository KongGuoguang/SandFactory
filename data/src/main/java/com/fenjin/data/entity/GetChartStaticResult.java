package com.fenjin.data.entity;

import java.util.List;

/**
 * Author:kongguoguang
 * Date:2019/8/5
 * Time:15:48
 * Summary:图标统计
 */
public class GetChartStaticResult {
    private int flag;

    private List<ChartItem> result;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public List<ChartItem> getResult() {
        return result;
    }

    public void setResult(List<ChartItem> result) {
        this.result = result;
    }

    public class ChartItem {
        private String key;

        private float weight;

        private float money;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
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
    }
}
