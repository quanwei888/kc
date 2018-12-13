package com.kanci.di;

import android.content.Context;

import com.kanci.data.AppDataManager;
import com.kanci.ui.book.add.BookAddViewModel;
import com.kanci.ui.book.plan.BookPlanViewModel;
import com.kanci.ui.card.CardViewModel;
import com.kanci.ui.main.MainViewModel;
import com.kanci.ui.word.main.WordMainViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    Context context;

    public AppModule() {
    }

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context context() {
        return context;
    }

    @Provides
    public AppDataManager appDataManager() {
        return AppDataManager.instance(context);
    }

    @Provides
    public MainViewModel.View mainViewModel() {
        return (MainViewModel.View) context;
    }

    @Provides
    public CardViewModel.View cardViewModel() {
        return (CardViewModel.View) context;
    }

    @Provides
    public BookAddViewModel.View wookAddViewModel() {
        return (BookAddViewModel.View) context;
    }

    @Provides
    public BookPlanViewModel.View wookPlanViewModel() {
        return (BookPlanViewModel.View) context;
    }

    @Provides
    public WordMainViewModel.View wordMainViewModel() {
        return (WordMainViewModel.View) context;
    }

}
