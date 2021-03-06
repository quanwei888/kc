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
import android.content.Context;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.kanci.BuildConfig;
import com.kanci.data.local.db.AppDatabase;
import com.kanci.data.local.prefs.AppPref;
import com.kanci.data.model.api.ApiException;
import com.kanci.data.model.api.ApiResponse;
import com.kanci.data.model.bean.Book;
import com.kanci.data.model.bean.Task;
import com.kanci.data.model.db.Def;
import com.kanci.data.model.db.Word;
import com.kanci.data.remote.ApiHelper;
import com.kanci.utils.AppConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import me.goldze.mvvmhabit.http.interceptor.logging.Level;
import me.goldze.mvvmhabit.http.interceptor.logging.LoggingInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.OkHttpClient.Builder;

public class AppDataHelper {
    Map<String, Integer> data = new HashMap<>();
    final static String KEY_TASK__DONE = "KEY_TASK__DONE";
    final static String KEY_BOOK_DONE = "KEY_BOOK_DONE";
    final static String KEY_TASK_NEW_COUNT = "KEY_TASK_NEW_COUNT";
    final static String KEY_TASK_COUNT = "KEY_TASK_COUNT";

    private final ApiHelper apiHelper;

    private final AppPref pref;
    private final AppDatabase db;

    private static AppDataHelper instance;

    public static AppDataHelper instance(Context context) {
        return instance = instance == null ? new AppDataHelper(context) : instance;
    }

    private AppDataHelper(Context context) {
        this.db = AppDatabase.instance(context);
        this.pref = new AppPref(context, AppConstants.PREF_NAME);


        LoggingInterceptor mLoggingInterceptor = new LoggingInterceptor
                .Builder()//构建者模式
                .loggable(true) //是否开启日志打印
                .setLevel(Level.BODY) //打印的等级
                .log(Platform.INFO) // 打印类型
                .request("Request") // request的Tag
                .response("Response")// Response的Tag
                .addHeader("version", BuildConfig.VERSION_NAME)//打印版本
                .build();

        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        Builder okHttpClient = new OkHttpClient().newBuilder()
                .cookieJar(cookieJar)
                .addInterceptor(mLoggingInterceptor)
                .connectTimeout(60 * 5, TimeUnit.SECONDS)
                .readTimeout(60 * 5, TimeUnit.SECONDS)
                .writeTimeout(60 * 5, TimeUnit.SECONDS);

        this.apiHelper = new Retrofit.Builder()
                //.baseUrl("http://lucky888.vicp.io:10000/index.php/")
                .baseUrl("http://47.105.160.166:8000/")
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(ApiHelper.class);
    }

    private Object Query(ApiResponse.EntityResponse response) throws ApiException {
        if (!response.isSuccess()) {
            throw new ApiException(response.status, response.message);
        }
        return response.data;
    }

    private Object QueryList(ApiResponse.EntityListResponse response) throws ApiException {
        if (!response.isSuccess()) {
            throw new ApiException(response.status, response.message);
        }
        return response.data;
    }

    private void Post(ApiResponse.CommonResponse response) throws ApiException {
        if (!response.isSuccess()) {
            throw new ApiException(response.status, response.message);
        }
    }

    /*****************Cache***************/
    public int getBookDone(int bookId) throws ApiException {
        return db.wordDao().doneCount(bookId).blockingGet();
    }

    public int getTaskDone(int bookId) throws ApiException {
        List<String> ws = getTaskWordSetLocal(bookId);
        return db.wordDao().taskDoneCount(bookId, ws).blockingGet();
    }

    public int getTaskCount(int bookId) throws ApiException {
        List<String> ws = getTaskWordSetLocal(bookId);
        return db.wordDao().taskCount(bookId, ws).blockingGet();
    }

    public int getTaskNewCount(int bookId) throws ApiException {
        List<String> ws = getTaskWordSetLocal(bookId);
        return db.wordDao().taskNewCount(bookId, ws).blockingGet();
    }

    void expireWordCache() {
        data.clear();
    }

    /*************Task*************/

    public Task getTask() throws ApiException {
        Task task = getTaskLocal();
        if (task == null) {
            task = getTaskRemote();
        }

        //缓存BOOk Word List
        if (!isBookWordListCached(task.bookId)) {
            getBookWordListRemote(task.bookId);
        }

        //缓存Task Word List
        List<String> ws = getTaskWordSetLocal(task.bookId);
        if (ws.size() == 0) {
            ws = getTaskWordSetRemote(task.bookId);
        }

        task.doneCount = getTaskDone(task.bookId);
        task.newCount = getTaskNewCount(task.bookId);
        task.count = getTaskNewCount(task.bookId);
        pref.saveTask(task);

        return task;
    }

    public Task getTaskLocal() {
        Task task = this.pref.getTask();
        return task;
    }

    public Task getTaskRemote() throws ApiException {
        Task task = (Task) Query(apiHelper.doGetTask().blockingGet());
        pref.saveTask(task);
        return task;
    }

    public void createTask(int bookId) throws ApiException {
        Post(apiHelper.doCreateTask(bookId).blockingGet());
    }

