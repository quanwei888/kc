package com.kanci.ui.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<T>> {
    protected BaseViewModel.BaseView view;
    protected List<T> data = new ArrayList<>();

    public BaseAdapter(BaseViewModel.BaseView view) {
        this.view = view;
    }

    public void addAll(List<T> items) {
        data.addAll(items);
        notifyDataSetChanged();
    }

    public void add(T item) {
        data.add(item);
        notifyDataSetChanged();
    }

    public void remove(T item) {
        int pos = data.indexOf(item);
        remove(pos);
    }

    public void remove(int pos) {
        if (pos == -1) {
            return;
        }
        data.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, data.size() - pos);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayoutId(), parent, false);
        return createViewHolder(binding, view);
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int pos) {
        holder.onBind(data, pos);
    }

    public abstract BaseViewHolder createViewHolder(ViewDataBinding binding, BaseViewModel.BaseView view);

    public abstract int getLayoutId();
}
