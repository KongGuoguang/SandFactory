package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.view.View;

import com.fenjin.data.bean.ChengZhongStatisticsItem;
import com.fenjin.data.entity.ChengZhongStatisticsParam;
import com.fenjin.data.entity.GetChartStaticResult;
import com.fenjin.data.entity.GetChengZhongStatisticsResult;
import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.adapter.ChengZhongStatisticsAdapter;
import com.fenjin.sandfactory.fragment.StatisticsFragment;
import com.fenjin.sandfactory.usecase.ChartStaticUseCase;
import com.fenjin.sandfactory.usecase.ChengZhongStaticUseCase;
import com.fenjin.sandfactory.util.DateUtil;
import com.fenjin.sandfactory.util.ErrorCodeUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class StatisticsViewModel extends BaseViewModel {

    private ChengZhongStaticUseCase chengZhongStaticUseCase = new ChengZhongStaticUseCase(getApplication());

    private ChartStaticUseCase chartStaticUseCase = new ChartStaticUseCase(getApplication());

    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public MutableLiveData<Integer> errorCode = new MutableLiveData<>();

    public MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public MutableLiveData<Boolean> showBarChart = new MutableLiveData<>();

    public MutableLiveData<Integer> statisticContent = new MutableLiveData<>();

    public ChengZhongStatisticsAdapter adapter = new ChengZhongStatisticsAdapter();

    private boolean loadAllStatisticsData = true;


    public StatisticsViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadTodayChengZhongStatistics() {
        ChengZhongStatisticsParam chengZhongStatisticsParam = new ChengZhongStatisticsParam();
        chengZhongStatisticsParam.setStartTime(DateUtil.getTodayDate());
        chengZhongStatisticsParam.setEndTime(DateUtil.getTodayDate());

        loadChengZhongStatistics(chengZhongStatisticsParam);
    }

    public void loadMonthChengZhongStatistics() {
        ChengZhongStatisticsParam chengZhongStatisticsParam = new ChengZhongStatisticsParam();
        chengZhongStatisticsParam.setStartTime(DateUtil.getMonthFirstDayDate());
        chengZhongStatisticsParam.setEndTime(DateUtil.getMonthLastDayDate());

        loadChengZhongStatistics(chengZhongStatisticsParam);
    }

    public void loadYearChengZhongStatistics() {
        ChengZhongStatisticsParam chengZhongStatisticsParam = new ChengZhongStatisticsParam();
        chengZhongStatisticsParam.setStartTime(DateUtil.getYearFirstDayDate());
        chengZhongStatisticsParam.setEndTime(DateUtil.getYearLastDayDate());

        loadChengZhongStatistics(chengZhongStatisticsParam);
    }

    /**
     * 加载称重统计数据
     */
    private void loadChengZhongStatistics(ChengZhongStatisticsParam param) {

        chengZhongStaticUseCase.get(param)
                .execute(new Observer<GetChengZhongStatisticsResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        loading.postValue(true);
                    }

                    @Override
                    public void onNext(GetChengZhongStatisticsResult getChengZhongStatisticsResult) {
                        loading.postValue(false);
                        if (getChengZhongStatisticsResult.getFlag() == 1) {
                            List<ChengZhongStatisticsItem> chengZhongStatisticsItems = new ArrayList<>();
                            chengZhongStatisticsItems.add(getChengZhongStatisticsResult.getResult().getCar());
                            chengZhongStatisticsItems.add(getChengZhongStatisticsResult.getResult().getWeight());
                            chengZhongStatisticsItems.add(getChengZhongStatisticsResult.getResult().getMoney());
                            adapter.setChengZhongStatisticsItemList(chengZhongStatisticsItems);
                            adapter.notifyDataSetChanged();
                        } else {
                            errorMessage.postValue(getChengZhongStatisticsResult.getMessage());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        loading.postValue(false);
                        errorCode.postValue(ErrorCodeUtil.getErrorCode(e));
                    }

                    @Override
                    public void onComplete() {
                        if (loadAllStatisticsData) {
                            loadChartStatistics();
                        }
                    }
                });

    }

    /**
     * 加载图表统计数据
     */
    private void loadChartStatistics() {
        chartStaticUseCase.execute(new Consumer<GetChartStaticResult>() {
            @Override
            public void accept(GetChartStaticResult getChartStaticResult) throws Exception {
                Collections.reverse(getChartStaticResult.getResult());
                dataRepository.setChartItemList(getChartStaticResult.getResult());
                showBarChart.postValue(true);
                loadAllStatisticsData = false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_site_statistics:
                statisticContent.postValue(StatisticsFragment.STATISTIC_TYPE_SITE);
                break;
            case R.id.tv_company_statistics:
                statisticContent.postValue(StatisticsFragment.STATISTIC_TYPE_COMPANY);
                break;
            case R.id.tv_balance_query:
                statisticContent.postValue(StatisticsFragment.STATISTIC_TYPE_BALANCE);
                break;
        }
    }

    private String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s + "kg";
    }
}
