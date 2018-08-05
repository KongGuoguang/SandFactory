package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.fenjin.data.entity.ChengZhongRecord;
import com.fenjin.data.entity.ChengZhongRecordListResult;
import com.fenjin.sandfactory.usecase.GetChengZhongrecordListUseCase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Author:kongguoguang
 * Date:2018-08-02
 * Time:20:12
 * Summary:
 */
public class QueryViewModel extends BaseViewModel {
    public QueryViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<String> searchKey = new ObservableField<>();

    public MutableLiveData<List<ChengZhongRecord>> chengZhongRecordListLive = new MutableLiveData<>();

    private GetChengZhongrecordListUseCase useCase = new GetChengZhongrecordListUseCase(getApplication());

    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public void loadChengZhongRecordList(){

        chengZhongRecordListLive.postValue(new ArrayList<ChengZhongRecord>());

        loading.postValue(true);

        useCase.get(1, 40, searchKey.get()).execute(
                new Observer<ChengZhongRecordListResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ChengZhongRecordListResult chengZhongRecordListResult) {
                        loading.postValue(false);
                        if (chengZhongRecordListResult.getFlag() == 1){
                            chengZhongRecordListLive.postValue(
                                    chengZhongRecordListResult.getResult().getList());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loading.postValue(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                }
        );
    }
}
