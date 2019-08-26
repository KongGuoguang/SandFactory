package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import com.fenjin.data.entity.StatisticQueryDetailListResult;
import com.fenjin.sandfactory.adapter.StatisticQueryDetailAdapter;
import com.fenjin.sandfactory.usecase.StatisticQueryDetailListUseCase;
import com.fenjin.sandfactory.util.ErrorCodeUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class StatisticQueryDetailViewModel extends BaseViewModel {
    public StatisticQueryDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<Integer> queryType = new ObservableField<>();// 1- 以沙场查询，2-以公司查询

    public ObservableField<String> site = new ObservableField<>();

    public ObservableField<String> company = new ObservableField<>();

    public ObservableField<String> startTime = new ObservableField<>();

    public ObservableField<String> endTime = new ObservableField<>();

    public ObservableField<Boolean> showQueryResult = new ObservableField<>();

    public MutableLiveData<String> loadingMsg = new MutableLiveData<>();

    public MutableLiveData<Integer> errorCode = new MutableLiveData<>();

    public MutableLiveData<String> errorMsg = new MutableLiveData<>();

    public MutableLiveData<Integer> clickedViewId = new MutableLiveData<>();


    public StatisticQueryDetailAdapter adapter = new StatisticQueryDetailAdapter();

    private StatisticQueryDetailListUseCase useCase = new StatisticQueryDetailListUseCase(getApplication());

    public void startQuery() {
        Map<String, Object> param = new HashMap<>();
        param.put("sandName", site.get());
        param.put("sh", company.get());
        param.put("startTime", startTime.get());
        param.put("endTime", endTime.get());

        useCase.get(param).execute(new Observer<StatisticQueryDetailListResult>() {
            @Override
            public void onSubscribe(Disposable d) {
                loadingMsg.postValue("正在加载");
            }

            @Override
            public void onNext(StatisticQueryDetailListResult result) {
                loadingMsg.postValue("");
                if (result.getFlag() == 1) {
                    showQueryResult.set(true);
                    adapter.setItems(result.getResult());
                    adapter.notifyDataSetChanged();
                } else {
                    errorMsg.postValue(result.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                loadingMsg.postValue("");
                errorCode.postValue(ErrorCodeUtil.getErrorCode(e));
            }

            @Override
            public void onComplete() {

            }
        });

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        clickedViewId.postValue(view.getId());
    }
}
