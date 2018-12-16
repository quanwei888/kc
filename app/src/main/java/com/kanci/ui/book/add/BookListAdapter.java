package com.kanci.ui.book.add;


import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kanci.BR;
import com.kanci.R;
import com.kanci.data.model.bean.Book;
import com.kanci.databinding.ItemBookBinding;

import java.util.ArrayList;
import java.util.List;

public class BookListAdapter {
    /*
    public List<Book> data = new ArrayList<>();

    public OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void addAll(List data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void add(Book item) {
        this.data.add(item);
        notifyDataSetChanged();
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, null);
        ItemBookBinding binding = DataBindingUtil.bind(view);
        BookViewHolder vh = new BookViewHolder(binding);
        vh.itemView.setOnClickListener((v) -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v);
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(BookViewHolder vh, int pos) {
        vh.onBind(vh, pos);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        public ItemBookBinding binding;
        public BookItem item = new BookItem();

        public BookViewHolder(ItemBookBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(BookViewHolder vh, int pos) {
            item.book = data.get(pos);
            binding.setVariable(BR.vm, item);
            itemView.setTag(item.book);
        }
    }

    public static class BookItem {
        public Book book;
    }

    public static interface OnItemClickListener {
        void onItemClick(View view);
    }
    */
}
