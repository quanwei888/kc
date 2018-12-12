package com.kanci.di;

import android.content.Context;

import com.kanci.data.AppDataManager;
import com.kanci.data.remote.ApiHelper;
import com.kanci.ui.book.addbook.AddBookViewModel;
import com.kanci.ui.card.CardViewModel;
import com.kanci.ui.main.MainActivity;
import com.kanci.ui.main.MainViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    Context context;

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
    public MainViewModel.View mainView() {
        return (MainViewModel.View) context;
    }

    @Provides
    public CardViewModel.View cardView() {
        return (CardViewModel.View) context;
    }

    @Provides
    public AddBookViewModel.View addBookView() {
        return (AddBookViewModel.View) context;
    }


}
