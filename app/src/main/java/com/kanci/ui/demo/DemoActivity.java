package com.kanci.ui.demo;

import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.kanci.BR;
import com.kanci.R;
import com.kanci.databinding.ActivityDemoBinding;
import com.kanci.ui.base.BaseActivity;

public class DemoActivity extends BaseActivity<ActivityDemoBinding, DemoViewModel> {
    protected ActivityDemoBinding binding;
    protected DemoViewModel vm;

    @Override
    public DemoViewModel getViewModel() {
        return vm = (vm != null ? vm : new DemoViewModel());
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_demo;
    }

    @Override
    public ViewDataBinding getViewBinding() {
        return binding;
    }

    @Override
    public void setViewBinding(ViewDataBinding binding) {
        this.binding = (ActivityDemoBinding) binding;
    }

    @Override
    protected void setup() {
        getViewModel().updateUser();
    }
}
