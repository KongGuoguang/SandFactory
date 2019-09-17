package com.fenjin.sandfactory.activity;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.fenjin.data.bean.PersonalInfo;
import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.databinding.ActivityPersonalInfoBinding;
import com.fenjin.sandfactory.viewmodel.PersonalInfoViewModel;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.io.File;

public class PersonalInfoActivity extends BaseActivity {

    private PersonalInfoViewModel viewModel;

    private PersonalInfo personalInfo;

    private final int REQUEST_CODE_PERMISSION = 0x01;
    private final int REQUEST_CODE_SELECT_PICTURE = 0x02;
    private final int REQUEST_CODE_CROP_PICTURE = 0x03;

    private Uri imageCropUri;
    private File cropFile;

    private QMUITipDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPersonalInfoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_personal_info);
        viewModel = ViewModelProviders.of(this).get(PersonalInfoViewModel.class);
        personalInfo = viewModel.dataRepository.getPersonalInfo();
        binding.setViewModel(viewModel);
        binding.setPersonalInfo(personalInfo);

        registerObserver();

        cropFile = new File(getExternalFilesDir("/"), "head.jpg");
        imageCropUri = Uri.fromFile(cropFile);
    }

    private void registerObserver() {
        viewModel.clickedViewId.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer == null) return;
                Intent intent = new Intent(PersonalInfoActivity.this, EditPersonalInfoActivity.class);
                switch (integer) {
                    case R.id.tv_cancel:
                        finish();
                        break;
                    case R.id.layout_head_img:
                        if (ContextCompat.checkSelfPermission(PersonalInfoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            //未授权，申请授权(从相册选择图片需要读取存储卡的权限)
                            ActivityCompat.requestPermissions(PersonalInfoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
                        } else {
                            //已授权，获取照片
                            choosePhoto();
                        }
                        break;
                    case R.id.layout_name:
                        intent.putExtra(EditPersonalInfoActivity.EDIT_INFO_TYPE, EditPersonalInfoActivity.EDIT_INFO_TYPE_NAME);
                        intent.putExtra(EditPersonalInfoActivity.EDIT_INFO_CONTENT, personalInfo.getName());
                        startActivity(intent);
                        break;
                    case R.id.layout_sex:
                        intent.putExtra(EditPersonalInfoActivity.EDIT_INFO_TYPE, EditPersonalInfoActivity.EDIT_INFO_TYPE_SEX);
                        String sex = personalInfo.getSex() == 2 ? "女" : "男";
                        intent.putExtra(EditPersonalInfoActivity.EDIT_INFO_CONTENT, sex);
                        startActivity(intent);
                        break;
                    case R.id.layout_phone_number:
                        intent.putExtra(EditPersonalInfoActivity.EDIT_INFO_TYPE, EditPersonalInfoActivity.EDIT_INFO_TYPE_PHONE);
                        intent.putExtra(EditPersonalInfoActivity.EDIT_INFO_CONTENT, personalInfo.getMobileTel());
                        startActivity(intent);
                        break;
                    case R.id.layout_address:
                        intent.putExtra(EditPersonalInfoActivity.EDIT_INFO_TYPE, EditPersonalInfoActivity.EDIT_INFO_TYPE_ADDRESS);
                        intent.putExtra(EditPersonalInfoActivity.EDIT_INFO_CONTENT, personalInfo.getAddress());
                        startActivity(intent);
                        break;
                    case R.id.layout_company:
                        intent.putExtra(EditPersonalInfoActivity.EDIT_INFO_TYPE, EditPersonalInfoActivity.EDIT_INFO_TYPE_COMPANY);
                        intent.putExtra(EditPersonalInfoActivity.EDIT_INFO_CONTENT, personalInfo.getCompany());
                        startActivity(intent);
                        break;
                }
            }
        });

        viewModel.loadingMsg.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s == null) return;
                if (!TextUtils.isEmpty(s)) {
                    if (loadingDialog == null) {
                        loadingDialog = new QMUITipDialog.Builder(PersonalInfoActivity.this)
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
    }

    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUEST_CODE_SELECT_PICTURE);
    }

    /**
     * 裁剪图片
     *
     * @param uri
     */
    public void cropImg(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");  //调用裁剪
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);  //设置宽高比例为1：1
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 700);   //设置裁剪图片宽高700x700
        intent.putExtra("outputY", 700);
        intent.putExtra("return-data", false);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);   //防止出现黑边框
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageCropUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());  //设置输出格式
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, REQUEST_CODE_CROP_PICTURE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            choosePhoto();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_SELECT_PICTURE) {
                if (data == null) return;
                cropImg(data.getData());
            }

            if (requestCode == REQUEST_CODE_CROP_PICTURE) {
                viewModel.uploadHeadImg(cropFile);
            }
        }
    }
}
