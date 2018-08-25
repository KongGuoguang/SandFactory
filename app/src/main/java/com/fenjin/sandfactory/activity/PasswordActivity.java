package com.fenjin.sandfactory.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.app.BaseActivity;
import com.fenjin.sandfactory.databinding.ActivityPasswordBinding;
import com.fenjin.sandfactory.util.ErrorCodeUtil;
import com.fenjin.sandfactory.viewmodel.PasswordViewModel;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

public class PasswordActivity extends BaseActivity {

    private PasswordViewModel viewModel;

    private QMUITipDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.translucent(this);
        ActivityPasswordBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_password);
        viewModel = ViewModelProviders.of(this).get(PasswordViewModel.class);
        binding.setViewModel(viewModel);
        registerObserver();
    }

    private void registerObserver(){
        viewModel.finishActivity.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                finish();
            }
        });

        viewModel.loading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean == null) return;
                if (aBoolean){
                    if (loadingDialog == null){
                        loadingDialog = new QMUITipDialog.Builder(PasswordActivity.this)
                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                                .setTipWord("正在修改")
                                .create();
                        loadingDialog.setCancelable(true);
                    }
                    loadingDialog.show();
                }else {
                    if (loadingDialog != null && loadingDialog.isShowing()){
                        loadingDialog.dismiss();
                    }
                }
            }
        });

        viewModel.errorCode.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer == null) return;
                if (integer == 0){
                    startActivity(new Intent(PasswordActivity.this, LoginActivity.class));
                    finish();
                    ToastUtils.showShort("修改成功，请重新登录");
                }else if(integer == ErrorCodeUtil.TOKEN_TIME_OUT){
                    startActivity(new Intent(PasswordActivity.this, LoginActivity.class));
                    finish();
                    ToastUtils.showShort("登录信息超时，请重新登录");
                }
            }
        });

        viewModel.errorMessage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                showErrorDialog(s);
            }
        });
    }
}
