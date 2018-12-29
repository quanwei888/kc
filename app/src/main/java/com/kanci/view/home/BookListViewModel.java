package com.kanci.view.home;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.kanci.R;
import com.kanci.BR;
import com.kanci.data.model.api.ApiException;
import com.kanci.data.model.bean.Book;

import java.util.List;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class BookListViewModel extends BaseViewModel {
    public BookListViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableList<BookItemViewModel> itemList = new ObservableArrayList<>();
    public ItemBinding<BookItemViewModel> itemBinding = ItemBinding.of(BR.vm, R.layout.item_book);
    public BindingRecyclerViewAdapter<BookItemViewModel> adapter = new BindingRecyclerViewAdapter<>();

    public void doLoadBookList() {
        new Query<List<Book>>() {
            @Override
            public List<Book> doQuery() throws ApiException {
                return DH().getBookList();
            }

            @Override
            public void onSuccess(List<Book> data) {
                data.forEach(book -> {
                BookItemViewModel itemViewModel = new BookItemViewModel(BookListViewModel.this, book);
                itemList.add(itemViewModel);
            });
            }
        }.run();
    }
}
