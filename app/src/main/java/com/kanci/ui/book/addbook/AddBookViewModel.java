package com.kanci.ui.book.addbook;

import com.kanci.data.model.bean.Book;
import com.kanci.ui.BaseViewModel;
import com.kanci.ui.book.BookListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class AddBookViewModel extends BaseViewModel {
    public static interface View extends BaseViewModel.View {
        void showBookList(Map<String, BookListAdapter> adapters);
    }

    @Inject
    public AddBookViewModel.View view;

    public Map<String, List<Book>> books = new HashMap<>();
    public Map<String, BookListAdapter> adapters = new HashMap<>();


    @Inject
    public AddBookViewModel() {
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
                                BookListAdapter adapter = new BookListAdapter();
                                adapter.addAll(books.get(key));
                                adapters.put(key, adapter);
                            }
                            view.showBookList(adapters);
                        },
                        error -> {
                            view.handleError(error);
                        }
                );
    }

}