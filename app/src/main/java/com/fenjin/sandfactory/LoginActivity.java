package com.fenjin.sandfactory;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fenjin.sandfactory.databinding.ActivityLoginBinding;
import com.fenjin.sandfactory.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        LoginViewModel viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding.setViewModel(viewModel);
    }
}
