package com.kanci.ui.selectbook;

import com.kanci.ui.base.BaseActivity;
import com.kanci.ui.base.BaseViewModel;
import com.kanci.ui.main.MainActivity;
import com.kanci.utils.AppSession;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class SelectBookViewModel extends BaseViewModel {
    protected SelectBookActivity view;
    public SelectBookAdapter adapter = new SelectBookAdapter();

    @Override
    public SelectBookActivity getView() {
        return view;
    }

    @Override
    public void setView(BaseActivity view) {
        this.view = (SelectBookActivity) view;
    }

    /**
     * 加载单词书列表
     */
    public void loadBookList() {
        getDataManager().doGetBookList(AppSession.uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {
                    adapter.addAll(res.data);
                });
    }

}
