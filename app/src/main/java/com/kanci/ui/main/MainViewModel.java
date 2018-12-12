package com.kanci.ui.main;

import android.databinding.ObservableField;

import com.kanci.data.model.db.Book;
import com.kanci.data.model.db.BookState;
import com.kanci.data.model.db.BookTask;
import com.kanci.ui.ActivityMgr;
import com.kanci.ui.BaseViewModel;
import com.kanci.utils.AppSession;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainViewModel extends BaseViewModel {
    public interface View extends BaseViewModel.View {

    }

    @Inject
    public View view;
    public ObservableField<BookState> bookState = new ObservableField<>();
    public ObservableField<BookTask> bookTask = new ObservableField<>();

    @Inject
    public MainViewModel() {
    }

    public void loadData() {
        getDataManager().getCurrentBookState()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            if (result == null) {
                                ActivityMgr.gotoSelectBook(view);
                            } else {
                                bookState.set(result);
                                loadBookTask(result.bookId);
                            }
                        },
                        error -> {
                            view.handleError(error);
                        }
                ).dispose();
    }

    public void loadBookTask(int bookId) {
        getDataManager().getBookTask(bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            bookTask.set(result);
                        },
                        error -> {
                            view.handleError(error);
                        }
                ).dispose();
    }
}
