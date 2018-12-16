package com.kanci.ui.word.main;

import android.databinding.ObservableField;

import com.kanci.data.model.db.BookState;
import com.kanci.data.model.db.BookWordDef;
import com.kanci.data.model.db.TaskWord;
import com.kanci.ui.base.BaseViewModel;
import com.kanci.ui.word.WordStratege;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainViewModel extends BaseViewModel {
    public interface View extends BaseViewModel.View {
        void showWord(TaskWord taskWord, BookWordDef wordDef, int fragmentType);

        void onComplete();

        void showTip();

    }

    @Inject
    public View view;

    /**/
    public ObservableField<BookState> bookState = new ObservableField<>();
    public ObservableField<BookWordDef> wordDef = new ObservableField<>();
    public WordStratege wordStratege;

    @Inject
    public MainViewModel() {
    }

    public void loadData() {
        Single<Map<String, Object>> single = Single.create(emitter -> {
            //获取单词书状态
            BookState bookState = getDataManager().getBookState();
            //缓存TaskWord，@TODO 优化，判断是否已经缓存了
            getDataManager().cacheTaskWordList(bookState.bookId);
            //获取TaskWord
            List<TaskWord> taskWordList = getDataManager().getTaskWordList(bookState.bookId);

            List<String> words = new ArrayList<>();
            for (TaskWord taskWord : taskWordList) {
                words.add(taskWord.word);
            }
            //获取Worddef
            List<BookWordDef> wordDefList = getDataManager().getBookWordDefByWords(bookState.bookId, words);

            Map<String, Object> data = new HashMap<>();
            data.put("taskWordList", taskWordList);
            data.put("wordDefList", wordDefList);
            emitter.onSuccess(data);
        });

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        Disposable disposable = single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            wordStratege = new WordStratege((List<TaskWord>) result.get("taskWordList"),
                                    (List<BookWordDef>) result.get("wordDefList"));
                            nextWord();
                        },
                        error -> {
                            view.handleError(error);
                        }
                );
        compositeDisposable.add(disposable);
    }

    public void nextWord() {
        if (wordStratege.hasNext()) {
            String word = wordStratege.next();
            view.showWord(wordStratege.taskWord(word), wordStratege.wordDef(word), 0);
        }
    }

    public void showTip() {
        view.showTip();
    }
}
