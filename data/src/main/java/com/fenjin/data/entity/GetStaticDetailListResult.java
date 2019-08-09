package com.fenjin.data.entity;

import java.util.List;

/**
 * Author:kongguoguang
 * Date:2019/8/5
 * Time:17:16
 * Summary:获取统计详情列表结果
 */
public class GetStaticDetailListResult {
    private int flag;

    private List<StaticDetail> result;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public List<StaticDetail> getResult() {
        return result;
    }

    public void setResult(List<StaticDetail> result) {
        this.result = result;
    }

    public class StaticDetail {

        private String hm;

        private float weight;

        private float car;

        private float money;


        public String getHm() {
            return hm;
        }

        public void setHm(String hm) {
            this.hm = hm;
        }

        public float getWeight() {
            return weight;
        }

        public void setWeight(float weight) {
            this.weight = weight;
        }

        public float getCar() {
            return car;
        }

        public void setCar(float car) {
            this.car = car;
        }

        public float getMoney() {
            return money;
        }

        public void setMoney(float money) {
            this.money = money;
        }

    }
}
