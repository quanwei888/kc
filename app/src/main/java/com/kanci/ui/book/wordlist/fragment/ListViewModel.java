package com.kanci.ui.book.wordlist.fragment;

import com.kanci.data.model.api.ApiException;
import com.kanci.data.model.db.Word;
import com.kanci.ui.base.BaseViewModel;

import java.util.List;

public class ListViewModel extends BaseViewModel {
    @Override
    public ListFragment V() {
        return (ListFragment) super.V();
    }

    public void doLoadBookWordList(int bookId, int tag) {
        new Query<List<Word>>() {
            @Override
            public List<Word> doQuery() throws ApiException {
                return DH().getBookWordList(bookId, tag);
            }

            @Override
            public void onSuccess(List<Word> data) {
                V().onLoadBookList(data);
            }
        }.run();
    }

    public void doSetWordTag(Word word, int tag) {
        new Post() {

            @Override
            public void doPost() throws ApiException {
                DH().setWordTag(word.bookId, word.word, tag);
            }

            @Override
            public void onSuccess() {
                //((ItemViewHolder) V()).onSetWord(word);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                //V().onSetWord(word);
            }
        }.run();
    }
}
