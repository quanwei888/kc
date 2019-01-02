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

import java.util.List;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class BookListViewModel extends BaseViewModel {
    public BookListViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableList<ItemBookViewModel> itemList = new ObservableArrayList<>();
    public ItemBinding<ItemBookViewModel> itemBinding = ItemBinding.of(new OnItemBind<ItemBookViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, ItemBookViewModel item) {
            if (item.isHeader) {
                itemBinding.set(BR.vm, R.layout.item_book_header);
            } else {
                itemBinding.set(BR.vm, R.layout.item_book);
            }
        }
    });

    public BindingRecyclerViewAdapter<ItemBookViewModel> adapter = new BindingRecyclerViewAdapter<>();

    public void doLoadBookList() {
        new Query<List<Book>>() {
            @Override
            public List<Book> doQuery() throws ApiException {
                return DH().getBookList();
            }

            @Override
            public void onSuccess(List<Book> data) {
                String lastTag = "";
                for (Book book : data) {
                    ItemBookViewModel itemViewModel = new ItemBookViewModel(BookListViewModel.this, book);
                    if (!lastTag.equals(book.tag)) {
                        ItemBookViewModel headerViewModel = new ItemBookViewModel(BookListViewModel.this, book);
                        headerViewModel.isHeader = true;
                        itemList.add(headerViewModel);
                    }
                    lastTag = itemViewModel.book.get().tag;
                    itemList.add(itemViewModel);
                }
                ;
            }
        }.run();
    }
}
