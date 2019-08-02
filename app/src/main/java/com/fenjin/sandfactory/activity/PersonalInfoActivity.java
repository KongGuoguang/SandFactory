package com.fenjin.sandfactory.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;

import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.databinding.ActivityPersonalInfoBinding;
import com.fenjin.sandfactory.viewmodel.PersonalInfoViewModel;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

public class PersonalInfoActivity extends BaseActivity {

    private PersonalInfoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPersonalInfoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_personal_info);
        viewModel = ViewModelProviders.of(this).get(PersonalInfoViewModel.class);
        binding.setViewModel(viewModel);
        registerObserver();
    }

    private void registerObserver() {
        viewModel.showDialog.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer == null) return;
                final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(PersonalInfoActivity.this);
                switch (integer) {
                    case PersonalInfoViewModel.DIALOG_MODIFY_PHONE_NUMBER:
                        builder.setPlaceholder("请输入您的手机号");
                        builder.setInputType(InputType.TYPE_CLASS_PHONE);
                        builder.addAction("保存", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                                viewModel.modifyPhoneNumber(builder.getEditText().getText().toString());
                            }
                        });
                        break;
                    case PersonalInfoViewModel.DIALOG_MODIFY_ADDRESS:
                        builder.setPlaceholder("请输入您的地址");
                        builder.setInputType(InputType.TYPE_CLASS_TEXT);
                        builder.addAction("保存", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                                viewModel.modifyAddress(builder.getEditText().getText().toString());
                            }
                        });
                        break;
                    case PersonalInfoViewModel.DIALOG_MODIFY_COMPANY:
                        builder.setPlaceholder("请输入您的公司名称");
                        builder.setInputType(InputType.TYPE_CLASS_TEXT);
                        builder.addAction("保存", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                                viewModel.modifyCompany(builder.getEditText().getText().toString());
                            }
                        });
                        break;
                }
                builder.create().show();
            }
        });
    }
}
