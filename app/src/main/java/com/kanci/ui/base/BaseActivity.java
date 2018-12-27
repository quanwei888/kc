package com.kanci.ui.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import android.arch.lifecycle.ViewModelProviders;

import com.kanci.BR;
import com.kanci.data.AppDataHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity implements BaseViewModel.BaseView {
    private VM viewModel;
    private V binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.translucent(this);
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

    public int getBindingId() {
        return BR.vm;
    }

    public VM createViewModel() {
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) ViewModelProviders.of(this).get(modelClass);
            viewModel.init(this);
        }
        return viewModel;
    }

    public V getBinding() {
        return binding;
    }

    public VM VM() {
        return viewModel;
    }

    @Override
    protected void onDestroy() {
        if (VM().disposable != null && !VM().disposable.isDisposed()) {
            VM().disposable.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void handleError(Throwable e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }
}
