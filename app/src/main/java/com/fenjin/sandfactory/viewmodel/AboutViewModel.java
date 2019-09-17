package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.ObservableField;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import com.bumptech.glide.request.FutureTarget;
import com.fenjin.sandfactory.glide.GlideApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AboutViewModel extends BaseViewModel {
    public AboutViewModel(@NonNull Application application) {
        super(application);
        versionName.set(getVersionName(getApplication()));
    }

    public ObservableField<String> sysLogo = new ObservableField<>(dataRepository.getSysLogoUrl());

    public ObservableField<String> sysName = new ObservableField<>(dataRepository.getSysName());

    public ObservableField<String> sysQrImg = new ObservableField<>(dataRepository.getSysQrImgUrl());


    public ObservableField<String> versionName = new ObservableField<>();

    public ObservableField<String> updateResult = new ObservableField<>();

    public MutableLiveData<Boolean> finishActivity = new MutableLiveData<>();

    public void finishActivity(){
        finishActivity.postValue(true);
    }


    /**
     * 获取软件版本号名称
     */
    private String getVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "V" + localVersion;
    }

    public void saveQrImg() {
        Observable.just(sysQrImg.get())
                .map(new Function<String, File>() {
                    @Override
                    public File apply(String s) throws Exception {
                        FutureTarget<File> futureTarget = GlideApp.with(getApplication()).asFile().load(s).submit();
                        return futureTarget.get();
                    }
                })
                .map(new Function<File, Boolean>() {
                    @Override
                    public Boolean apply(File file) throws Exception {
                        File target = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), file.getName());
                        FileInputStream fileInputStream = new FileInputStream(file);
                        FileOutputStream fileOutputStream = new FileOutputStream(target);
                        byte[] buffer = new byte[1024];
                        int byteRead;
                        while (-1 != (byteRead = fileInputStream.read(buffer))) {
                            fileOutputStream.write(buffer, 0, byteRead);
                        }
                        fileInputStream.close();
                        fileOutputStream.flush();
                        fileOutputStream.close();

                        MediaStore.Images.Media.insertImage(getApplication().getContentResolver(),
                                target.getAbsolutePath(), target.getName(), null);

                        getApplication().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(target)));


                        return true;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        showToast("保存成功");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
