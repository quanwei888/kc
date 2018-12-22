package com.kanci.ui.book.add;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.kanci.data.model.api.ApiException;
import com.kanci.data.model.bean.Book;
import com.kanci.ui.base.BaseActivity;
import com.kanci.ui.base.BaseViewModel;

public class AddViewModel extends BaseViewModel {
    public ObservableField<Book> book = new ObservableField<>();
    public ObservableInt plan = new ObservableInt(100);
    public ObservableInt remainDays = new ObservableInt(0);

    public AddViewModel(BaseActivity activity) {
        super(activity);
    }

    @Override
    public AddActivity V() {
        return (AddActivity) super.V();
    }

    public void doLoadBook(int bookId) {
        new Query<Book>() {

            @Override
            public Book doQuery() throws ApiException {
                return DH().getBookLocal(bookId);
            }

            @Override
            public void onSuccess(Book data) {
                book.set(data);
                initPlan(data, 100);
            }
        }.run();
    }

    public void doChangePlan(int count) {
        plan.set(count);
        remainDays.set(getRemainDaysByPlan(book.get(), plan.get()));
    }

    private void initPlan(Book book, int count) {
        if (book.isFavor) {
            plan.set(book.plan);
            remainDays.set(getRemainDaysByPlan(book, plan.get()));
        } else {
            plan.set(count);
            remainDays.set(getRemainDaysByPlan(book, plan.get()));
        }
    }

    private int getRemainDaysByPlan(Book book, int plan) {
        return book.getRemainWord() / plan + Math.min(book.getRemainWord() % plan, 1);
    }

    public void doAddBook() {
        new Post() {

            @Override
            public void doPost() throws ApiException {
                DH().addBook(book.get().id, plan.get());
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
                DH().createTask(book.get().id);
            }

            @Override
            public void onSuccess() {
                V().onCreateTask();
            }
        }.run();
    }
}
