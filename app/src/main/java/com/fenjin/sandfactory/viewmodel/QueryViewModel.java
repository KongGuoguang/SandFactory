package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.content.res.ColorStateList;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.fenjin.data.entity.ChengZhongRecord;
import com.fenjin.data.entity.ChengZhongRecordListResult;
import com.fenjin.sandfactory.adapter.ChengZhongListAdapter;
import com.fenjin.sandfactory.usecase.GetChengZhongrecordListUseCase;
import com.fenjin.sandfactory.util.ErrorCodeUtil;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButtonDrawable;

import java.util.ArrayList;
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

    public QMUIRoundButtonDrawable drawable;

    public QueryViewModel(@NonNull Application application) {
        super(application);
        drawable = new QMUIRoundButtonDrawable();
        drawable.setBgData(ColorStateList.valueOf(Color.WHITE));
        drawable.setIsRadiusAdjustBounds(true);
    }

    public ObservableField<String> searchKey = new ObservableField<>();

    private GetChengZhongrecordListUseCase useCase = new GetChengZhongrecordListUseCase(getApplication());

    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public MutableLiveData<Boolean> lastPage = new MutableLiveData<>();

    public MutableLiveData<Integer> errorCode = new MutableLiveData<>();

    public MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public int currentPage;

    private int pageSize = 10;

    private List<ChengZhongRecord> chengZhongRecordList = new ArrayList<>();

    public ChengZhongListAdapter adapter = new ChengZhongListAdapter(chengZhongRecordList);

    public void loadFirstPageChengZhongRecords(){

        chengZhongRecordList.clear();

        currentPage = 0;

        adapter.notifyDataSetChanged();

        loading.postValue(true);

        loadChengZhongRecordList(currentPage + 1);
    }

    public void loadNextPageChengZhongRecords(){

        loadChengZhongRecordList(currentPage + 1);
    }



    private void loadChengZhongRecordList(int pageNumber){

        LogUtils.d("load page " + pageNumber);

        useCase.get(pageNumber, pageSize, searchKey.get()).execute(
                new Observer<ChengZhongRecordListResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ChengZhongRecordListResult chengZhongRecordListResult) {
                        loading.postValue(false);
                        if (chengZhongRecordListResult.getFlag() == 1){

                            List<ChengZhongRecord> chengZhongRecords = chengZhongRecordListResult.getResult().getList();

                            int total = chengZhongRecordListResult.getResult().getTotal();

                            if (chengZhongRecords != null && chengZhongRecords.size() > 0){
                                chengZhongRecordList.addAll(chengZhongRecords);
                                adapter.notifyDataSetChanged();

                                int count = chengZhongRecordList.size();
                                if (count % pageSize == 0){
                                    currentPage = count / pageSize;
                                }else {
                                    currentPage = count / pageSize + 1;
                                }
                            }

                            lastPage.postValue(chengZhongRecordList.size() == total);

                        }else {
                            errorMessage.postValue(chengZhongRecordListResult.getMessage());
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
