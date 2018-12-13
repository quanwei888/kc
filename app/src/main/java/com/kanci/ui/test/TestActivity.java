package com.kanci.ui.test;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.kanci.R;
import com.kanci.databinding.ActivityTestBinding;
import com.kanci.di.AppModule;
import com.kanci.di.DaggerAppComponent;
import com.kanci.ui.base.BaseActivity;

public class TestActivity extends BaseActivity {
    public TestViewModel vm;
    protected ActivityTestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        DaggerAppComponent.builder().appModule(new AppModule(this)).build().inject(this);
    }
}
