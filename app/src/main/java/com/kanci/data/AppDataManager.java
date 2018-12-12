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

import android.arch.persistence.db.SimpleSQLiteQuery;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kanci.data.local.db.AppDatabase;
import com.kanci.data.local.prefs.AppPreferencesHelper;
import com.kanci.data.model.bean.Book;
import com.kanci.data.model.db.BookState;
import com.kanci.data.model.db.BookWord;
import com.kanci.data.model.db.BookWordDef;
import com.kanci.data.remote.ApiHelper;
import com.kanci.utils.AppConstants;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.OkHttpClient.*;

public class AppDataManager {

    private final ApiHelper apiHelper;

    private final AppPreferencesHelper preferencesHelper;
    private final AppDatabase db;

    private static AppDataManager instance;

    public static AppDataManager instance(Context context) {
        return instance = instance == null ? new AppDataManager(context) : instance;
    }

    private AppDataManager(Context context) {
        this.db = Room.databaseBuilder(context, AppDatabase.class, AppConstants.DB_NAME).fallbackToDestructiveMigration().build();
        this.preferencesHelper = new AppPreferencesHelper(context, AppConstants.PREF_NAME);

        Builder okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60 * 5, TimeUnit.SECONDS)
                .readTimeout(60 * 5, TimeUnit.SECONDS)
                .writeTimeout(60 * 5, TimeUnit.SECONDS);
        okHttpClient.interceptors().add(new AddCookiesInterceptor());
        okHttpClient.interceptors().add(new ReceivedCookiesInterceptor());

        this.apiHelper = new Retrofit.Builder()
                .baseUrl("http://lucky888.vicp.io:10000/")
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(ApiHelper.class);
    }

    class AddCookiesInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            HashSet<String> preferences = preferencesHelper.getCookie();
            for (String cookie : preferences) {
                builder.addHeader("Cookie", cookie);
                Log.v("OkHttp", "Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
            }
            return chain.proceed(builder.build());
        }
    }

    class ReceivedCookiesInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());

            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                HashSet<String> cookies = new HashSet<>();

                for (String header : originalResponse.headers("Set-Cookie")) {
                    cookies.add(header);
                }

                preferencesHelper.setCookie(cookies);
            }
            return originalResponse;
        }
    }

    /**
     * 当前单词书状态
     *
     * @return
     */
    public BookState getBookState() {
        //local
        BookState entity = db.bookStateDao().findOne().blockingGet();
        if (entity == null) {
            //remote
            entity = apiHelper.doGetBookState().blockingGet().data;
            if (entity != null) {
                db.bookStateDao().insert(entity);
            }
        }
        return entity;
    }

    /**
     * 获取单词书单词列表
     *
     * @param bookId
     * @return
     */
    public List<BookWord> getBookWordList(int bookId, int page, int size) {
        //local
        SimpleSQLiteQuery query = new SimpleSQLiteQuery("SELECT * FROM BookWord WHERE bookId = ?",
                new Object[]{bookId});
        List<BookWord> entityList = db.bookWordDao().queryAll(query).blockingGet();
        return entityList;
    }

    /**
     * 加载BookWord到本地
     *
     * @param bookId
     */
    public void cacheBookWordList(int bookId) {
        List<BookWord> entityList = apiHelper.doGetBookWordList(bookId).blockingGet().data;
        if (entityList.size() == 0) {
            return;
        }
        for (BookWord word : entityList) {
            db.bookWordDao().insert(word);
        }
    }

    /**
     * 获取单个BookWordDef
     *
     * @param bookId
     * @param word
     * @return
     */
    public BookWordDef getBookWordDef(int bookId, String word) {
        //local
        BookWordDef entity = db.bookWordDefDao().findByPk(bookId, word).blockingGet();
        if (entity == null) {
            //remote
            entity = apiHelper.doGetBookWordDef(bookId, word).blockingGet().data;
            if (entity != null) {
                db.bookWordDefDao().insert(entity);
            }
        }
        return entity;
    }

    /**
     * 加载BookWordDef到本地
     *
     * @param bookId
     */
    public void cacheBookWordDefList(int bookId) {
        List<BookWordDef> entityList = apiHelper.doGetBookWordDefList(bookId).blockingGet().data;
        if (entityList.size() == 0) {
            return;
        }
        for (BookWordDef word : entityList) {
            db.bookWordDefDao().insert(word);
        }
    }

    /**
     * 获取所有Book
     *
     * @return
     */
    public List<Book> getBookList() {
        List<Book> entityList = apiHelper.doGetBookList().blockingGet().data;
        return entityList;
    }
}
