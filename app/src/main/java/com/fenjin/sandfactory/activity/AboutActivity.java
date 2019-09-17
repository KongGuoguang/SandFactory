package com.fenjin.sandfactory.activity;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;

import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.databinding.ActivityAboutBinding;
import com.fenjin.sandfactory.viewmodel.AboutViewModel;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

public class AboutActivity extends BaseActivity {

    private AboutViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAboutBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_about);
        viewModel = ViewModelProviders.of(this).get(AboutViewModel.class);
        binding.setViewModel(viewModel);
        registerObserver();

        binding.ivQr.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (!TextUtils.isEmpty(viewModel.sysQrImg.get())) {
                    new QMUIDialog.MessageDialogBuilder(AboutActivity.this)
                            .setTitle("保存到相册？")
                            .addAction("取消", new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    dialog.dismiss();
                                }
                            })
                            .addAction("确定", new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    dialog.dismiss();
                                    if (ContextCompat.checkSelfPermission(AboutActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                        //未授权，申请授权(从相册选择图片需要读取存储卡的权限)
                                        ActivityCompat.requestPermissions(AboutActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x01);
                                    } else {
                                        //已授权，保存照片
                                        viewModel.saveQrImg();
                                    }
                                }
                            })
                            .show();
                }
                return false;
            }
        });
    }

    private void registerObserver(){

        viewModel.finishActivity.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                finish();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            viewModel.saveQrImg();
        }
    }
}
