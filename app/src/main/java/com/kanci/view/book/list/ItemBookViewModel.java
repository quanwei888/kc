package com.kanci.view.book.list;


import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.kanci.data.model.bean.Book;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class ItemBookViewModel extends ItemViewModel<BookListViewModel> {
    public ObservableField<Book> book = new ObservableField();
    public boolean isHeader = false;

    public ItemBookViewModel(@NonNull BookListViewModel viewModel) {
        super(viewModel);
    }

    public ItemBookViewModel(@NonNull BookListViewModel viewModel, Book book) {
        super(viewModel);
        this.book.set(book);
    }
    public BindingCommand onItemClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

        }
    });
}
