package com.kanci.view.home;

import android.arch.lifecycle.Observer;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kanci.BR;
import com.kanci.R;
import com.kanci.data.model.bean.Book;
import com.kanci.databinding.ActivityHomeBinding;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import me.goldze.mvvmhabit.base.BaseActivity;

public class HomeActivity extends BaseActivity<ActivityHomeBinding, HomeViewModel> {

    @Override
    public void onResume() {
        super.onResume();
        viewModel.doLoadTask();
    }
    @Override
    public int initContentView(Bundle bundle) {
        QMUIStatusBarHelper.translucent(this);
        return R.layout.activity_home;
    }

    @Override
    public int initVariableId() {
        return BR.vm;
    }

    @Override
    public void initData() {
        viewModel.doLoadTask();
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.finishLoadTask.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                viewModel.doLoadBook(viewModel.task.get().bookId);
            }
        });
    }
}
