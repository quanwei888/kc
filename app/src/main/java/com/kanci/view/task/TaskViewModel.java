package com.kanci.view.task;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.kanci.data.model.api.ApiException;
import com.kanci.data.model.db.Word;
import com.kanci.ui.task.TaskPlan;
import com.kanci.view.base.BaseViewModel;

import java.util.List;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * Created by qw on 18-12-29.
 */

public class TaskViewModel extends BaseViewModel {
    public TaskViewModel(@NonNull Application application) {
        super(application);
    }

    public TaskPlan taskPlan;
    public ObservableField<Word> word = new ObservableField<>();
    public ObservableList<String> options = new ObservableArrayList<>();
    public String anwser = "";
    public ObservableBoolean hide = new ObservableBoolean(true);

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public ObservableBoolean finishLoadTaskWord = new ObservableBoolean(false);
        public ObservableBoolean swichNextWord = new ObservableBoolean(false);
        public ObservableBoolean finishTask = new ObservableBoolean(false);
    }

    public BindingCommand onDoneWordCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.swichNextWord.set(!uc.swichNextWord.get());
            doSetWordTag(word.get(), 1);
        }
    });
    public BindingCommand onNextWordCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.swichNextWord.set(!uc.swichNextWord.get());
        }
    });
    public BindingCommand onTipCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            hide.set(!hide.get());
        }
    });
    public View.OnClickListener onSelectCommand = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button btn = (Button) v;
            if (btn.getText().equals(anwser)) {
                word.get().rightCount += 1;
            } else {
                word.get().errorCount += 1;
            }
            uc.swichNextWord.set(!uc.swichNextWord.get());
        }
    };
    public BindingCommand onSelect3Command = new BindingCommand(new BindingAction() {
        int index = 3;

        @Override
        public void call() {
            if (options.get(index).equals(anwser)) {
                word.get().rightCount += 1;
            } else {
                word.get().errorCount += 1;
            }
        }
    });

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
                uc.finishLoadTaskWord.set(!uc.finishLoadTaskWord.get());
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

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }.run();
    }

    public void doNextWord() {
        if (!taskPlan.hasNext()) {
            uc.finishTask.set(!uc.finishTask.get());
            return;
        }
        word.set(taskPlan.next());
        List<String> os = (List<String>) word.get().def.qaEn.get("option");
        options.clear();
        options.addAll(os);
        //anwser = os.get(Integer.parseInt(word.get().def.qaEn.get("rightIndex"))
    }
}
