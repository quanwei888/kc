package com.kanci.di;

import com.kanci.ui.book.add.BookAddActivity;
import com.kanci.ui.book.plan.BookPlanActivity;
import com.kanci.ui.card.CardActivity;
import com.kanci.ui.main.MainActivity;
import com.kanci.ui.test.TestActivity;
import com.kanci.ui.word.main.WordMainActivity;

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

    void inject(BookAddActivity activity);

    void inject(BookPlanActivity activity);

    void inject(WordMainActivity activity);
}
