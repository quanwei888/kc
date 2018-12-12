package com.kanci.di;

import com.kanci.ui.card.CardActivity;
import com.kanci.ui.main.MainActivity;
import com.kanci.ui.selectbook.SelectBookActivity;
import com.kanci.ui.test.TestActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = AppModule.class
)
public interface AppComponent {
    void inject(TestActivity activity);

    void inject(MainActivity activity);

    void inject(CardActivity activity);
    void inject(SelectBookActivity activity);
}
