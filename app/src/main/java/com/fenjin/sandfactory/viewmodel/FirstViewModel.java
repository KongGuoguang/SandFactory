package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.fenjin.data.entity.ChengZhongRecordListResult;
import com.fenjin.data.entity.GetChartStaticResult;
import com.fenjin.data.entity.TodayCountResult;
import com.fenjin.sandfactory.adapter.ChengZhongListAdapter;
import com.fenjin.sandfactory.usecase.GetChartStaticUseCase;
import com.fenjin.sandfactory.usecase.GetChengZhongrecordListUseCase;
import com.fenjin.sandfactory.usecase.GetTodayCountUseCase;
import com.fenjin.sandfactory.util.ErrorCodeUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class FirstViewModel extends BaseViewModel {

    private GetChengZhongrecordListUseCase useCase = new GetChengZhongrecordListUseCase(getApplication());

    private GetTodayCountUseCase todayCountUseCase = new GetTodayCountUseCase(getApplication());

    private GetChartStaticUseCase chartStaticUseCase = new GetChartStaticUseCase(getApplication());

    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public MutableLiveData<Integer> errorCode = new MutableLiveData<>();

    public MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public ObservableField<String> totalTruckNumber = new ObservableField<>();

    public ObservableField<String> totalWeight = new ObservableField<>();

    public ChengZhongListAdapter adapter = new ChengZhongListAdapter();

    public List<GetChartStaticResult.ChartItem> chartItemList = dataRepository.getChartItemList();

    public FirstViewModel(@NonNull Application application) {
        super(application);
        adapter.setHideHeadDivider(true);
    }

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
                        } else {
                            errorMessage.postValue(chengZhongRecordListResult.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loading.postValue(false);
                        int code = ErrorCodeUtil.getErrorCode(e);
                        if (code == ErrorCodeUtil.TOKEN_TIME_OUT) {
                            dataRepository.saveToken("");
                        }
                        errorCode.postValue(code);
                    }

                    @Override
                    public void onComplete() {

                    }
                }
        );

        todayCountUseCase.execute(new Consumer<TodayCountResult>() {
            @Override
            public void accept(TodayCountResult todayCountResult) throws Exception {
                if (todayCountResult.getFlag() == 1) {
                    totalTruckNumber.set(String.valueOf(todayCountResult.getResult().getTodayNo()));
                    totalWeight.set(subZeroAndDot(String.valueOf(todayCountResult.getResult().getTodayWeight())));
                }
            }
        });
    }

    private String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s + "kg";
    }
}
