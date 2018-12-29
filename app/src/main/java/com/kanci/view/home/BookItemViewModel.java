package com.kanci.view.home;


import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.kanci.data.model.bean.Book;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class BookItemViewModel extends ItemViewModel<BookListViewModel> {
    public ObservableField<Book> book = new ObservableField();
    public boolean isSecFirst = true;

    public BookItemViewModel(@NonNull BookListViewModel viewModel) {
        super(viewModel);
    }

    public BookItemViewModel(@NonNull BookListViewModel viewModel, Book book) {
        super(viewModel);
        this.book.set(book);
    }
    public BindingCommand onItemClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

        }
    });
}
