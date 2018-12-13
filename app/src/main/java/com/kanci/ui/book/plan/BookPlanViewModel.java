package com.kanci.ui.book.plan;

import android.databinding.ObservableField;

import com.kanci.data.model.bean.Book;
import com.kanci.ui.ActivityMgr;
import com.kanci.ui.base.BaseViewModel;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class BookPlanViewModel extends BaseViewModel {
    public static interface View extends BaseViewModel.View {
    }

    @Inject
    public BookPlanViewModel.View view;

    public ObservableField<Book> book = new ObservableField<>();


    @Inject
    public BookPlanViewModel() {
    }

    /**
     * 加载单词书列表
     */
    public void loadData() {

    }

    public void addBook(int plan) {
        Single<Void> single = Single.create(emitter -> {
            Book bk = book.get();
            //加载单词书的BookWord到本地
            getDataManager().cacheBookWordList(bk.bookId);
            //更新单词书的plan
            getDataManager().addBook(bk.bookId, plan);
            //创建任务
            getDataManager().createTask(bk.bookId);
            //更新本地的BookState
            getDataManager().cacheBookState();

            emitter.onSuccess(null);
        });
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        Disposable disposable = single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            //回主页
                            ActivityMgr.gotoMain(view);
                        },
                        error -> {
                            view.handleError(error);
                        }
                );
        compositeDisposable.add(disposable);
    }

}
