package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.fenjin.data.bean.StatisticsQueryCount;
import com.fenjin.data.bean.StatisticsQueryItem;
import com.fenjin.data.entity.LoadCompanyNamesResult;
import com.fenjin.data.entity.LoadSiteNamesResult;
import com.fenjin.data.entity.StatisticQueryCountResult;
import com.fenjin.data.entity.StatisticQueryListResult;
import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.adapter.StatisticQueryAdapter;
import com.fenjin.sandfactory.usecase.LoadCompanyNamesUseCase;
import com.fenjin.sandfactory.usecase.LoadSiteNamesUseCase;
import com.fenjin.sandfactory.usecase.StatisticQueryCountUseCase;
import com.fenjin.sandfactory.usecase.StatisticQueryListUseCase;
import com.fenjin.sandfactory.util.ErrorCodeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Author:kongguoguang
 * Date:2019/8/24
 * Time:16:08
 * Summary:
 */
public class StatisticQueryViewModel extends BaseViewModel {
    public StatisticQueryViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<Integer> queryType = new ObservableField<>();// 1- 以沙场查询，2-以公司查询

    public ObservableField<String> siteOrCompany = new ObservableField<>();

    public ObservableField<String> startTime = new ObservableField<>();

    public ObservableField<String> endTime = new ObservableField<>();

    public ObservableField<Integer> totalCar = new ObservableField<>();

    public ObservableField<Float> totalWeight = new ObservableField<>();

    public ObservableField<Float> totalMoney = new ObservableField<>();

    public ObservableField<Boolean> showQueryResult = new ObservableField<>();

    public MutableLiveData<String> loadingMsg = new MutableLiveData<>();

    public MutableLiveData<Integer> errorCode = new MutableLiveData<>();

    public MutableLiveData<String> errorMsg = new MutableLiveData<>();

    public MutableLiveData<Integer> clickedViewId = new MutableLiveData<>();

    private List<StatisticsQueryItem> items = new ArrayList<>();

    public StatisticQueryAdapter adapter = new StatisticQueryAdapter(items);

    private LoadSiteNamesUseCase loadSiteNamesUseCase = new LoadSiteNamesUseCase(getApplication());
    private LoadCompanyNamesUseCase loadCompanyNamesUseCase = new LoadCompanyNamesUseCase(getApplication());

    private StatisticQueryCountUseCase statisticQueryCountUseCase = new StatisticQueryCountUseCase(getApplication());
    private StatisticQueryListUseCase statisticQueryListUseCase = new StatisticQueryListUseCase(getApplication());

    public void loadSiteOrCompanyNames() {
        if (queryType.get() == 1) {
            loadSiteNamesUseCase.execute(new Observer<LoadSiteNamesResult>() {
                @Override
                public void onSubscribe(Disposable d) {
                    loadingMsg.postValue("更新站点");
                }

                @Override
                public void onNext(LoadSiteNamesResult loadSiteNamesResult) {
                    loadingMsg.postValue("");
                    if (loadSiteNamesResult.getFlag() != 1) {
                        errorMsg.postValue(loadSiteNamesResult.getMessage());
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
        } else {
            loadCompanyNamesUseCase.execute(new Observer<LoadCompanyNamesResult>() {
                @Override
                public void onSubscribe(Disposable d) {
                    loadingMsg.postValue("更新单位");
                }

                @Override
                public void onNext(LoadCompanyNamesResult loadCompanyNamesResult) {
                    loadingMsg.postValue("");
                    if (loadCompanyNamesResult.getFlag() != 1) {
                        errorMsg.postValue(loadCompanyNamesResult.getMessage());
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
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        clickedViewId.postValue(view.getId());
    }

    public void startQuery() {
        if (TextUtils.isEmpty(siteOrCompany.get())) {
            if (queryType.get() == 1) {
                showToast(R.string.select_site);
            } else {
                showToast(R.string.select_company);
            }
            return;
        }

        if (TextUtils.isEmpty(startTime.get())) {
            showToast(R.string.select_start_time);
            return;
        }

        if (TextUtils.isEmpty(endTime.get())) {
            showToast(R.string.select_end_time);
            return;
        }

        showQueryResult.set(false);

        queryStatisticQueryCount();

    }

    private void queryStatisticQueryCount() {

        Map<String, Object> map = new HashMap<>();
        map.put("type", queryType.get());
        if (queryType.get() == 1) {
            map.put("sandName", siteOrCompany.get());
        } else {
            map.put("sh", siteOrCompany.get());
        }
        map.put("startTime", startTime.get());
        map.put("endTime", endTime.get());

        statisticQueryCountUseCase.get(map).execute(new Observer<StatisticQueryCountResult>() {
            @Override
            public void onSubscribe(Disposable d) {
                loadingMsg.postValue("正在查询");
            }

            @Override
            public void onNext(StatisticQueryCountResult statisticQueryCountResult) {
                if (statisticQueryCountResult.getFlag() == 1) {
                    StatisticsQueryCount count = statisticQueryCountResult.getResult();
                    totalCar.set(count.getCar());
                    totalWeight.set(count.getWeight());
                    totalMoney.set(count.getMoney());
                    queryStatisticQueryList();
                } else {
                    loadingMsg.postValue("");
                    errorMsg.postValue(statisticQueryCountResult.getMessage());
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

    private void queryStatisticQueryList() {

        adapter.setType(queryType.get());

        Map<String, Object> map = new HashMap<>();
        map.put("type", queryType.get());
        if (queryType.get() == 1) {
            map.put("sandName", siteOrCompany.get());
        } else {
            map.put("sh", siteOrCompany.get());
        }
        map.put("startTime", startTime.get());
        map.put("endTime", endTime.get());
        map.put("pageNum", 1);
        map.put("pageSize", 10);

        statisticQueryListUseCase.get(map).execute(new Observer<StatisticQueryListResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(StatisticQueryListResult statisticQueryListResult) {
                loadingMsg.setValue("");
                if (statisticQueryListResult.getFlag() == 1) {
                    items.addAll(statisticQueryListResult.getResult().getList());
                    adapter.notifyDataSetChanged();
                    showQueryResult.set(true);
                } else {
                    errorMsg.postValue(statisticQueryListResult.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                loadingMsg.setValue("");
                errorCode.postValue(ErrorCodeUtil.getErrorCode(e));
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
