package com.fenjin.sandfactory.usecase;

import android.content.Context;

import com.fenjin.data.entity.ChengZhongRecordListResult;

import io.reactivex.Observable;

/**
 * Author:kongguoguang
 * Date:2018-08-02
 * Time:20:17
 * Summary:
 */
public class GetChengZhongrecordListUseCase extends BaseUseCase<ChengZhongRecordListResult> {

    private int pageNum;

    private int pageSize;

    public GetChengZhongrecordListUseCase(Context context) {
        super(context);
    }

    public GetChengZhongrecordListUseCase get(int pageNum, int pageSize){
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        return this;
    }

    @Override
    public Observable<ChengZhongRecordListResult> buildObservable() {
        return dataRepository.getList(pageNum, pageSize);
    }
}
