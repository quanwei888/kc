package com.kanci.ui.task.card;

import android.databinding.ObservableField;

import com.kanci.data.model.api.ApiException;
import com.kanci.data.model.db.Word;
import com.kanci.ui.base.BaseViewModel;
import com.kanci.ui.task.TaskPlan;

import java.util.List;

public class CardViewModel extends BaseViewModel {
    public TaskPlan taskPlan;
    public ObservableField<Word> word = new ObservableField<>();

    @Override
    public CardActivity V() {
        return (CardActivity) super.V();
    }

    public void doLoadTaskWordList(int bookId) {
        new Query<TaskPlan>() {
            @Override
            public TaskPlan doQuery() throws ApiException {
                List<Word> wordList = DH().getTaskWordListAddDef(bookId);
                return new TaskPlan(wordList);
            }

            @Override
            public void onSuccess(TaskPlan data) {
                taskPlan = data;
                V().onLoadTaskWordList();
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
                V().onSetWord();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                V().onSetWord();
            }
        }.run();
    }

    public void doNextWord() {
        if (!taskPlan.hasNext()) {
            V().onComplateTaskPlan();
            return;
        }
        word.set(taskPlan.next());
    }
}
