package com.fenjin.sandfactory.usecase;

import android.content.Context;

import com.fenjin.data.entity.StatisticQueryListParam;
import com.fenjin.data.entity.StatisticQueryListResult;

import io.reactivex.Observable;

/**
 * Author:kongguoguang
 * Date:2019/8/24
 * Time:18:22
 * Summary:
 */
public class StatisticQueryListUseCase extends BaseUseCase<StatisticQueryListResult> {
    public StatisticQueryListUseCase(Context context) {
        super(context);
    }

    private StatisticQueryListParam param;

    public StatisticQueryListUseCase get(StatisticQueryListParam param) {
        this.param = param;
        return this;
    }

    @Override
    public Observable<StatisticQueryListResult> buildObservable() {
        return dataRepository.getStatisticQueryList(param);
    }
}
