package com.kanci.view.book.list;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.kanci.BR;
import com.kanci.R;
import com.kanci.data.model.api.ApiException;
import com.kanci.data.model.bean.Book;
import com.kanci.view.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class BookListViewModel extends BaseViewModel {
    public BookListViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableList<ItemTagViewModel> itemList = new ObservableArrayList<>();
    public ItemBinding<ItemTagViewModel> itemBinding = ItemBinding.of(BR.vm, R.layout.item_book_tag);
    public BindingRecyclerViewAdapter<ItemTagViewModel> adapter = new BindingRecyclerViewAdapter<>();

    public void doLoadBookList() {
        new Query<List<Book>>() {
            @Override
            public List<Book> doQuery() throws ApiException {
                return DH().getBookList();
            }

            @Override
            public void onSuccess(List<Book> data) {
                String lastTag = "";
                List<ItemBookViewModel> bookList = new ArrayList<>();
                for (Book book : data) {
                    if (lastTag.equals("")) {
                        lastTag = book.tag;
                    }
                    ItemBookViewModel itemViewModel = new ItemBookViewModel(BookListViewModel.this, book);
                    if (!lastTag.equals(book.tag)) {
                        bookList.add(new ItemBookViewModel(BookListViewModel.this, book));
                        ItemTagViewModel tagVm = new ItemTagViewModel(BookListViewModel.this, lastTag, bookList);
                        itemList.add(tagVm);
                        bookList.clear();
                        lastTag = book.tag;
                    } else {
                        bookList.add(new ItemBookViewModel(BookListViewModel.this, book));
                    }
                }

                if (bookList.size() > 0) {
                    ItemTagViewModel tagVm = new ItemTagViewModel(BookListViewModel.this, lastTag, bookList);
                    itemList.add(tagVm);
                }
            }
        }.run();
    }
}
