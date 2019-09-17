package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fenjin.data.entity.QueryCompanyResult;
import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.usecase.QueryCompanyUseCase;
import com.fenjin.sandfactory.util.ErrorCodeUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SelectCompannyViewModel extends BaseViewModel {
    public SelectCompannyViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<String> searchKeywords = new ObservableField<>("");

    public MutableLiveData<String> loadingMsg = new MutableLiveData<>();

    public MutableLiveData<Integer> errorCode = new MutableLiveData<>();

    public MutableLiveData<String> errorMsg = new MutableLiveData<>();

    private QueryCompanyUseCase queryCompanyUseCase = new QueryCompanyUseCase(getApplication());

    public List<String> items = new ArrayList<>(Arrays.asList(dataRepository.getCompanyNames()));

    public Adapter adapter = new Adapter(items);

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.tv_search) {
            queryCompany();
        }
    }

    public void queryCompany() {
        items.clear();
        adapter.notifyDataSetChanged();
        queryCompanyUseCase.query(searchKeywords.get())
                .execute(new Observer<QueryCompanyResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        loadingMsg.postValue("正在搜索");
                    }

                    @Override
                    public void onNext(QueryCompanyResult queryCompanyResult) {
                        loadingMsg.postValue("");
                        if (queryCompanyResult.getFlag() == 1) {
                            items.clear();
                            items.addAll(queryCompanyResult.getResult());
                            adapter.notifyDataSetChanged();

                            if (items.size() == 0) {
                                showToast("暂无数据");
                            }
                        } else {
                            errorMsg.postValue(queryCompanyResult.getMessage());
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


    private class Adapter extends BaseAdapter {

        List<String> items;

        public Adapter(List<String> items) {
            this.items = items;
        }

        @Override
        public int getCount() {
            return items == null ? 0 : items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Holder holder;
            if (view == null) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_company_name, viewGroup, false);
                holder = new Holder(view);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }

            String name = items.get(i);
            holder.name.setText(name);

            return view;
        }


        class Holder {
            TextView name;

            Holder(View view) {
                name = view.findViewById(R.id.name);
            }
        }
    }

}
