package com.kanci.ui.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.kanci.BR;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by qw on 18-12-23.
 */

public abstract class BaseViewHolder<I, V extends ViewDataBinding, VM extends ItemViewModel> extends RecyclerView.ViewHolder {
    private V binding;
    private VM viewModel;

    public BaseViewHolder(V binding) {
        super(binding.getRoot());
        this.binding = binding;
        viewModel = createViewModel();
        binding.setVariable(getBindingId(), viewModel);
    }

    public VM createViewModel() {
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[2];
            } else {
                modelClass = ItemViewModel.class;
            }
            try {
                viewModel = (VM) modelClass.newInstance();
            } catch (Exception e) {
                viewModel = null;
            }
        }
        return viewModel;
    }

    public int getBindingId() {
        return BR.vm;
    }

    public V V() {
        return binding;
    }

    public VM VM() {
        return viewModel;
    }

    public abstract void onBind(List<I> data, int pos);
}
