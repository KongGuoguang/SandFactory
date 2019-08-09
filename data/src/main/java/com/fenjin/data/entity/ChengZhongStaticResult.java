package com.fenjin.data.entity;

/**
 * Author:kongguoguang
 * Date:2019/8/5
 * Time:14:45
 * Summary:称重统计结果
 */
public class ChengZhongStaticResult {
    private int flag;

    private Result result;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {

        private Count car;

        private Count weight;

        private Count money;

        public Count getCar() {
            return car;
        }

        public void setCar(Count car) {
            this.car = car;
        }

        public Count getWeight() {
            return weight;
        }

        public void setWeight(Count weight) {
            this.weight = weight;
        }

        public Count getMoney() {
            return money;
        }

        public void setMoney(Count money) {
            this.money = money;
        }

        public class Count {

            private float total;

            private float hesha;

            private float sashi;

            public float getTotal() {
                return total;
            }

            public void setTotal(float total) {
                this.total = total;
            }

            public float getHesha() {
                return hesha;
            }

            public void setHesha(float hesha) {
                this.hesha = hesha;
            }

            public float getSashi() {
                return sashi;
            }

            public void setSashi(float sashi) {
                this.sashi = sashi;
            }
        }

    }
}
