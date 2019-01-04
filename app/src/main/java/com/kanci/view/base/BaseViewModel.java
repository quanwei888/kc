package com.kanci.view.base;

import android.app.Application;
import android.support.annotation.NonNull;

import com.kanci.data.AppDataHelper;
import com.kanci.data.model.api.ApiException;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.utils.ToastUtils;

public abstract class BaseViewModel extends me.goldze.mvvmhabit.base.BaseViewModel {
    private AppDataHelper dh;

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public AppDataHelper DH() {
        if (dh == null) {
            dh = AppDataHelper.instance(getApplication());
        }
        return dh;
    }

    static SingleTransformer schedulersTransformer() {
        return new SingleTransformer() {
            @Override
            public SingleSource apply(Single upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public abstract class Query<T> {
        public Query() {
        }

        public void run() {
            Single<T> single = Single.create(emitter -> {
                emitter.onSuccess(doQuery());
            });
            CompositeDisposable disposable = new CompositeDisposable();
            disposable.add(single.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            data -> {
                                onSuccess(data);
                            },
                            error -> {
                                onError(error);
                            }
                    ));
        }

        public abstract T doQuery() throws ApiException;

        public void onSuccess(T data) {
        }

        public void onError(Throwable e) {
            ToastUtils.showLong(e.getMessage());
        }
    }

    public abstract class Post {
        public Post() {
        }

        public void run() {
            Maybe<Void> single = Maybe.create(emitter -> {
                doPost();
                emitter.onComplete();
            });
            CompositeDisposable disposable = new CompositeDisposable();
            disposable.add(single.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            result -> {
                            },
                            error -> {
                                onError(error);
                            },
                            () -> {
                                onSuccess();
                            }
                    ));
        }

        public abstract void doPost() throws ApiException;

        public void onSuccess() {
        }

        public void onError(Throwable e) {
            ToastUtils.showLong(e.getMessage());
        }
    }

}
