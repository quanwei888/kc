package com.kanci.view.task;

import android.databinding.Observable;
import android.os.Bundle;
import android.os.Handler;

import com.kanci.BR;
import com.kanci.R;
import com.kanci.databinding.ActivityTaskBinding;
import com.kanci.view.home.HomeActivity;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * Created by qw on 18-12-29.
 */

public class TaskActivity extends BaseActivity<ActivityTaskBinding, TaskViewModel> {
    public int bookId;

    @Override
    public int initContentView(Bundle bundle) {
        QMUIStatusBarHelper.translucent(this);
        return R.layout.activity_task1;
    }

    @Override
    public int initVariableId() {
        return BR.vm;
    }

    @Override
    public void initParam() {
        bookId = getIntent().getIntExtra("bookId", 0);
    }

    @Override
    public void initData() {
        viewModel.doLoadTaskWordList(bookId);
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.finishLoadTaskWord.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                viewModel.doNextWord();
                viewModel.hide.set(true);
            }
        });
        viewModel.uc.swichNextWord.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                viewModel.hide.set(false);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        viewModel.hide.set(true);
                        viewModel.doNextWord();
                    }
                }, 1000);
            }
        });
        viewModel.uc.finishTask.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                startActivity(HomeActivity.class);
            }
        });

    }
}
