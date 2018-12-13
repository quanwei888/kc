package com.kanci.ui.book.plan;

import android.databinding.ObservableField;

import com.kanci.data.model.bean.Book;
import com.kanci.ui.BaseViewModel;

import javax.inject.Inject;


public class BookPlanViewModel extends BaseViewModel {
    public static interface View extends BaseViewModel.View {
    }

    @Inject
    public BookPlanViewModel.View view;

    public ObservableField<Book> book = new ObservableField<>();


    @Inject
    public BookPlanViewModel() {
    }

    /**
     * 加载单词书列表
     */
    public void loadData() {

    }

}
