package com.kanci.ui.book.wordlist;

import com.kanci.data.model.api.ApiException;
import com.kanci.data.model.db.Word;
import com.kanci.ui.base.BaseActivity;
import com.kanci.ui.base.BaseViewModel;

import java.util.List;

public class WordListViewModel extends BaseViewModel {

    public WordListViewModel(BaseView view) {
        super(view);
    }

    public void doLoadBookWordList(int bookId) {
        new Query<List<Word>>() {
            @Override
            public List<Word> doQuery() throws ApiException {
                return DH().getBookWordList(bookId);
            }

            @Override
            public void onSuccess(List<Word> data) {
                ((WordListActivity) V()).onLoadBookList(data);
            }
        }.run();
    }

    public void doSetWordTag(int bookId, String word, int tag) {
        new Post() {

            @Override
            public void doPost() throws ApiException {
                DH().setWordTag(bookId, word, tag);
            }

            @Override
            public void onSuccess() {
                ((WordListActivity) V()).onSetWord();
            }
        }.run();
    }
}
