package com.kanci.ui.book.add;

import android.databinding.ObservableField;

import com.kanci.data.model.api.ApiException;
import com.kanci.data.model.bean.Book;
import com.kanci.ui.base.BaseActivity;
import com.kanci.ui.base.BaseViewModel;

public class AddViewModel extends BaseViewModel {
    public ObservableField<Book> book = new ObservableField<>();

    public AddViewModel(BaseView view) {
        super(view);
    }

    @Override
    public AddActivity V() {
        return (AddActivity) super.V();
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

    private int getRemainDaysByPlan(Book book, int plan) {
        return book.getRemainWord() / plan + Math.min(book.getRemainWord() % plan, 1);
    }

    public void doAddBook(int plan) {
        new Post() {

            @Override
            public void doPost() throws ApiException {
                DH().addBook(book.get().bookId, plan);
            }

            @Override
            public void onSuccess() {
                V().onAddBook();
            }
        }.run();
    }

    public void doCreateTask() {
        new Post() {

            @Override
            public void doPost() throws ApiException {
                DH().createTask(book.get().bookId);
            }

            @Override
            public void onSuccess() {
                V().onCreateTask();
            }
        }.run();
    }
}
