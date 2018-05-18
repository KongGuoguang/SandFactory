package com.fenjin.sandfactory;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.os.Bundle;

import com.fenjin.sandfactory.databinding.ActivityLoginBinding;
import com.fenjin.sandfactory.viewmodel.LoginViewModel;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

public class LoginActivity extends BaseActivity {

    private QMUITipDialog loginDialog;

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
                }else {
                    showErrorDialog(viewModel.loginErrorMessage);
                }
            }
        });

        viewModel.loginIng.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean == null) return;
                if (aBoolean){
                   if (loginDialog == null){
                       loginDialog = new QMUITipDialog.Builder(LoginActivity.this)
                               .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                               .setTipWord("正在登录")
                               .create();
                       loginDialog.setCancelable(false);
                   }
                   loginDialog.show();
                }else {
                    if (loginDialog != null && loginDialog.isShowing()){
                        loginDialog.dismiss();
                    }
                }
            }
        });
    }
}
