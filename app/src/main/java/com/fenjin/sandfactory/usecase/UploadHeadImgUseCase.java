package com.fenjin.sandfactory.usecase;

import android.content.Context;

import com.fenjin.data.entity.UploadHeadImgResult;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadHeadImgUseCase extends BaseUseCase<UploadHeadImgResult> {
    public UploadHeadImgUseCase(Context context) {
        super(context);
    }

    private File file;

    public UploadHeadImgUseCase upload(File file) {
        this.file = file;
        return this;
    }

    @Override
    public Observable<UploadHeadImgResult> buildObservable() {
        return Observable.just(file)
                .map(new Function<File, MultipartBody.Part>() {
                    @Override
                    public MultipartBody.Part apply(File file) throws Exception {
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
                        return MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                    }
                }).flatMap(new Function<MultipartBody.Part, ObservableSource<UploadHeadImgResult>>() {
                    @Override
                    public ObservableSource<UploadHeadImgResult> apply(MultipartBody.Part bytes) throws Exception {
                        return dataRepository.uploadHeadImg(bytes);
                    }
                }).doOnNext(new Consumer<UploadHeadImgResult>() {
                    @Override
                    public void accept(UploadHeadImgResult uploadHeadImgResult) throws Exception {
                        if (uploadHeadImgResult.getFlag() == 1) {
                            String url = "http://" + dataRepository.getIp() + ":" + dataRepository.getPort() + uploadHeadImgResult.getResult();
                            dataRepository.getPersonalInfo().setHeadImg(url);
                        }
                    }
                });
    }
}
