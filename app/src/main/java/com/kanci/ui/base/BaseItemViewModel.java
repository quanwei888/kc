package com.kanci.ui.base;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

public class BaseItemViewModel<Item> extends BaseViewModel {
    public ObservableBoolean isFirst;
    public ObservableBoolean isLast;
    public ObservableField<Item> item = new ObservableField<>();

    public BaseItemViewModel(BaseActivity activity) {
        super(activity);
    }
}
