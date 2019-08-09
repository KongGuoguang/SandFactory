package com.fenjin.data.memory;

import com.fenjin.data.entity.GetChartStaticResult;
import com.fenjin.data.entity.GetPersonInfoResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:kongguoguang
 * Date:2019/8/5
 * Time:17:49
 * Summary:
 */
public class MemoryRepository {

    private GetPersonInfoResult.PersonalInfo personalInfo = new GetPersonInfoResult().new PersonalInfo();

    private List<GetChartStaticResult.ChartItem> chartItemList = new ArrayList<>();


    public GetPersonInfoResult.PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(GetPersonInfoResult.PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public List<GetChartStaticResult.ChartItem> getChartItemList() {
        return chartItemList;
    }

    public void setChartItemList(List<GetChartStaticResult.ChartItem> chartItemList) {
        this.chartItemList = chartItemList;
    }
}
