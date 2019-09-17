package com.fenjin.sandfactory.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.databinding.ActivitySelectCompanyBinding;
import com.fenjin.sandfactory.viewmodel.SelectCompannyViewModel;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

public class SelectCompanyActivity extends BaseActivity {

    private SelectCompannyViewModel viewModel;
    private QMUITipDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySelectCompanyBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_select_company);
        viewModel = ViewModelProviders.of(this).get(SelectCompannyViewModel.class);
        binding.setViewModel(viewModel);

        ListView listView = binding.listView;
        listView.setAdapter(viewModel.adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("name", viewModel.items.get(i));
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        if (viewModel.items.size() == 0) {
            viewModel.queryCompany();
        }
    }

    private void registerObserver() {
        viewModel.loadingMsg.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s == null) return;
                if (!TextUtils.isEmpty(s)) {
                    if (loadingDialog == null) {
                        loadingDialog = new QMUITipDialog.Builder(SelectCompanyActivity.this)
                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                                .setTipWord(s)
                                .create();
                        loadingDialog.setCancelable(true);
                    }
                    loadingDialog.show();
                } else {
                    if (loadingDialog != null && loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                    }
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
    }


}
