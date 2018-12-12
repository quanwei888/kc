package com.kanci.ui.selectbook;

import android.databinding.ObservableField;

import com.kanci.data.model.bean.Book;
import com.kanci.data.model.db.BookState;
import com.kanci.ui.ActivityMgr;
import com.kanci.ui.BaseViewModel;
import com.kanci.utils.AppSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class SelectBookViewModel extends BaseViewModel {
    public static interface View extends BaseViewModel.View {
        void showBookList(Map<String, SelectBookAdapter> adapters);
    }

    @Inject
    public SelectBookViewModel.View view;

    public Map<String, List<Book>> books = new HashMap<>();
    public Map<String, SelectBookAdapter> adapters = new HashMap<>();


    @Inject
    public SelectBookViewModel() {
    }

    /**
     * 加载单词书列表
     */
    public void loadData() {

        Single<List<Book>> single = Single.create(emitter -> {
            List<Book> bookList = getDataManager().getBookList();
            emitter.onSuccess(bookList);
        });
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            for (Book book : result) {
                                if (!books.containsKey(book.tag)) {
                                    books.put(book.tag, new ArrayList<>());
                                }
                                books.get(book.tag).add(book);
                            }
                            for (String key : books.keySet()) {
                                SelectBookAdapter adapter = new SelectBookAdapter();
                                adapter.addAll(books.get(key));
                                adapters.put(key, adapter);
                            }
                        },
                        error -> {
                            view.handleError(error);
                        }
                ).dispose();
    }

}
