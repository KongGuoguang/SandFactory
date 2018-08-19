package com.fenjin.sandfactory.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.databinding.ActivityAboutBinding;
import com.fenjin.sandfactory.viewmodel.AboutViewModel;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

public class AboutActivity extends AppCompatActivity {

    private AboutViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.translucent(this);
        ActivityAboutBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_about);
        viewModel = ViewModelProviders.of(this).get(AboutViewModel.class);
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
