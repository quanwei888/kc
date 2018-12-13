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
import android.arch.persistence.room.EmptyResultSetException;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.kanci.data.local.db.AppDatabase;
import com.kanci.data.local.prefs.AppPreferencesHelper;
import com.kanci.data.model.api.ApiException;
import com.kanci.data.model.api.ApiResponse;
import com.kanci.data.model.bean.Book;
import com.kanci.data.model.db.BookState;
import com.kanci.data.model.db.BookWord;
import com.kanci.data.model.db.BookWordDef;
import com.kanci.data.model.db.TaskWord;
import com.kanci.data.remote.ApiHelper;
import com.kanci.utils.AppConstants;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        Builder okHttpClient = new OkHttpClient().newBuilder()
                .cookieJar(cookieJar)
                .connectTimeout(60 * 5, TimeUnit.SECONDS)
                .readTimeout(60 * 5, TimeUnit.SECONDS)
                .writeTimeout(60 * 5, TimeUnit.SECONDS);
        //okHttpClient.interceptors().add(new AddCookiesInterceptor());
        //okHttpClient.interceptors().add(new ReceivedCookiesInterceptor());

        this.apiHelper = new Retrofit.Builder()
                .baseUrl("http://lucky888.vicp.io:10000/index.php/")
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
    public BookState getBookState() throws ApiException {
        //local
        BookState entity = null;
        try {
            entity = db.bookStateDao().findOne().blockingGet();
        } catch (EmptyResultSetException e) {

        }
        if (entity == null) {
            //remote
            ApiResponse.EntityResponse<BookState> response = apiHelper.doGetBookState().blockingGet();
            if (!response.isSuccess()) {
                throw new ApiException(response.status, response.message);
            }
            entity = response.data;
            if (entity != null) {
                db.bookStateDao().insert(entity);
            }
        }
        return entity;
    }

    /**
     * 当前单词书状态
     *
     * @return
     */
    public void cacheBookState() throws ApiException {
        ApiResponse.EntityResponse<BookState> response = apiHelper.doGetBookState().blockingGet();
        if (!response.isSuccess()) {
            throw new ApiException(response.status, response.message);
        }
        BookState entity = response.data;
        if (entity != null) {
            db.bookStateDao().insert(entity);
        }

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
     * 获取单词书单词列表
     *
     * @param bookId
     * @return
     */
    public List<TaskWord> getTaskWordList(int bookId) {
        List<TaskWord> entityList = db.taskWordDao().findAll().blockingGet();
        return entityList;
    }

    /**
     * 缓存单词书任务
     *
     * @param bookId
     * @return
     */
    public void cacheTaskWordList(int bookId) throws ApiException {
        ApiResponse.EntityListResponse<TaskWord> response = apiHelper.doGetTaskWordList(bookId).blockingGet();
        if (!response.isSuccess()) {
            throw new ApiException(response.status, response.message);
        }
        List<TaskWord> entityList = response.data;
        if (entityList.size() == 0) {
            return;
        }
        for (TaskWord word : entityList) {
            db.taskWordDao().insert(word);
        }
    }

    /**
     * 加载BookWord到本地
     *
     * @param bookId
     */
    public void cacheBookWordList(int bookId) throws ApiException {
        ApiResponse.EntityListResponse<BookWord> response = apiHelper.doGetBookWordList(bookId).blockingGet();
        if (!response.isSuccess()) {
            throw new ApiException(response.status, response.message);
        }
        List<BookWord> entityList = response.data;
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
    public BookWordDef getBookWordDef(int bookId, String word) throws ApiException {
        //local
        BookWordDef entity = null;
        try {
            entity = db.bookWordDefDao().findByPk(bookId, word).blockingGet();
        } catch (EmptyResultSetException e) {

        }
        if (entity == null) {
            //remote
            ApiResponse.EntityResponse<BookWordDef> response = apiHelper.doGetBookWordDef(bookId, word).blockingGet();
            if (!response.isSuccess()) {
                throw new ApiException(response.status, response.message);
            }
            entity = response.data;
            db.bookWordDefDao().insert(entity);
        }
        return entity;
    }

    public List<BookWordDef> getBookWordDefByWords(int bookId, List<String> words) throws ApiException {
        //local
        List<BookWordDef> entityList = db.bookWordDefDao().findAllByWords(bookId, words).blockingGet();
        Set<String> wordSet = new HashSet<>();
        for (BookWordDef wordDef : entityList) {
            wordSet.add(wordDef.word);
        }

        List<String> lackWords = new ArrayList<>();
        for (String word : words) {
            if (!wordSet.contains(word)) {
                lackWords.add(word);
            }
        }

        ApiResponse.EntityListResponse<BookWordDef> response = apiHelper.doGetBookWordDefListByWords(bookId, lackWords).blockingGet();
        if (!response.isSuccess()) {
            throw new ApiException(response.status, response.message);
        }

        List<BookWordDef> lackEntityList = response.data;
        for (BookWordDef word : lackEntityList) {
            db.bookWordDefDao().insert(word);
            entityList.add(word);
        }
        return entityList;
    }

    /**
     * 加载BookWordDef到本地
     *
     * @param bookId
     */
    public void cacheBookWordDefList(int bookId) throws ApiException {
        ApiResponse.EntityListResponse<BookWordDef> response = apiHelper.doGetBookWordDefList(bookId).blockingGet();
        if (!response.isSuccess()) {
            throw new ApiException(response.status, response.message);
        }
        List<BookWordDef> entityList = response.data;
        for (BookWordDef word : entityList) {
            db.bookWordDefDao().insert(word);
        }
    }

    /**
     * 获取所有Book
     *
     * @return
     */
    public List<Book> getBookList() throws ApiException {
        ApiResponse.EntityListResponse<Book> response = apiHelper.doGetBookList().blockingGet();
        if (!response.isSuccess()) {
            throw new ApiException(response.status, response.message);
        }
        List<Book> entityList = response.data;
        return entityList;
    }

    /**
     * 添加Book
     *
     * @param bookId
     * @param plan
     * @throws ApiException
     */
    public void addBook(int bookId, int plan) throws ApiException {
        ApiResponse.CommonResponse response = apiHelper.doAddBook(bookId, plan).blockingGet();
        if (!response.isSuccess()) {
            throw new ApiException(response.status, response.message);
        }
    }

    /**
     * 创建新任务
     *
     * @param bookId
     * @throws ApiException
     */
    public void createTask(int bookId) throws ApiException {
        ApiResponse.CommonResponse response = apiHelper.doCreateTask(bookId).blockingGet();
        if (!response.isSuccess()) {
            throw new ApiException(response.status, response.message);
        }
    }
}
