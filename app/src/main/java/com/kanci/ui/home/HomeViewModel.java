package com.kanci.ui.home;

import android.databinding.ObservableField;

import com.kanci.data.model.api.ApiException;
import com.kanci.data.model.bean.Book;
import com.kanci.data.model.bean.Task;
import com.kanci.ui.base.BaseActivity;
import com.kanci.ui.base.BaseViewModel;


public class HomeViewModel extends BaseViewModel {
    public ObservableField<Task> task = new ObservableField<>();
    public ObservableField<Book> book = new ObservableField<>();


    @Override
    public HomeActivity V() {
        return (HomeActivity) super.V();
    }

    public void doLoadTask() {
        new Query<Task>() {
            @Override
            public Task doQuery() throws ApiException {
                return DH().getTask();
            }

            @Override
            public void onSuccess(Task data) {
                task.set(data);
                V().onLoadTask(data);
            }
        }.run();
    }

    public void doLoadBook(int bookId) {
        new Query<Book>() {

            @Override
            public Book doQuery() throws ApiException {
                return DH().getBook(bookId);
            }

            @Override
            public void onSuccess(Book data) {
                book.set(data);
                V().onLoadBook(data);
            }
        }.run();
    }
}
