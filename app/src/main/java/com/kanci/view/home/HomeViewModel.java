package com.kanci.view.home;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.kanci.data.model.api.ApiException;
import com.kanci.data.model.bean.Book;
import com.kanci.data.model.bean.Task;
import com.kanci.ui.book.list.ListActivity;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class HomeViewModel extends BaseViewModel {
    public HomeViewModel(@NonNull Application application) {
        super(application);
    }


    public ObservableField<Task> task = new ObservableField<>();
    public ObservableField<Book> book = new ObservableField<>();

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //加载Task完成
        public ObservableBoolean finishLoadTask = new ObservableBoolean(false);
    }

    public BindingCommand onStartCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(BookListActivity.class);
        }
    });

    public BindingCommand onModPlanCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(BookListActivity.class);
        }
    });

    public BindingCommand onBookListCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(BookListActivity.class);
        }
    });

    public void doLoadTask() {
        new Query<Task>() {
            @Override
            public Task doQuery() throws ApiException {
                return DH().getTask();
            }

            @Override
            public void onSuccess(Task data) {
                task.set(data);
                uc.finishLoadTask.set(!uc.finishLoadTask.get());
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
            }
        }.run();
    }
}
