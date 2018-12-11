package com.kanci.ui;

import com.kanci.data.AppDataManager;

import javax.inject.Inject;

/**
 * Created by qw on 18-12-12.
 */

public abstract class BaseViewModel {
    @Inject
    public AppDataManager dataManager;

    public AppDataManager getDataManager() {
        return dataManager;
    }
}
