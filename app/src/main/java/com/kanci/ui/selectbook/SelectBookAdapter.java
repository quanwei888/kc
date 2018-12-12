package com.kanci.ui.selectbook;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kanci.BR;
import com.kanci.R;
import com.kanci.data.model.bean.Book;
import com.kanci.databinding.ItemSelectBookBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class SelectBookAdapter extends BaseAdapter {
    public List<Book> data = new ArrayList<>();


    public SelectBookAdapter() {
    }

    public void addAll(List data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Book book = data.get(i);
        ItemViewModel viewModel = new ItemViewModel();
        viewModel.book = book;
        ItemSelectBookBinding binding;

        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_select_book, null);
            binding = DataBindingUtil.bind(view);
            view.setTag(view);
            binding.setVariable(BR.vm, viewModel);
        } else {
            binding = (ItemSelectBookBinding) view.getTag();
        }
        binding.setVariable(BR.vm, viewModel);
        return binding.getRoot();
    }

    public class ItemViewModel {
        public Book book;
    }
}
