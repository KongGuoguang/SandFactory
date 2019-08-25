package com.fenjin.sandfactory.usecase;

import android.content.Context;

import com.fenjin.data.entity.StatisticQueryCountParam;
import com.fenjin.data.entity.StatisticQueryCountResult;

import io.reactivex.Observable;

/**
 * Author:kongguoguang
 * Date:2019/8/25
 * Time:14:46
 * Summary:
 */
public class StatisticQueryCountUseCase extends BaseUseCase<StatisticQueryCountResult> {
    public StatisticQueryCountUseCase(Context context) {
        super(context);
    }

    private StatisticQueryCountParam param;

    public StatisticQueryCountUseCase get(StatisticQueryCountParam param) {
        this.param = param;
        return this;
    }

    @Override
    public Observable<StatisticQueryCountResult> buildObservable() {
        return dataRepository.getStatisticQueryCount(param);
    }
}
