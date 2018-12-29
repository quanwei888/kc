package com.kanci.view.home;


import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.kanci.data.model.bean.Book;
import com.kanci.data.model.db.Word;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class WordItemViewModel extends ItemViewModel<BookWordViewModel> {
    public ObservableField<Word> word = new ObservableField();
    public boolean isSecFirst = true;

    public WordItemViewModel(@NonNull BookWordViewModel viewModel) {
        super(viewModel);
    }

    public WordItemViewModel(@NonNull BookWordViewModel viewModel, Word word) {
        super(viewModel);
        this.word.set(word);
    }

    public BindingCommand onItemClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

        }
    });
}
