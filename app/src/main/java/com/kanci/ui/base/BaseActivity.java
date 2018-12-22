package com.kanci.ui.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.kanci.data.AppDataHelper;

public abstract class BaseActivity extends AppCompatActivity implements BaseViewModel.BaseView {
    private BaseViewModel viewModel;
    private ViewDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = createViewModel();
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setVariable(getBindingId(), viewModel);
        binding.executePendingBindings();
    }

    @Override
    public AppDataHelper createDH() {
        return AppDataHelper.instance(this);
    }

    public abstract int getLayoutId();

    public abstract int getBindingId();

    public abstract BaseViewModel createViewModel();

    public ViewDataBinding getBinding() {
        return binding;
    }

    public BaseViewModel VM() {
        return viewModel;
    }


    @Override
    public void handleError(Throwable e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }
}
