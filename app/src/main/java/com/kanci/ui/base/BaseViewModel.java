package com.kanci.ui.base;

import com.kanci.data.AppDataHelper;
import com.kanci.data.model.api.ApiException;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseViewModel {
    private AppDataHelper DH;
    private BaseView view;

    public AppDataHelper DH() {
        return DH;
    }

    public BaseViewModel(BaseActivity view) {
        this.view = view;
        DH = view.createDH();
    }

    public Object V() {
        return view;
    }

    public abstract class Query<T> {
        public void run() {
            Single<T> single = Single.create(emitter -> {
                emitter.onSuccess(doQuery());
            });

            CompositeDisposable compositeDisposable = new CompositeDisposable();
            Disposable disposable = single.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            data -> {
                                onSuccess(data);
                            },
                            error -> {
                                onError(error);
                            }
                    );
            compositeDisposable.add(disposable);
        }

        public abstract T doQuery() throws ApiException;

        public abstract void onSuccess(T data);

        public void onError(Throwable e) {
            view.handleError(e);
        }
    }

    public abstract class Post {

        public void run() {
            Maybe<Void> single = Maybe.create(emitter -> {
                doPost();
                emitter.onComplete();
            });

            CompositeDisposable compositeDisposable = new CompositeDisposable();
            Disposable disposable = single.subscribeOn(Schedulers.io())
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
                    );
            compositeDisposable.add(disposable);
        }

        public abstract void doPost() throws ApiException;

        public abstract void onSuccess();

        public void onError(Throwable e) {
            view.handleError(e);
        }
    }

    public static interface BaseView {

        AppDataHelper createDH();

        void handleError(Throwable e);
    }
}