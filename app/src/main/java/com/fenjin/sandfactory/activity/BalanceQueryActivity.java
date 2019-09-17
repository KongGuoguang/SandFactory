package com.fenjin.sandfactory.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.adapter.LinearItemDecoration;
import com.fenjin.sandfactory.databinding.ActivityBalanceQueryBinding;
import com.fenjin.sandfactory.viewmodel.BalanceQueryViewModel;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

public class BalanceQueryActivity extends BaseActivity {

    private BalanceQueryViewModel viewModel;

    private QMUITipDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBalanceQueryBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_balance_query);
        viewModel = ViewModelProviders.of(this).get(BalanceQueryViewModel.class);
        binding.setViewModel(viewModel);

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new LinearItemDecoration());
        recyclerView.setAdapter(viewModel.adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_IDLE) {//滑动到了底部，加载更多
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (linearLayoutManager == null) return;
                    int itemTotalCount = viewModel.adapter.getItemCount();
                    int mLastChildPosition = linearLayoutManager.findLastVisibleItemPosition();
                    if (mLastChildPosition != 0 && mLastChildPosition == itemTotalCount - 1) {
                        viewModel.startQuery(true);
                    }
                }
            }
        });

        registerObserver();

        viewModel.startQuery(false);

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
                    loadingDialog = new QMUITipDialog.Builder(BalanceQueryActivity.this)
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
                    case R.id.bt_query:
                        viewModel.startQuery(false);
                        break;
                }
            }
        });
    }

}
