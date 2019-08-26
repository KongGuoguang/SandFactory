package com.fenjin.sandfactory.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.adapter.StatisticQueryItemDecoration;
import com.fenjin.sandfactory.databinding.ActivityStatisticQueryDetailBinding;
import com.fenjin.sandfactory.viewmodel.StatisticQueryDetailViewModel;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

public class StatisticQueryDetailActivity extends BaseActivity {

    private StatisticQueryDetailViewModel viewModel;

    private QMUITipDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStatisticQueryDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_statistic_query_detail);
        viewModel = ViewModelProviders.of(this).get(StatisticQueryDetailViewModel.class);
        binding.setViewModel(viewModel);

        Intent intent = getIntent();
        viewModel.queryType.set(intent.getIntExtra(StatisticQueryActivity.QUERY_TYPE, 1));
        viewModel.site.set(intent.getStringExtra(StatisticQueryActivity.SITE_NAME));
        viewModel.company.set(intent.getStringExtra(StatisticQueryActivity.COMPANY_NAME));
        viewModel.startTime.set(intent.getStringExtra(StatisticQueryActivity.START_TIME));
        viewModel.endTime.set(intent.getStringExtra(StatisticQueryActivity.END_TIME));

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new StatisticQueryItemDecoration());
        recyclerView.setAdapter(viewModel.adapter);

        registerObserver();
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.startQuery();
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
                    loadingDialog = new QMUITipDialog.Builder(StatisticQueryDetailActivity.this)
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
//                    case R.id.tv_select_site_or_company:
//                        selectSiteOrCompany();
//                        break;
//                    case R.id.tv_select_start_time:
//                        selectTime(viewModel.startTime);
//                        break;
//                    case R.id.tv_select_end_time:
//                        selectTime(viewModel.endTime);
//                        break;
//                    case R.id.bt_query:
//                        viewModel.startQuery();
//                        break;
                }
            }
        });
    }
}