    public List<Word> getTaskWordList(int bookId) throws ApiException {
        List<String> ws = getTaskWordSetLocal(bookId);
        if (ws.size() == 0) {
            ws = getTaskWordSetRemote(bookId);
        }

        if (!isBookWordListCached(bookId)) {
            getBookWordListRemote(bookId);
        }

        //SimpleSQLiteQuery sql = new SimpleSQLiteQuery("select * from Word where bookId=? and word in (?)", new Object[]{bookId, ws});

        List<Word> wordList = db.wordDao().findTaskAll(bookId, ws).blockingGet();
        return wordList;
    }

    public List<Word> getTaskWordListAddDef(int bookId) throws ApiException {
        List<String> ws = getTaskWordSetLocal(bookId);
        if (ws.size() == 0) {
            ws = getTaskWordSetRemote(bookId);
        }
        List<Word> wordList = getTaskWordList(bookId);

        //加载Def
        List<Def> defList = getDefByWords(bookId, new ArrayList<>(ws));
        Map<String, Word> wordMap = wordList.stream().collect(Collectors.toMap(x -> x.word, x -> x));
        Map<String, Def> defMap = defList.stream().collect(Collectors.toMap(x -> x.word, x -> x));
        defMap.forEach((k, v) -> wordMap.get(k).def = v);

        return wordList;
    }

    public List<String> getTaskWordSetLocal(int bookId) throws ApiException {
        List<String> ws = pref.getTaskWordList();
        return ws;
    }

    public List<String> getTaskWordSetRemote(int bookId) throws ApiException {
        List<String> ws = (List<String>) QueryList(apiHelper.doGetTaskWordList(bookId).blockingGet());
        pref.saveTaskWordList(ws);
        ws = pref.getTaskWordList();
        return ws;
    }

    /*************Book*************/
    public Book getBook(int bookId) throws ApiException {
        Book book = getBookLocal(bookId);
        if (book == null) {
            book = getBookRemote(bookId);
        }

        if (!isBookWordListCached(bookId)) {
            getBookWordListRemote(bookId);
        }

        book.doneCount = getBookDone(bookId);
        pref.saveBook(book);
        return book;
    }

    public Book getBookView(int bookId) throws ApiException {
        Book book = (Book) Query(apiHelper.doGetBook(bookId).blockingGet());
        return book;
    }

    public Book getBookLocal(int bookId) throws ApiException {
        return pref.getBook();
    }

    public Book getBookRemote(int bookId) throws ApiException {
        Book book = (Book) Query(apiHelper.doGetBook(bookId).blockingGet());
        pref.saveBook(book);
        return book;
    }

    public List<Book> getBookList() throws ApiException {
        return (List<Book>) QueryList(apiHelper.doGetBookList().blockingGet());
    }

    public void addBook(int bookId, int plan) throws ApiException {
        Post(apiHelper.doAddBook(bookId, plan).blockingGet());
    }

    public List<Word> getBookWordListLocal(int bookId) throws ApiException {
        SimpleSQLiteQuery sql = new SimpleSQLiteQuery("select * from Word where bookId=?",
                new Object[]{bookId});
        List<Word> wordList = db.wordDao().queryAll(sql).blockingGet();
        return wordList;
    }

    public List<Word> getBookWordListLocal(int bookId, int tag) throws ApiException {
        SimpleSQLiteQuery sql = new SimpleSQLiteQuery("select * from Word where bookId=? and tag=?",
                new Object[]{bookId, tag});
        List<Word> wordList = db.wordDao().queryAll(sql).blockingGet();
        return wordList;
    }

    public List<Word> getBookWordListRemote(int bookId) throws ApiException {
        List<Word> wordList = (List<Word>) QueryList(apiHelper.doGetBookWordList(bookId).blockingGet());
        db.wordDao().delete(bookId);
        wordList.forEach(w -> db.wordDao().insert(w));
        return wordList;
    }

    public List<Word> getBookWordList(int bookId) throws ApiException {
        List<Word> wordList = getBookWordListLocal(bookId);
        if (wordList.size() == 0) {
            wordList = getBookWordListRemote(bookId);
        }
        return wordList;
    }

    public List<Word> getBookWordList(int bookId, int tag) throws ApiException {
        List<Word> wordList = getBookWordListLocal(bookId);
        if (wordList.size() == 0) {
            getBookWordListRemote(bookId);//全部缓存
        }
        wordList = getBookWordListLocal(bookId, tag);
        return wordList;
    }

    public boolean isBookWordListCached(int bookId) throws ApiException {
        List<Word> wordList = getBookWordListLocal(bookId);
        return wordList.size() > 0;
    }


    /*************Word*************/
    public void setWordTag(int bookId, String word, int tag) throws ApiException {
        try {
            //Update Local Word
            Word wordObj = db.wordDao().findByPk(bookId, word).blockingGet();
            wordObj.tag = tag;
            db.wordDao().update(wordObj);

            //Update Remote Local Word
            Post(apiHelper.doSetWordTag(bookId, word, tag).blockingGet());
        } catch (EmptyResultSetException e) {

        }
    }

    public List<Def> getDefByWords(int bookId, List<String> words) throws ApiException {
        List<Def> defList = db.defDao().findAllByWords(bookId, words).blockingGet();
        if (defList.size() < words.size()) {
            defList = (List<Def>) QueryList(apiHelper.doGetDefListByWords(bookId, words).blockingGet());
        }
        defList.forEach(w -> {
            w.bookId = bookId;
            db.defDao().delete(w);
            db.defDao().insert(w);
        });
        return defList;
    }
}
