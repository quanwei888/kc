package com.kanci.ui.base;

import android.arch.lifecycle.ViewModel;

import com.kanci.data.AppDataHelper;
import com.kanci.data.model.api.ApiException;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ItemViewModel<VM extends BaseViewModel> {
    public VM viewModel;

    public ItemViewModel(VM viewModel) {
        this.viewModel = viewModel;
    }
}
