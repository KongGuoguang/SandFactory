package com.fenjin.data.entity;

import java.util.List;

/**
 * Author:kongguoguang
 * Date:2019/8/5
 * Time:16:47
 * Summary:获取统计列表
 */
public class GetStaticListResult {
    private int flag;

    private List<Static> result;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public List<Static> getResult() {
        return result;
    }

    public void setResult(List<Static> result) {
        this.result = result;
    }

    public class Static {
        private String sandName;

        private String sh;

        private float weight;

        private float car;

        private float money;

        private float balance;

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

        public float getBalance() {
            return balance;
        }

        public void setBalance(float balance) {
            this.balance = balance;
        }
    }
}
