package com.kanci.view.home;

import android.os.Bundle;

import com.kanci.BR;
import com.kanci.R;
import com.kanci.databinding.ActivityHomeBinding;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import me.goldze.mvvmhabit.base.BaseActivity;

public class HomeActivity extends BaseActivity<ActivityHomeBinding, HomeViewModel> {

    @Override
    public void onResume() {
        super.onResume();
        //viewModel.doLoadData();
    }

    @Override
    public int initContentView(Bundle bundle) {
        QMUIStatusBarHelper.translucent(this);
        return R.layout.activity_home1;
    }

    @Override
    public int initVariableId() {
        return BR.vm;
    }

    @Override
    public void initData() {
        viewModel.doLoadData();
    }

    @Override
    public void initViewObservable() {
    }
}
