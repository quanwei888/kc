package com.kanci.view.book.word;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.kanci.R;
import com.kanci.BR;
import com.kanci.data.model.api.ApiException;
import com.kanci.data.model.db.Word;
import com.kanci.view.base.BaseViewModel;

import java.util.List;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class BookWordViewModel extends BaseViewModel {
    public BookWordViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableList<WordItemViewModel> itemList = new ObservableArrayList<>();
    public ItemBinding<WordItemViewModel> itemBinding = ItemBinding.of(BR.vm, R.layout.item_word);
    public BindingRecyclerViewAdapter<WordItemViewModel> adapter = new BindingRecyclerViewAdapter<>();

    public void doLoadWordList(int bookId, int tag) {
        new Query<List<Word>>() {
            @Override
            public List<Word> doQuery() throws ApiException {
                return DH().getBookWordList(bookId, tag);
            }

            @Override
            public void onSuccess(List<Word> data) {
                data.forEach(word -> {
                    WordItemViewModel itemViewModel = new WordItemViewModel(BookWordViewModel.this, word);
                    itemList.add(itemViewModel);
                });
            }
        }.run();
    }
}
