package com.kanci.ui.word;

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
import com.kanci.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WordMainActivity extends BaseActivity {
    public ViewModel vm;
    public ActivityWordMainBinding binding;

    public static Intent newIntent(Context context) {
        return new Intent(context, WordMainActivity.class);
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

    public class ViewModel {
        public AppDataManager dataManager = AppDataManager.instance(WordMainActivity.this);

        public ObservableField<BookWordDef> wordDef = new ObservableField<>();
        public WordStratege wordStratege;

        public ViewModel() {
        }

        public void loadData() {
            Single<Map<String, Object>> single = Single.create(emitter -> {
                //获取单词书状态
                BookState bookState = dataManager.getBookState();
                //缓存TaskWord，@TODO 优化，判断是否已经缓存了
                dataManager.cacheTaskWordList(bookState.bookId);
                //获取TaskWord
                List<TaskWord> taskWordList = dataManager.getTaskWordListLocal(bookState.bookId);

                List<String> words = new ArrayList<>();
                for (TaskWord taskWord : taskWordList) {
                    words.add(taskWord.word);
                }
                //获取Worddef
                List<BookWordDef> wordDefList = dataManager.getBookWordDefByWords(bookState.bookId, words);

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
                                if(wordStratege.hasNext()) {
                                    String word = wordStratege.next();
                                    wordDef.set(wordStratege.wordDef(word));
                                }
                            },
                            error -> {
                                WordMainActivity.this.handleError(error);
                            }
                    );
            compositeDisposable.add(disposable);
        }

    }

}
