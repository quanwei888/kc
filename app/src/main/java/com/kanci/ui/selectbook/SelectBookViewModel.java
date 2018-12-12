package com.kanci.ui.selectbook;

import android.databinding.ObservableField;

import com.kanci.data.model.db.Book;
import com.kanci.ui.BaseViewModel;
import com.kanci.utils.AppSession;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;


public class SelectBookViewModel extends BaseViewModel {
    public static interface View {

    }

    @Inject
    public SelectBookViewModel.View view;
    @Inject
    public SelectBookAdapter adapter;
    public ObservableField<Book> book = new ObservableField<>();


    @Inject
    public SelectBookViewModel() {
    }

    /**
     * 加载单词书列表
     */
    public void loadBookList() {
        getDataManager().doGetBookList(AppSession.uid)
                .subscribeOn(Schedulers.io())
                //.observeOn(Schedulers.SelectBookThread())
                .subscribe(res -> {
                    adapter.addAll(res.data);
                });
    }

}
