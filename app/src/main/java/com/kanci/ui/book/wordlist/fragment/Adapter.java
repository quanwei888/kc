package com.kanci.ui.book.wordlist.fragment;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;

import com.kanci.R;
import com.kanci.data.model.db.Word;
import com.kanci.databinding.ItemWordBinding;
import com.kanci.ui.base.BaseAdapter;
import com.kanci.ui.base.BaseViewHolder;
import com.kanci.ui.base.ItemViewModel;

import java.util.List;

public class Adapter extends BaseAdapter<Word, Adapter.VH> {
    private OnEventListener listener;

    public OnEventListener getListener() {
        return listener;
    }

    public void setListener(OnEventListener listener) {
        this.listener = listener;
    }


    public interface OnEventListener {
        void onItemClick(Word item, int pos);
    }

    @Override
    public VH createViewHolder(ViewDataBinding binding) {
        return new VH((ItemWordBinding) binding);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_word;
    }


    public class VH extends BaseViewHolder<Word, ItemWordBinding, Adapter.VM> {
        public VH(ItemWordBinding binding) {
            super(binding);
        }

        @Override
        public void onBind(List<Word> data, int pos) {
            Word word = (Word) data.get(pos);
            VM().word.set(word);
            VM().isFirst.set(false);
            VM().isLast.set(false);
            if (pos == 0) {
                VM().isFirst.set(true);
            }
            if (pos == data.size() - 1) {
                VM().isLast.set(true);
            }

            V().doneView.setTag(word);
        }
    }

    public static class VM extends ItemViewModel {
        public ObservableField<Word> word = new ObservableField<>();
        public ObservableBoolean isFirst = new ObservableBoolean(false);
        public ObservableBoolean isLast = new ObservableBoolean(false);
    }
}
