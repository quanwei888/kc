package com.kanci.ui.home;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;

import com.kanci.data.model.api.ApiException;
import com.kanci.data.model.bean.Book;
import com.kanci.data.model.bean.Task;
import com.kanci.ui.base.BaseViewModel;
import com.kanci.ui.base.ICallback;


public class HomeViewModel extends BaseViewModel {
    public ObservableField<Task> task = new ObservableField<>();
    public ObservableField<Book> book = new ObservableField<>();


    @Override
    public HomeActivity V() {
        return (HomeActivity) super.V();
    }

    public void doLoadTask(ICallback<Task> callback) {
        new Query<Task>(callback) {
            @Override
            public Task doQuery() throws ApiException {
                return DH().getTask();
            }
        }.run();
    }

    public void doLoadBook(int bookId, ICallback<Book> callback) {
        new Query<Book>(callback) {

            @Override
            public Book doQuery() throws ApiException {
                return DH().getBook(bookId);
            }
        }.run();
    }
}
