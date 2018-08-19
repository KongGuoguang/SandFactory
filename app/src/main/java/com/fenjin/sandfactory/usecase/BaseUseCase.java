package com.fenjin.sandfactory.usecase;


import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.fenjin.data.DataRepository;
import com.fenjin.sandfactory.app.BaseApplication;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Author:kongguoguang
 * Date:2018-05-17
 * Time:9:31
 * Summary:
 */
public abstract class BaseUseCase<T> {

    protected Context context;

    protected DataRepository dataRepository;

    public BaseUseCase(Context context){
        this.context = context;
        dataRepository = ((BaseApplication) context.getApplicationContext()).getDataRepository();
    }

    protected final String TAG = this.getClass().getSimpleName();

    public abstract Observable<T> buildObservable();

    private Consumer<Throwable> throwableConsumer;

    private Consumer<T> defaultConsumer;

    public Disposable execute(){
        return buildObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getDefaultConsumer(), getThrowableConsumer());
    }

    public void execute(Observer<T> observer){
        buildObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public Disposable execute(Consumer<T> consumer){
        return buildObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer, getThrowableConsumer());
    }

    private Consumer<Throwable> getThrowableConsumer(){
        if (throwableConsumer == null){
            throwableConsumer = new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    LogUtils.e(TAG, throwable.toString());
                }
            };
        }

        return throwableConsumer;
    }

    private Consumer<T> getDefaultConsumer(){
        if (defaultConsumer == null){
            defaultConsumer = new Consumer<T>() {
                @Override
                public void accept(T t) throws Exception {

                }
            };
        }

        return defaultConsumer;
    }
}
