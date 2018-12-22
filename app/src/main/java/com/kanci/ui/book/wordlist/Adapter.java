package com.kanci.ui.book.wordlist;

import com.kanci.data.model.db.Word;
import com.kanci.ui.base.BaseAdapter;
import com.kanci.ui.base.BaseActivity;
import com.kanci.ui.base.BaseItemViewModel;

public class Adapter<Item, VM> extends BaseAdapter {
    public Adapter(BaseActivity activity) {
        super(activity);
    }

    @Override
    public BaseItemViewModel createVM(BaseActivity activity) {
        return new ViewModel(activity);
    }

    @Override
    public int getBindingId() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    public class ViewModel extends BaseItemViewModel<Word> {

        public ViewModel(BaseActivity activity) {
            super(activity);
        }
    }

}
