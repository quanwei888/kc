/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.kanci.data;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kanci.data.local.db.AppDatabase;
import com.kanci.data.local.db.AppDbHelper;
import com.kanci.data.local.db.DbHelper;
import com.kanci.data.local.prefs.AppPreferencesHelper;
import com.kanci.data.local.prefs.PreferencesHelper;
import com.kanci.data.model.api.BookListResponse;
import com.kanci.data.model.api.BookResponse;
import com.kanci.data.model.api.WordListResponse;
import com.kanci.data.model.db.Book;
import com.kanci.data.model.db.BookWord;
import com.kanci.data.remote.ApiHelper;
import com.kanci.ui.base.BaseViewModel;
import com.kanci.utils.AppConstants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppDataManager {

    private final ApiHelper apiHelper;

    private final Context context;

    private final DbHelper dbHelper;

    private final Gson gson;

    private final PreferencesHelper preferencesHelper;

    private static AppDataManager instance;

    public static AppDataManager instance(Context context) {
        return instance = instance == null ? new AppDataManager(context) : instance;
    }

    private AppDataManager(Context context) {
        this.context = context;
        this.dbHelper = new AppDbHelper(Room.databaseBuilder(context, AppDatabase.class, AppConstants.DB_NAME).fallbackToDestructiveMigration().build());

        this.preferencesHelper = new AppPreferencesHelper(context, AppConstants.PREF_NAME);
        this.apiHelper = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(ApiHelper.class);
        this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    public Single<BookResponse> doGetCurrentBook(int uid) {
        return apiHelper.doGetCurrentBook(uid);
    }

    public Single<BookListResponse> doGetBookList(int uid) {
        return apiHelper.doGetBookList(uid);
    }

    public Single<WordListResponse> doGetBookWordList(int uid, int page, int size) {
        return apiHelper.doGetBookWordList(uid, page, size);
    }
}
