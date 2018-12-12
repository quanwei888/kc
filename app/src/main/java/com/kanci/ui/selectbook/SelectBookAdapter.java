package com.kanci.ui.selectbook;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kanci.BR;
import com.kanci.data.model.db.Book;
import com.kanci.databinding.ItemSelectBookBinding;
import com.kanci.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class SelectBookAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public List<Book> data = new ArrayList<>();

    public void addAll(List data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Inject
    public SelectBookAdapter() {
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int pos) {
        holder.onBind(pos);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemSelectBookBinding binding = ItemSelectBookBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    public class ItemViewModel {
        public Book book;

    }

    public class ViewHolder extends BaseViewHolder {
        protected ItemSelectBookBinding binding;
        protected ItemViewModel viewModel;

        public ViewHolder(ItemSelectBookBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(int pos) {
            Book book = data.get(pos);
            viewModel = new ItemViewModel();
            viewModel.book = book;
            binding.setVariable(BR.vm, viewModel);
            binding.executePendingBindings();
        }
    }
}
