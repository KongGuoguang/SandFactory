package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import com.fenjin.sandfactory.usecase.LoadCompanyNamesUseCase;
import com.fenjin.sandfactory.usecase.LoadSandFactoryNamesUseCase;
import com.fenjin.sandfactory.usecase.StatisticQueryUseCase;

/**
 * Author:kongguoguang
 * Date:2019/8/24
 * Time:16:08
 * Summary:
 */
public class StatisticQueryViewModel extends BaseViewModel {
    public StatisticQueryViewModel(@NonNull Application application) {
        super(application);
    }

    public int type = 1;// 1- 以沙场查询，2-以公司查询

    private StatisticQueryUseCase statisticQueryUseCase = new StatisticQueryUseCase(getApplication());

    private LoadSandFactoryNamesUseCase loadSandFactoryNamesUseCase = new LoadSandFactoryNamesUseCase(getApplication());
    private LoadCompanyNamesUseCase loadCompanyNamesUseCase = new LoadCompanyNamesUseCase(getApplication());
}
