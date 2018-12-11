package com.kanci.ui.main;

import android.databinding.ObservableField;

import com.kanci.data.model.db.Book;
import com.kanci.ui.BaseViewModel;
import com.kanci.utils.AppSession;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;


public class MainViewModel extends BaseViewModel {
    public static interface View {

    }

    @Inject
    public View view;
    public ObservableField<Book> book = new ObservableField<>();

    @Inject
    public MainViewModel() {
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
    }
}
