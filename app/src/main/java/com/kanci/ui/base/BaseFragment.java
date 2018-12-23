package com.kanci.ui.base;

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

public abstract class BaseFragment extends Fragment implements BaseViewModel.BaseView {
    private BaseViewModel viewModel;
    private ViewDataBinding binding;

    @Override
    public AppDataHelper createDH() {
        return AppDataHelper.instance(getActivity());
    }


    public abstract int getLayoutId();

    public int getBindingId() {
        return BR.vm;
    }

    public abstract BaseViewModel createViewModel();

    public ViewDataBinding getBinding() {
        return binding;
    }

    public BaseViewModel VM() {
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
}
