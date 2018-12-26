package com.kanci.ui.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<I, VH extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder> {
    protected List<I> data = new ArrayList<>();

    public void addAll(List<I> items) {
        data.addAll(items);
        notifyDataSetChanged();
    }

    public void add(I item) {
        data.add(item);
        notifyDataSetChanged();
    }

    public void remove(I item) {
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
        return createViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int pos) {
        holder.onBind(data, pos);
    }

    public abstract VH createViewHolder(ViewDataBinding binding);

    public abstract int getLayoutId();
}
