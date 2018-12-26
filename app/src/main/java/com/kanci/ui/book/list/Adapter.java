package com.kanci.ui.book.list;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.kanci.R;
import com.kanci.data.model.bean.Book;
import com.kanci.databinding.ItemBookBinding;
import com.kanci.ui.base.BaseAdapter;
import com.kanci.ui.base.BaseViewHolder;
import com.kanci.ui.base.ItemViewModel;

import java.util.List;

public class Adapter extends BaseAdapter<Book, Adapter.VH> {
    private OnEventListener listener;

    public OnEventListener getListener() {
        return listener;
    }

    public void setListener(OnEventListener listener) {
        this.listener = listener;
    }


    public interface OnEventListener {
        void onItemClick(Book item, int pos);
    }

    @Override
    public VH createViewHolder(ViewDataBinding binding) {
        return new VH((ItemBookBinding) binding);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_book;
    }

    public class VH extends BaseViewHolder<Book, ItemBookBinding, Adapter.VM> {
        public VH(ItemBookBinding binding) {
            super(binding);
        }

        @Override
        public void onBind(List<Book> data, int pos) {
            Book book = data.get(pos);
            VM().book.set(book);
            VM().isFirst.set(false);
            VM().isLast.set(false);
            VM().isSecFirst.set(false);
            if (pos == 0) {
                VM().isFirst.set(true);
            }
            if (pos == data.size() - 1) {
                VM().isLast.set(true);
            }

            if (pos > 0) {
                Book lastBook = (Book) data.get(pos - 1);
                if (!lastBook.tag.equals(book.tag)) {
                    VM().isSecFirst.set(true);
                }
            } else {
                VM().isSecFirst.set(true);
            }
            V().getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(book, pos);
                    }
                }
            });
        }
    }

    public static class VM extends ItemViewModel {
        public ObservableField<Book> book = new ObservableField<>();
        public ObservableBoolean isFirst = new ObservableBoolean(false);
        public ObservableBoolean isLast = new ObservableBoolean(false);
        public ObservableBoolean isSecFirst = new ObservableBoolean(false);
    }

}
