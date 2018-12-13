package com.kanci.ui.main;

import android.databinding.ObservableField;

import com.kanci.data.model.bean.Book;
import com.kanci.data.model.db.BookState;
import com.kanci.ui.ActivityMgr;
import com.kanci.ui.base.BaseViewModel;
import com.kanci.ui.book.BookListAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainViewModel extends BaseViewModel {
    public interface View extends BaseViewModel.View {

    }

    @Inject
    public View view;
    public ObservableField<BookState> bookState = new ObservableField<>();

    @Inject
    public MainViewModel() {
    }

    public void loadData() {
        Single<BookState> single = Single.create(emitter -> {
            BookState bookState = getDataManager().getBookState();
            emitter.onSuccess(bookState);
        });

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        Disposable disposable = single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            if (result == null) {
                                ActivityMgr.gotoBookAdd(view);
                            } else {
                                bookState.set(result);
                            }
                        },
                        error -> {
                            view.handleError(error);
                        }
                );
        compositeDisposable.add(disposable);
        compositeDisposable.dispose();
    }

    public void runStudy() {
        ActivityMgr.gotoWordMain(view);
    }

    public void addBook() {
        ActivityMgr.gotoBookAdd(view);
    }
}
