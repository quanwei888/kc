package com.kanci.ui.base;

import android.content.Context;

import com.kanci.data.AppDataManager;

import javax.inject.Inject;

/**
 * Created by qw on 18-12-12.
 */

public abstract class BaseViewModel {
    public interface View {
        void handleError(Throwable e);

        Context getContext();
    }

    @Inject
    public AppDataManager dataManager;

    public AppDataManager getDataManager() {
        return dataManager;
    }
}
