package com.fenjin.data.entity;

import com.fenjin.data.bean.StatisticsQueryItem;

import java.util.List;

/**
 * Author:kongguoguang
 * Date:2019/8/5
 * Time:16:47
 * Summary:获取统计查询列表
 */
public class StatisticQueryListResult extends BaseResult<StatisticQueryListResult.Result> {


    public class Result {
        private int total;
        private int pageNum;
        private int pageSize;
        private List<StatisticsQueryItem> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public List<StatisticsQueryItem> getList() {
            return list;
        }

        public void setList(List<StatisticsQueryItem> list) {
            this.list = list;
        }
    }

}
