package com.kanci.ui.book.list;

import android.os.Bundle;

import com.kanci.data.model.bean.Book;
import com.kanci.ui.base.BaseActivity;
import com.kanci.ui.base.BaseViewModel;

import java.util.List;

public class ListActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public int getBindingId() {
        return 0;
    }

    @Override
    public BaseViewModel createViewModel() {
        return new ListViewModel(this);
    }

    public void onLoadBookList(List<Book> bookList) {

    }
}
