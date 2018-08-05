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

    private String searchKey;

    public GetChengZhongrecordListUseCase(Context context) {
        super(context);
    }

    public GetChengZhongrecordListUseCase get(int pageNum, int pageSize, String searchKey){
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.searchKey = searchKey;
        return this;
    }

    @Override
    public Observable<ChengZhongRecordListResult> buildObservable() {
        return dataRepository.getChengZhongRecordList(pageNum, pageSize, searchKey);
    }
}
