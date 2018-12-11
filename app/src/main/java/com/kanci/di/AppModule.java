package com.kanci.di;

import android.content.Context;

import dagger.Module;

@Module
public class AppModule {
    Context context;

    public AppModule(Context context) {
        this.context = context;
    }
}
