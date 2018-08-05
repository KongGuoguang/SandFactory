package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.fenjin.data.entity.ChengZhongRecord;
import com.fenjin.data.entity.ChengZhongRecordListResult;
import com.fenjin.sandfactory.usecase.GetChengZhongrecordListUseCase;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class FirstViewModel extends BaseViewModel {
    public FirstViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<ChengZhongRecord>> chengZhongRecordListLive = new MutableLiveData<>();

    private GetChengZhongrecordListUseCase useCase = new GetChengZhongrecordListUseCase(getApplication());

    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public void loadChengZhongRecordList(){
        loading.postValue(true);
        useCase.get(1, 10, null).execute(
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
