package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.fenjin.data.bean.BalanceQueryItem;
import com.fenjin.data.entity.BalanceQueryResult;
import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.adapter.BalanceQueryAdapter;
import com.fenjin.sandfactory.usecase.BalanceQueryUseCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Author:kongguoguang
 * Date:2019/8/28
 * Time:18:46
 * Summary:
 */
public class BalanceQueryViewModel extends BaseViewModel {
    public BalanceQueryViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<String> keywords = new ObservableField<>();

    public MutableLiveData<String> loadingMsg = new MutableLiveData<>();

    public MutableLiveData<Integer> errorCode = new MutableLiveData<>();

    public MutableLiveData<String> errorMsg = new MutableLiveData<>();

    public MutableLiveData<Integer> clickedViewId = new MutableLiveData<>();

    private List<BalanceQueryItem> items = new ArrayList<>();

    public BalanceQueryAdapter adapter = new BalanceQueryAdapter(items);

    private BalanceQueryUseCase useCase = new BalanceQueryUseCase(getApplication());

    private int currentPage;
    private int pageSize = 10;
    private int totalCount;

    @Override
    public void onClick(View view) {
        super.onClick(view);
        clickedViewId.postValue(view.getId());
    }

    public void startQuery(boolean loadMore) {
        if (TextUtils.isEmpty(keywords.get())) {
            showToast(R.string.balance_query_hint);
            return;
        }

        if (!loadMore) {
            totalCount = 0;
            currentPage = 1;
            items.clear();
            adapter.notifyDataSetChanged();
        }

        if (totalCount > 0 && items.size() >= totalCount) return;

        Map<String, Object> param = new HashMap<>();
        param.put("pageNum", currentPage);
        param.put("pageSize", pageSize);
        param.put("keyword", keywords.get());

        useCase.query(param).execute(new Observer<BalanceQueryResult>() {
            @Override
            public void onSubscribe(Disposable d) {
                if (items.size() == 0) {
                    loadingMsg.postValue("正在查询");
                }
            }

            @Override
            public void onNext(BalanceQueryResult result) {
                loadingMsg.postValue("");
                if (result.getFlag() == 1) {
                    items.addAll(result.getResult().getList());
                    adapter.notifyDataSetChanged();
                    totalCount = result.getResult().getTotal();
                    if (totalCount == 0) {
                        showToast("暂无数据");
                    }
                    if (items.size() < totalCount) {//
                        currentPage++;
                    }
                } else {
                    errorMsg.postValue(result.getMessage());
                }

            }

            @Override
            public void onError(Throwable e) {
                loadingMsg.postValue("");

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
