package com.fenjin.sandfactory.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;

import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.databinding.ActivityEditPersonalInfoBinding;
import com.fenjin.sandfactory.viewmodel.EditPersonalInfoViewModel;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

public class EditPersonalInfoActivity extends BaseActivity {

    public static final String EDIT_INFO_CONTENT = "edit_info_content";

    public static final String EDIT_INFO_TYPE = "edit_info_type";

    public static final int EDIT_INFO_TYPE_NAME = 0;
    public static final int EDIT_INFO_TYPE_SEX = 1;
    public static final int EDIT_INFO_TYPE_PHONE = 2;
    public static final int EDIT_INFO_TYPE_ADDRESS = 3;
    public static final int EDIT_INFO_TYPE_COMPANY = 4;

    private EditPersonalInfoViewModel viewModel;

    private QMUITipDialog loadingDialog;

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEditPersonalInfoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_personal_info);
        viewModel = ViewModelProviders.of(this).get(EditPersonalInfoViewModel.class);
        binding.setViewModel(viewModel);

        Intent intent = getIntent();
        viewModel.editType.set(intent.getIntExtra(EDIT_INFO_TYPE, EDIT_INFO_TYPE_NAME));
        viewModel.inputContent.set(intent.getStringExtra(EDIT_INFO_CONTENT));

        registerObserver();
        editText = binding.editText;


    }

    @Override
    protected void onResume() {
        super.onResume();
        editText.setSelection(editText.length());
        editText.requestFocus();
    }

    private void registerObserver() {

        viewModel.loadingMsg.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s == null) return;
                if (!TextUtils.isEmpty(s)) {
                    if (loadingDialog == null) {
                        loadingDialog = new QMUITipDialog.Builder(EditPersonalInfoActivity.this)
                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                                .setTipWord(s)
                                .create();
                        loadingDialog.setCancelable(true);
                    }
                    loadingDialog.show();
                } else {
                    if (loadingDialog != null && loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                    }
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

        viewModel.errorMsg.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                showErrorDialog(s);
            }
        });

        viewModel.clickedViewId.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer == null) return;
                if (integer == R.id.tv_cancel) {
                    finish();
                } else if (integer == R.id.tv_save) {
                    viewModel.editPersonalInfo();
                }
            }
        });

    }


}
