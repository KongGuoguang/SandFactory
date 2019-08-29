package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.fenjin.data.entity.ChengZhongRecord;
import com.fenjin.data.entity.ChengZhongRecordListResult;
import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.adapter.ChengZhongListAdapter;
import com.fenjin.sandfactory.usecase.GetChengZhongrecordListUseCase;
import com.fenjin.sandfactory.util.ErrorCodeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Author:kongguoguang
 * Date:2018-08-02
 * Time:20:12
 * Summary:
 */
public class QueryFragmentViewModel extends BaseViewModel {

    public static final int SEARCH_TYPE_BILL = 0;

    public static final int SEARCH_TYPE_CAR_NO = 1;

    public static final int SEARCH_TYPE_GOODS_NAME = 2;

    public static final int SEARCH_TYPE_SITE_NAME = 3;


    public QueryFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<String> searchKeywords = new ObservableField<>();
    public ObservableField<String> searchKeywordsHint = new ObservableField<>(
            getApplication().getString(R.string.search_bill_hint));

    private GetChengZhongrecordListUseCase useCase = new GetChengZhongrecordListUseCase(getApplication());

    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public MutableLiveData<Boolean> lastPage = new MutableLiveData<>();

    public MutableLiveData<Integer> errorCode = new MutableLiveData<>();

    public MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public int searchType = SEARCH_TYPE_BILL;

    public int currentPage;

    private int pageSize = 10;

    private int totalCount;

    private List<ChengZhongRecord> chengZhongRecordList = new ArrayList<>();

    public ChengZhongListAdapter adapter = new ChengZhongListAdapter(chengZhongRecordList);

    public void loadFirstPageChengZhongRecords(){

        if (TextUtils.isEmpty(searchKeywords.get())) {
            showToast(searchKeywordsHint.get());
            return;
        }

        chengZhongRecordList.clear();

        adapter.notifyDataSetChanged();

        currentPage = 1;

        totalCount = 0;

        loading.postValue(true);

        loadChengZhongRecordList();
    }

    public void loadNextPageChengZhongRecords(){
        if (chengZhongRecordList.size() == totalCount) return;
        loadChengZhongRecordList();
    }


    private void loadChengZhongRecordList() {

        LogUtils.d("load page " + currentPage);
        Map<String, Object> param = new HashMap<>();
        param.put("pageNum", currentPage);
        param.put("pageSize", pageSize);
        switch (searchType) {
            case SEARCH_TYPE_BILL:
                param.put("xh", searchKeywords.get());
                break;
            case SEARCH_TYPE_CAR_NO:
                param.put("ch", searchKeywords.get());
                break;
            case SEARCH_TYPE_GOODS_NAME:
                param.put("hm", searchKeywords.get());
                break;
            case SEARCH_TYPE_SITE_NAME:
                param.put("sandName", searchKeywords.get());
                break;
        }

        useCase.query(param).execute(
                new Observer<ChengZhongRecordListResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ChengZhongRecordListResult result) {
                        loading.postValue(false);
                        if (result.getFlag() == 1) {
                            chengZhongRecordList.addAll(result.getResult().getList());
                            adapter.notifyDataSetChanged();
                            totalCount = result.getResult().getTotal();
                            if (chengZhongRecordList.size() < totalCount) {
                                currentPage++;
                            }
                            lastPage.postValue(chengZhongRecordList.size() == totalCount && totalCount > 0);

                            if (chengZhongRecordList.size() == 0) {
                                showToast("暂无数据");
                            }

                        } else {
                            errorMessage.postValue(result.getMessage());
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
