package com.kanci.ui.task;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;

import com.kanci.BR;
import com.kanci.R;
import com.kanci.data.AppDataManager;
import com.kanci.data.model.db.BookState;
import com.kanci.data.model.db.BookWordDef;
import com.kanci.data.model.db.TaskWord;
import com.kanci.databinding.ActivityWordMainBinding;
import com.kanci.ui.MainActivity;
import com.kanci.ui.base.BaseActivity;
import com.kanci.ui.book.BookAddActivity;
import com.kanci.ui.word.WordStratege;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TaskMainActivity extends BaseActivity {
    public ViewModel vm;
    public ActivityWordMainBinding binding;

    public static Intent newIntent(Context context) {
        return new Intent(context, TaskMainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModel();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_word_main);
        binding.setVariable(BR.vm, vm);
        binding.executePendingBindings();
        setup();
    }

    public void setup() {
        vm.loadData();
    }

    public void complateTask() {

    }

    public class ViewModel {
        public AppDataManager dataManager = AppDataManager.instance(TaskMainActivity.this);

        public ObservableField<BookWordDef> wordDef = new ObservableField<>();
        public ObservableField<TaskWord> taskWord = new ObservableField<>();
        BookState bookState;
        public Task task;

        public ViewModel() {
        }

        public void loadData() {
            Single<Task> single = Single.create(emitter -> {
                //获取单词书状态
                bookState = dataManager.getBookStateLocal();

                //检查TaskWord是否缓存
                if (!bookState.taskIsCached) {
                    dataManager.cacheTaskWordList(bookState.bookId);
                }

                //获取TaskWordList
                List<TaskWord> taskWordList = dataManager.getTaskWordListLocal(bookState.bookId);

                //获取Worddef
                List<String> taskWords = new ArrayList<>();
                taskWordList.forEach(taskWord -> taskWords.add(taskWord.word));
                List<BookWordDef> wordDefList = dataManager.getBookWordDefByWords(bookState.bookId, taskWords);

                Map<String, TaskWord> taskWordMap = new HashMap<>();
                taskWordList.forEach(taskWord -> taskWordMap.put(taskWord.word, taskWord));
                Map<String, BookWordDef> wordDefMap = new HashMap<>();
                wordDefList.forEach(wordDef -> wordDefMap.put(wordDef.word, wordDef));

                Task task = new Task();
                taskWords.forEach(word -> {
                    Task.Item item = new Task.Item();
                    item.word = word;
                    item.taskWord = taskWordMap.get(word);
                    item.wordDef = wordDefMap.get(word);
                    task.addItem(item);
                });

                emitter.onSuccess(task);
            });

            CompositeDisposable compositeDisposable = new CompositeDisposable();
            Disposable disposable = single.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            result -> {
                                task = result;
                                task.createPlan();
                                next();
                            },
                            error -> TaskMainActivity.this.handleError(error)
                    );
            compositeDisposable.add(disposable);
        }

        public void saveTaskWord(TaskWord taskWord) {
            Maybe<Void> single = Maybe.create(emitter -> {
                dataManager.saveTaskWord(taskWord);
                emitter.onComplete();
            });

            CompositeDisposable compositeDisposable = new CompositeDisposable();
            Disposable disposable = single.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            result -> {
                            },
                            error -> TaskMainActivity.this.handleError(error),
                            () -> {
                            }
                    );
            compositeDisposable.add(disposable);
        }

        public void next() {
            Task.Item item = task.next();
            if (item == null) {
                TaskMainActivity.this.complateTask();
            } else {
                wordDef.set(item.wordDef);
                taskWord.set(item.taskWord);
            }
        }

        public void doneWord() {
            taskWord.get().tag = 1;
            saveTaskWord(taskWord.get());
        }

        public void answerWord() {
            taskWord.get().rightCount += 1;
            saveTaskWord(taskWord.get());
        }
    }
}
