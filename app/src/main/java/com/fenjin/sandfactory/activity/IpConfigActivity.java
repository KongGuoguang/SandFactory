package com.fenjin.sandfactory.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.databinding.ActivityIpConfigBinding;
import com.fenjin.sandfactory.viewmodel.IpConfigViewModel;

public class IpConfigActivity extends BaseActivity {

    private IpConfigViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityIpConfigBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_ip_config);
        viewModel = ViewModelProviders.of(this).get(IpConfigViewModel.class);
        binding.setViewModel(viewModel);
        registerObserver();
    }

    private void registerObserver() {
        viewModel.finishActivity.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                finish();
            }
        });
    }
}
