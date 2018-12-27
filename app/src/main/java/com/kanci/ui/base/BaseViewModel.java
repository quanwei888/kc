package com.kanci.ui.base;

import android.arch.lifecycle.ViewModel;

import com.kanci.data.AppDataHelper;
import com.kanci.data.model.api.ApiException;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BaseViewModel extends ViewModel {
    private AppDataHelper DH;
    private BaseView view;
    CompositeDisposable disposable = new CompositeDisposable();

    public void init(BaseView view) {
        this.view = view;
        DH = view.createDH();
    }

    public AppDataHelper DH() {
        return DH;
    }


    public BaseView V() {
        return view;
    }

    public abstract class Query<T> {
        public ICallback<T> callback;

        public Query() {
        }

        public Query(ICallback<T> callback) {
            this.callback = callback;
        }

        public void run() {
            Single<T> single = Single.create(emitter -> {
                emitter.onSuccess(doQuery());
            });

            disposable.add(single.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            data -> {
                                if (callback != null) {
                                    callback.call(data);
                                }
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
            view.handleError(e);
        }
    }

    public abstract class Post {

        public void run() {
            Maybe<Void> single = Maybe.create(emitter -> {
                doPost();
                emitter.onComplete();
            });

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
