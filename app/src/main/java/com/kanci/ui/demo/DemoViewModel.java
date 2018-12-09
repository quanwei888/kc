package com.kanci.ui.demo;

import android.databinding.ObservableField;

import com.kanci.ui.base.BaseActivity;
import com.kanci.ui.base.BaseViewModel;
import com.kanci.ui.main.MainActivity;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.schedulers.Schedulers;


public class DemoViewModel extends BaseViewModel {
    protected DemoActivity view;

    public ObservableField<String> user = new ObservableField<>();

    public DemoViewModel() {
        user.set("Hello");
    }


    @Override
    public DemoActivity getView() {
        return view;
    }

    @Override
    public void setView(BaseActivity view) {
        this.view = (DemoActivity) view;
    }


    public void updateUser() {
        Single.create(new SingleOnSubscribe<String>() {

            @Override
            public void subscribe(SingleEmitter emitter) throws Exception {
                Thread.sleep(5000);
                emitter.onSuccess("Hello Bob");
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(res -> user.set(res), e -> getView().handleError(e));
    }
}
