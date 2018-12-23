package com.kanci.ui.book.wordlist.fragment;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.kanci.data.model.api.ApiException;
import com.kanci.data.model.db.Word;
import com.kanci.ui.base.BaseViewModel;

/**
 * Created by qw on 18-12-23.
 */

public class ItemViewModel extends BaseViewModel {
    public ObservableField<Word> word = new ObservableField<>();
    public ObservableBoolean isFirst = new ObservableBoolean(false);
    public ObservableBoolean isLast = new ObservableBoolean(false);

    public ItemViewModel(BaseView view) {
        super(view);
    }

    @Override
    public ItemViewHolder V() {
        return (ItemViewHolder) super.V();
    }

    public void doSetWordTag(Word word, int tag) {
        new Post() {

            @Override
            public void doPost() throws ApiException {
                DH().setWordTag(word.bookId, word.word, tag);
            }

            @Override
            public void onSuccess() {
                ((ItemViewHolder) V()).onSetWord(word);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                V().onSetWord(word);
            }
        }.run();
    }
}
