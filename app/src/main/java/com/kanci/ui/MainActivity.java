package com.kanci.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;

import com.kanci.BR;
import com.kanci.R;
import com.kanci.data.AppDataManager;
import com.kanci.data.model.api.ApiException;
import com.kanci.data.model.db.BookState;
import com.kanci.databinding.ActivityMainBinding;
import com.kanci.ui.base.BaseActivity;
import com.kanci.ui.book.BookListActivity;
import com.kanci.ui.word.WordMainActivity;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    public ViewModel vm;
    public ActivityMainBinding binding;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModel();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setVariable(BR.vm, vm);
        binding.executePendingBindings();
        setup();
    }

    protected void setup() {
        vm.loadData();
    }

    public class ViewModel {
        public AppDataManager dataManager = AppDataManager.instance(MainActivity.this);
        public ObservableField<BookState> bookState = new ObservableField<>();


        public void loadData() {
            Single<BookState> single = Single.create(emitter -> {
                BookState localBookState = dataManager.getBookStateLocal();
                BookState remoteBookState = null;

                try {
                    remoteBookState = dataManager.getBookStateRemote();
                } catch (ApiException e) {
                    //云端没有Bookstate,remoteBookState = null
                } catch (Exception e) {
                    //网络异常，直接用本地数据
                    emitter.onSuccess(localBookState);
                    return;
                }

                if (localBookState == null ||
                        (remoteBookState != null && remoteBookState.taskId == localBookState.taskId)) {
                    //本地与云端数据不一致，用云端更新
                    dataManager.saveBookState(remoteBookState);
                }
                emitter.onSuccess(remoteBookState);

            });

            CompositeDisposable compositeDisposable = new CompositeDisposable();
            Disposable disposable = single.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            result -> {
                                if (result == null) {
                                    //null表示当前没有任务
                                    addBook();
                                } else {
                                    bookState.set(result);
                                }
                            },
                            error -> {
                                MainActivity.this.handleError(error);
                            }
                    );
            compositeDisposable.add(disposable);
        }

        public void runStudy() {
            startActivity(WordMainActivity.newIntent(MainActivity.this));
        }

        public void addBook() {
            startActivity(BookListActivity.newIntent(MainActivity.this));
        }
    }
}