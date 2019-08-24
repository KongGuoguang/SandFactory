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
public class StatisticQueryUseCase extends BaseUseCase<StatisticQueryListResult> {
    public StatisticQueryUseCase(Context context) {
        super(context);
    }

    private StatisticQueryListParam param;

    public StatisticQueryUseCase get(StatisticQueryListParam param) {
        this.param = param;
        return this;
    }

    @Override
    public Observable<StatisticQueryListResult> buildObservable() {
        return dataRepository.getStatisticQueryList(param);
    }
}
