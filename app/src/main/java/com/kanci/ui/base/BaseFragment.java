package com.kanci.ui.base;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kanci.BR;
import com.kanci.data.AppDataHelper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends Fragment implements BaseViewModel.BaseView {
    private VM viewModel;
    private V binding;

    @Override
    public AppDataHelper createDH() {
        return AppDataHelper.instance(getActivity());
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

    public abstract int getLayoutId();

    public int getBindingId() {
        return BR.vm;
    }

    public V getBinding() {
        return binding;
    }

    public VM VM() {
        return viewModel;
    }


    @Override
    public void handleError(Throwable e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = createViewModel();
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        binding.setVariable(getBindingId(), viewModel);
        binding.executePendingBindings();
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        if (VM().disposable != null && !VM().disposable.isDisposed()) {
            VM().disposable.dispose();
        }
        super.onDestroy();
    }
}
