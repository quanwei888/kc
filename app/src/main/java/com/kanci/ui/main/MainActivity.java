package com.kanci.ui.main;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kanci.BR;
import com.kanci.R;
import com.kanci.databinding.ActivityDemoBinding;
import com.kanci.databinding.ActivityMainBinding;
import com.kanci.ui.base.AuthActivity;
import com.kanci.ui.base.BaseActivity;
import com.kanci.ui.base.BaseViewModel;
import com.kanci.ui.demo.DemoViewModel;
import com.kanci.ui.selectbook.SelectBookActivity;

public class MainActivity extends AuthActivity<ActivityMainBinding, MainViewModel> {
    protected ActivityMainBinding binding;
    protected MainViewModel vm;

    @Override
    protected BaseViewModel getViewModel() {
        return vm = (vm != null ? vm : new MainViewModel());
    }

    @Override
    protected int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public ViewDataBinding getViewBinding() {
        return binding;
    }

    @Override
    public void setViewBinding(ViewDataBinding binding) {
        this.binding = (ActivityMainBinding) binding;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void setup() {
        vm.loadCurrentUser();
        vm.loadCurrentBook();
    }

    public void gotoSelectBookActivity() {
        startActivity(SelectBookActivity.newIntent(this));
    }
}
