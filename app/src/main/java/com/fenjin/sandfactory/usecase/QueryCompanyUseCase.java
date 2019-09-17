package com.fenjin.sandfactory.usecase;

import android.content.Context;

import com.fenjin.data.entity.QueryCompanyResult;

import io.reactivex.Observable;

public class QueryCompanyUseCase extends BaseUseCase<QueryCompanyResult> {

    public QueryCompanyUseCase(Context context) {
        super(context);
    }

    private String keyword;

    public QueryCompanyUseCase query(String keyword) {
        this.keyword = keyword;
        return this;
    }

    @Override
    public Observable<QueryCompanyResult> buildObservable() {
        return dataRepository.queryCompany(keyword);
    }
}
