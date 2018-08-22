package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.fenjin.data.entity.ChengZhongRecord;
import com.fenjin.data.entity.ChengZhongRecordListResult;
import com.fenjin.sandfactory.adapter.ChengZhongListAdapter;
import com.fenjin.sandfactory.usecase.GetChengZhongrecordListUseCase;
import com.fenjin.sandfactory.util.ErrorCodeUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class FirstViewModel extends BaseViewModel {
    public FirstViewModel(@NonNull Application application) {
        super(application);
    }


    private GetChengZhongrecordListUseCase useCase = new GetChengZhongrecordListUseCase(getApplication());

    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public MutableLiveData<Integer> errorCode = new MutableLiveData<>();

    public ObservableField<String> totalTruckNumber = new ObservableField<>();

    public ObservableField<String> totalWeight = new ObservableField<>();

    public ChengZhongListAdapter adapter = new ChengZhongListAdapter();

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
                            adapter.setChengZhongRecordList(chengZhongRecordListResult.getResult().getList());
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loading.postValue(false);
                        errorCode.postValue(ErrorCodeUtil.getErrorCode(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                }
        );
    }
}
