package com.fenjin.sandfactory.usecase;

import android.content.Context;

import com.fenjin.data.entity.BalanceQueryResult;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Author:kongguoguang
 * Date:2019/8/28
 * Time:19:08
 * Summary:
 */
public class BalanceQueryUseCase extends BaseUseCase<BalanceQueryResult> {
    public BalanceQueryUseCase(Context context) {
        super(context);
    }

    private Map<String, Object> param;

    public BalanceQueryUseCase query(Map<String, Object> param) {
        this.param = param;
        return this;
    }

    @Override
    public Observable<BalanceQueryResult> buildObservable() {
        return dataRepository.getBalanceQueryResult(param);
    }
}
