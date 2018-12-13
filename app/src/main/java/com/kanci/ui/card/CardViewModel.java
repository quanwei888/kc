package com.kanci.ui.card;

import android.databinding.ObservableField;

import com.kanci.data.model.bean.Book;
import com.kanci.ui.base.BaseViewModel;

import javax.inject.Inject;


public class CardViewModel extends BaseViewModel {
    public static interface View {

    }

    @Inject
    public View view;
    public ObservableField<Book> book = new ObservableField<>();

    @Inject
    public CardViewModel() {
    }


    /**
     * 加载当前单词书
     */
    public void loadCurrentBook() {
    }

    /**
     * 加载当前用户信息
     */
    public void loadCurrentUser() {

    }

    public void selectBook() {
    }
}
