package com.fenjin.data.entity;

import com.fenjin.data.bean.BalanceQueryItem;

import java.util.List;

/**
 * Author:kongguoguang
 * Date:2019/8/28
 * Time:19:01
 * Summary:
 */
public class BalanceQueryResult extends BaseResult<BalanceQueryResult.Result> {

    public class Result {
        private int total;
        private List<BalanceQueryItem> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<BalanceQueryItem> getList() {
            return list;
        }

        public void setList(List<BalanceQueryItem> list) {
            this.list = list;
        }
    }
}
