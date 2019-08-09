package com.fenjin.data.entity;

/**
 * Author:kongguoguang
 * Date:2019/8/5
 * Time:17:15
 * Summary:获取统计详情总数结果
 */
public class GetStaticDetailCountResult {
    private int flag;

    private Count result;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Count getResult() {
        return result;
    }

    public void setResult(Count result) {
        this.result = result;
    }

    public class Count {
        private float weight;

        private float car;

        private float money;

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
