package com.fenjin.sandfactory.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.databinding.ActivityLoginBinding;
import com.fenjin.sandfactory.viewmodel.LoginViewModel;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

public class LoginActivity extends BaseActivity {

    private QMUITipDialog loadingDialog;

    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding.setViewModel(viewModel);
        init();

    }

    private void init(){

        viewModel.loginSuccess.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean == null) return;
                if (aBoolean){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            }
        });

        viewModel.loading.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (TextUtils.isEmpty(s)) {
                    if (loadingDialog != null && loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                    }
                } else {
                    loadingDialog = new QMUITipDialog.Builder(LoginActivity.this)
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

        viewModel.errorMessage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                showErrorDialog(s);
            }
        });

        viewModel.clickLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer == null) return;
                if (integer == viewModel.CLICK_INTENT_FINISH) {
                    finish();
                }

                if (integer == viewModel.CLICK_INTENT_CONFIG_IP) {
                    startActivity(new Intent(LoginActivity.this, IpConfigActivity.class));
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.loadSysConfig();
    }
}
