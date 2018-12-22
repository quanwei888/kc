package com.kanci.ui.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<Item, VM extends BaseItemViewModel> extends RecyclerView.Adapter<BaseAdapter<Item,VM>.ViewHolder> {
    BaseActivity activity;
    List<Item> data = new ArrayList<>();

    public BaseAdapter(BaseActivity activity) {
        this.activity = activity;
    }

    public void addAll(List<Item> items) {
        data.addAll(items);
        notifyDataSetChanged();
    }

    public void add(Item item) {
        data.add(item);
        notifyDataSetChanged();
    }

    public void remove(Item item) {
        data.remove(item);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayoutId(), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        Item item = data.get(pos);
        BaseItemViewModel<Item> vm = createVM(activity);
        vm.item.set(item);
        if (pos == 0) {
            vm.isFirst.set(true);
        }
        if (pos == data.size() - 1) {
            vm.isLast.set(true);
        }
        holder.binding.setVariable(getBindingId(), vm);
    }

    public abstract BaseItemViewModel createVM(BaseActivity activity);

    public abstract int getBindingId();

    public abstract int getLayoutId();

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
