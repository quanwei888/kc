package com.kanci.ui.main;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.kanci.BR;
import com.kanci.R;
import com.kanci.databinding.ActivityMainBinding;
import com.kanci.di.AppModule;
import com.kanci.di.DaggerAppComponent;
import com.kanci.ui.BaseActivity;
import com.kanci.ui.selectbook.SelectBookActivity;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainViewModel.View {
    @Inject
    public MainViewModel vm;
    public ActivityMainBinding binding;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DaggerAppComponent.builder().appModule(new AppModule(this)).build().inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setVariable(BR.vm, vm);
        binding.executePendingBindings();
        setup();
    }

    protected void setup() {
        vm.loadData();
    }
}
