package com.fenjin.sandfactory.usecase;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.fenjin.data.bean.ChengZhongStatisticsItem;
import com.fenjin.data.entity.GetChengZhongStatisticsResult;
import com.fenjin.sandfactory.R;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class ChengZhongStaticUseCase extends BaseUseCase<GetChengZhongStatisticsResult> {
    public ChengZhongStaticUseCase(Context context) {
        super(context);
    }

    private Map<String, Object> param;

    public ChengZhongStaticUseCase get(Map<String, Object> param) {
        this.param = param;
        return this;
    }

    @Override
    public Observable<GetChengZhongStatisticsResult> buildObservable() {
        return dataRepository.getChengZhongStatic(param)
                .doOnNext(new Consumer<GetChengZhongStatisticsResult>() {
                    @Override
                    public void accept(GetChengZhongStatisticsResult getChengZhongStatisticsResult) throws Exception {
                        ChengZhongStatisticsItem carItem = getChengZhongStatisticsResult.getResult().getCar();
                        carItem.setTitle("车数:" + carItem.getTotal() + "(车)");
                        carItem.setBackgroundColor(ContextCompat.getColor(context, R.color.light_yellow));
                        carItem.setTitleColor(ContextCompat.getColor(context, R.color.yellow));

                        ChengZhongStatisticsItem weightItem = getChengZhongStatisticsResult.getResult().getWeight();
                        weightItem.setTitle("重量：" + weightItem.getTotal() + "(吨)");
                        weightItem.setBackgroundColor(ContextCompat.getColor(context, R.color.light_orange));
                        weightItem.setTitleColor(ContextCompat.getColor(context, R.color.orange));

                        ChengZhongStatisticsItem moneyItem = getChengZhongStatisticsResult.getResult().getMoney();
                        moneyItem.setTitle("金额：" + moneyItem.getTotal() + "万");
                        moneyItem.setBackgroundColor(ContextCompat.getColor(context, R.color.light_blue));
                        moneyItem.setTitleColor(ContextCompat.getColor(context, R.color.blue));
                    }
                });
    }
}
