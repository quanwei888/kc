package com.kanci.ui.main;

import android.databinding.ObservableField;

import com.kanci.data.model.db.Book;
import com.kanci.ui.base.BaseActivity;
import com.kanci.ui.base.BaseViewModel;
import com.kanci.ui.demo.DemoActivity;
import com.kanci.utils.AppSession;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.schedulers.Schedulers;


public class MainViewModel extends BaseViewModel {
    protected MainActivity view;
    public ObservableField<Book> book = new ObservableField<>();


    @Override
    public MainActivity getView() {
        return view;
    }

    @Override
    public void setView(BaseActivity view) {
        this.view = (MainActivity) view;
    }

    /**
     * 加载当前单词书
     */
    public void loadCurrentBook() {
        getDataManager().doGetCurrentBook(AppSession.uid)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(res -> {
                    book.set(res.data);
                });
    }

    /**
     * 加载当前用户信息
     */
    public void loadCurrentUser() {

    }

    public void selectBook() {
        getView().gotoSelectBookActivity();
    }
}
