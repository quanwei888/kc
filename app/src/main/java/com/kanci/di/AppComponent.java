package com.kanci.di;

import android.content.Context;

import com.kanci.ui.test.TestActivity;

import dagger.Component;

@Component(
        modules = AppModule.class
)
public interface AppComponent {
    void inject(TestActivity activity);
}
