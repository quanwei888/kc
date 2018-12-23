package com.kanci.ui.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.kanci.BR;
import com.kanci.data.AppDataHelper;

import java.util.List;

/**
 * Created by qw on 18-12-23.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder implements BaseViewModel.BaseView {
    private ViewDataBinding binding;
    private BaseViewModel.BaseView rootView;
    private BaseAdapter<T> adapter;
    private BaseViewModel viewModel;

    public BaseViewHolder(ViewDataBinding binding, BaseViewModel.BaseView rootView, BaseAdapter<T> adapter) {
        super(binding.getRoot());
        this.binding = binding;
        this.rootView = rootView;
        this.adapter = adapter;
        viewModel = createViewModel();
        binding.setVariable(getBindingId(), viewModel);
    }

    @Override
    public AppDataHelper createDH() {
        return rootView.createDH();
    }

    @Override
    public void handleError(Throwable e) {
        rootView.handleError(e);
    }

    public BaseAdapter<T> getAdapter() {
        return adapter;
    }

    public ViewDataBinding getBinding() {
        return binding;
    }

    public int getBindingId() {
        return BR.vm;
    }

    public BaseViewModel VM() {
        return viewModel;
    }

    public abstract void onBind(List<T> data, int pos);

    public abstract BaseViewModel createViewModel();

    public BaseViewModel.BaseView RV() {
        return rootView;
    }
}
