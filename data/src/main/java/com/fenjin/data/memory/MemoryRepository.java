package com.fenjin.data.memory;

import com.fenjin.data.bean.ChartStatisticsItem;
import com.fenjin.data.bean.PersonalInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:kongguoguang
 * Date:2019/8/5
 * Time:17:49
 * Summary:
 */
public class MemoryRepository {

    private PersonalInfo personalInfo = new PersonalInfo();

    private List<ChartStatisticsItem> chartStatisticsItemList = new ArrayList<>();


    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public List<ChartStatisticsItem> getChartStatisticsItemList() {
        return chartStatisticsItemList;
    }

    public void setChartStatisticsItemList(List<ChartStatisticsItem> chartStatisticsItemList) {
        this.chartStatisticsItemList.clear();
        this.chartStatisticsItemList.addAll(chartStatisticsItemList);
    }
}
