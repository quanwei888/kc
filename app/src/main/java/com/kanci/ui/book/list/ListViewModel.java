package com.kanci.ui.book.list;

import com.kanci.data.model.api.ApiException;
import com.kanci.data.model.bean.Book;
import com.kanci.ui.base.BaseActivity;
import com.kanci.ui.base.BaseViewModel;

import java.util.List;

public class ListViewModel extends BaseViewModel {

    @Override
    public ListActivity V() {
        return (ListActivity) super.V();
    }

    public void doLoadBookList() {
        new Query<List<Book>>() {
            @Override
            public List<Book> doQuery() throws ApiException {
                return DH().getBookList();
            }

            @Override
            public void onSuccess(List<Book> data) {
                V().onLoadBookList(data);
            }
        }.run();
    }
}
