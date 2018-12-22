package com.kanci.ui.task.card;

import android.databinding.ObservableField;
import android.view.View;

import com.kanci.data.model.api.ApiException;
import com.kanci.data.model.db.Word;
import com.kanci.ui.base.BaseActivity;
import com.kanci.ui.base.BaseViewModel;
import com.kanci.ui.task.TaskPlan;

import java.util.List;

public class CardViewModel extends BaseViewModel {
    public CardViewModel(BaseActivity activity) {
        super(activity);
    }

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
                List<Word> wordList = DH().getTaskWordListAddDefLocal(bookId);
                return new TaskPlan(wordList);
            }

            @Override
            public void onSuccess(TaskPlan data) {
                taskPlan = data;
                V().onLoadTaskWordList();
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

    public void onCommitAnswer(View view) {
        V().onCommitAnswer(view);
    }

    public void onDoneWord() {
        V().onDoneWord();
    }
}
