package com.kanci.view.book.list;


import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.view.View;

import com.kanci.BR;
import com.kanci.R;

import java.util.List;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class ItemTagViewModel extends ItemViewModel<BookListViewModel> {
    public ObservableField<String> tag = new ObservableField();
    public ObservableList<ItemBookViewModel> itemList = new ObservableArrayList<>();
    public ItemBinding<ItemBookViewModel> itemBinding = ItemBinding.of(BR.vm, R.layout.item_book);
    public BindingRecyclerViewAdapter<ItemBookViewModel> adapter = new BindingRecyclerViewAdapter<>();
    public ObservableBoolean isFolder = new ObservableBoolean(true);

    public ItemTagViewModel(@NonNull BookListViewModel viewModel) {
        super(viewModel);
    }

    public ItemTagViewModel(@NonNull BookListViewModel viewModel, String tag, List<ItemBookViewModel> bookList) {
        super(viewModel);
        this.tag.set(tag);
        this.itemList.addAll(bookList);
    }

    public View.OnClickListener onItemClickCommand = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            isFolder.set(!isFolder.get());
        }
    };
}
