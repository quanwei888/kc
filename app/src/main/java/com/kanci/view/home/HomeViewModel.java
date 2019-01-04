package com.kanci.view.home;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.kanci.data.model.api.ApiException;
import com.kanci.data.model.bean.Book;
import com.kanci.data.model.bean.Task;
import com.kanci.view.base.BaseViewModel;
import com.kanci.view.book.list.BookListActivity;
import com.kanci.view.task.TaskActivity;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class HomeViewModel extends BaseViewModel {
    public HomeViewModel(@NonNull Application application) {
        super(application);
    }


    public ObservableField<Task> task = new ObservableField<>();
    public ObservableField<Book> book = new ObservableField<>();
    public ObservableInt taskCount = new ObservableInt(0);
    public ObservableInt taskDone = new ObservableInt(0);

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //加载Task完成
        public ObservableBoolean finishLoadTask = new ObservableBoolean(false);
    }

    public BindingCommand onStartCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (task.get() == null) {
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putInt("bookId", task.get().bookId);
            startActivity(TaskActivity.class, bundle);
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

    public void doLoadData() {
        new Post() {
            @Override
            public void doPost() throws ApiException {
                Task curTask = DH().getTask();
                task.set(curTask);
                book.set(DH().getBook(curTask.bookId));
            }
        }.run();
    }
}
