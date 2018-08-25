package com.fenjin.sandfactory.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.app.BaseActivity;
import com.fenjin.sandfactory.databinding.ActivityPasswordBinding;
import com.fenjin.sandfactory.viewmodel.PasswordViewModel;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

public class PasswordActivity extends BaseActivity {

    private PasswordViewModel viewModel;

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
    }
}
