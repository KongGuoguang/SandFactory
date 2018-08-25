package com.fenjin.sandfactory.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fenjin.data.entity.ChengZhongRecord;
import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.app.BaseActivity;
import com.fenjin.sandfactory.databinding.ActivityDetailBinding;
import com.fenjin.sandfactory.viewmodel.DetailViewModel;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

public class DetailActivity extends BaseActivity {

    private DetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.translucent(this);
        ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        binding.setViewModel(viewModel);

        ChengZhongRecord record = (ChengZhongRecord) getIntent().getSerializableExtra("record");
        binding.setRecord(record);

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
