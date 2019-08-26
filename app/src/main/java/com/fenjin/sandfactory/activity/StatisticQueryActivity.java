package com.fenjin.sandfactory.activity;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.DatePicker;

import com.fenjin.data.bean.StatisticsQueryItem;
import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.adapter.StatisticQueryAdapter;
import com.fenjin.sandfactory.adapter.StatisticQueryItemDecoration;
import com.fenjin.sandfactory.databinding.ActivityStatisticQueryBinding;
import com.fenjin.sandfactory.viewmodel.StatisticQueryViewModel;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.Calendar;

public class StatisticQueryActivity extends BaseActivity {

    public static final String QUERY_TYPE = "query_type";
    public static final String SITE_NAME = "site_name";
    public static final String COMPANY_NAME = "company_name";
    public static final String START_TIME = "start_time";
    public static final String END_TIME = "end_time";

    private StatisticQueryViewModel viewModel;

    private QMUITipDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStatisticQueryBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_statistic_query);
        viewModel = ViewModelProviders.of(this).get(StatisticQueryViewModel.class);
        binding.setViewModel(viewModel);

        final int queryType = getIntent().getIntExtra(QUERY_TYPE, 1);
        viewModel.queryType.set(queryType);

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new StatisticQueryItemDecoration());
        recyclerView.setAdapter(viewModel.adapter);
        viewModel.adapter.setCheckDetailListener(new StatisticQueryAdapter.CheckDetailListener() {
            @Override
            public void checkDetail(StatisticsQueryItem item) {
                Intent intent = new Intent(StatisticQueryActivity.this, StatisticQueryDetailActivity.class);
                intent.putExtra(QUERY_TYPE, queryType);
                if (queryType == 1) {
                    intent.putExtra(SITE_NAME, viewModel.siteOrCompany.get());
                    intent.putExtra(COMPANY_NAME, item.getSh());
                } else {
                    intent.putExtra(SITE_NAME, item.getSandName());
                    intent.putExtra(COMPANY_NAME, viewModel.siteOrCompany.get());
                }

                intent.putExtra(START_TIME, viewModel.startTime.get());
                intent.putExtra(END_TIME, viewModel.endTime.get());
                startActivity(intent);
            }
        });

        registerObserver();

        viewModel.loadSiteOrCompanyNames();
    }

    private void registerObserver() {

        viewModel.loadingMsg.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (TextUtils.isEmpty(s)) {
                    if (loadingDialog != null && loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                    }
                } else {
                    loadingDialog = new QMUITipDialog.Builder(StatisticQueryActivity.this)
                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                            .setTipWord(s)
                            .create();
                    loadingDialog.setCancelable(true);
                    loadingDialog.show();
                }
            }
        });

        viewModel.errorCode.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer == null) return;
                dealErrorCode(integer);
            }
        });

        viewModel.errorMsg.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                showErrorDialog(s);
            }
        });

        viewModel.clickedViewId.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer == null) return;

                switch (integer) {
                    case R.id.tv_cancel:
                        finish();
                        break;
                    case R.id.tv_select_site_or_company:
                        selectSiteOrCompany();
                        break;
                    case R.id.tv_select_start_time:
                        selectTime(viewModel.startTime);
                        break;
                    case R.id.tv_select_end_time:
                        selectTime(viewModel.endTime);
                        break;
                    case R.id.bt_query:
                        viewModel.startQuery();
                        break;
                }
            }
        });
    }

    private void selectSiteOrCompany() {
        final String[] items;
        if (viewModel.queryType.get() == 1) {
            items = viewModel.dataRepository.getSiteNames();
        } else {
            items = viewModel.dataRepository.getCompanyNames();
        }

        int checkedIndex = 0;

        if (!TextUtils.isEmpty(viewModel.siteOrCompany.get())) {
            for (int i = 0; i < items.length; i++) {
                if (viewModel.siteOrCompany.get().equals(items[i])) {
                    checkedIndex = i;
                    break;
                }
            }
        }

        new QMUIDialog.CheckableDialogBuilder(this)
                .setCheckedIndex(checkedIndex)
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.siteOrCompany.set(items[which]);
                        dialog.dismiss();
                    }
                })
                .create(R.style.QMUI_Dialog).show();
    }


    private void selectTime(final ObservableField<String> observableField) {
        int mYear;
        int mMonth;
        int mDay;

        final String date = observableField.get();
        if (TextUtils.isEmpty(date)) {
            Calendar ca = Calendar.getInstance();
            mYear = ca.get(Calendar.YEAR);
            mMonth = ca.get(Calendar.MONTH);
            mDay = ca.get(Calendar.DAY_OF_MONTH);
        } else {
            String[] array = date.split("-");
            mYear = Integer.parseInt(array[0]);
            mMonth = Integer.parseInt(array[1]) - 1;
            mDay = Integer.parseInt(array[2]);
        }

        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                StringBuilder builder = new StringBuilder();
                builder.append(year).append("-");
                if (++month < 10) {
                    builder.append(0);
                }
                builder.append(month).append("-");
                if (dayOfMonth < 10) {
                    builder.append(0);
                }
                builder.append(dayOfMonth);
                observableField.set(builder.toString());
            }
        }, mYear, mMonth, mDay);

        dialog.show();
    }
}
