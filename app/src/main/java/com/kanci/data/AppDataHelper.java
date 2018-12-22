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
import com.kanci.data.local.db.AppDatabase;
import com.kanci.data.local.prefs.AppPref;
import com.kanci.data.model.api.ApiException;
import com.kanci.data.model.api.ApiResponse;
import com.kanci.data.model.bean.Book;
import com.kanci.data.model.db.Def;
import com.kanci.data.model.db.Word;
import com.kanci.data.model.bean.Task;
import com.kanci.data.remote.ApiHelper;
import com.kanci.utils.AppConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.OkHttpClient.Builder;

public class AppDataHelper {
    Map<String, Integer> data = new HashMap<>();
    final static String KEY_TASK__DONE = "KEY_TASK__DONE";
    final static String KEY_BOOK_DONE = "KEY_BOOK_DONE";
    final static String KEY_TASK_NEW_COUNT = "KEY_TASK_NEW_COUNT";

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

        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        Builder okHttpClient = new OkHttpClient().newBuilder()
                .cookieJar(cookieJar)
                .connectTimeout(60 * 5, TimeUnit.SECONDS)
                .readTimeout(60 * 5, TimeUnit.SECONDS)
                .writeTimeout(60 * 5, TimeUnit.SECONDS);

        this.apiHelper = new Retrofit.Builder()
                //.baseUrl("http://lucky888.vicp.io:10000/index.php/")
                .baseUrl("http://lucky888.vicp.io:10000/index.php/")
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
    public int getBookDoneCache() throws ApiException {
        if (data.containsKey(KEY_BOOK_DONE) || true) {
            Task task = getTaskLocal();
            int count = db.wordDao().doneCount(task.bookId).blockingGet();
            data.put(KEY_BOOK_DONE, count);
        }
        return data.get(KEY_BOOK_DONE);
    }

    public int getTaskDoneCache() throws ApiException {
        if (data.containsKey(KEY_TASK__DONE) || true) {
            Task task = getTaskLocal();
            Set<String> ws = pref.getTaskWordList();
            if (ws.size() == 0) {
                ws = new HashSet<String>(getTaskWordList(task.bookId));
                pref.saveTaskWordList(ws);
            }

            int count = db.wordDao().taskDoneCount(task.bookId, new ArrayList<>(ws)).blockingGet();
            data.put(KEY_TASK__DONE, count);
        }
        return data.get(KEY_TASK__DONE);
    }

    public int getTaskNewCountCache() throws ApiException {
        if (data.containsKey(KEY_TASK_NEW_COUNT) || true) {
            Task task = getTaskLocal();
            Set<String> ws = pref.getTaskWordList();
            if (ws.size() == 0) {
                ws = new HashSet<String>(getTaskWordList(task.bookId));
                pref.saveTaskWordList(ws);
            }

            int count = db.wordDao().taskNewCount(task.bookId, new ArrayList<>(ws)).blockingGet();
            data.put(KEY_TASK_NEW_COUNT, count);
        }
        return data.get(KEY_TASK_NEW_COUNT);
    }

    public void expireWordCache() {
        data.clear();
    }

    /*************Task*************/
    public Task getTaskLocal() throws ApiException {
        Task task = this.pref.getTask();
        if (task == null) {
            task = getTask();
            task.done = getTaskDoneCache();
            saveTaskLocal(task);
        }
        return task;
    }

    public void saveTaskLocal(Task task) {
        this.pref.saveTask(task);
    }

    public Task getTask() throws ApiException {
        Task task = (Task) Query(apiHelper.doGetTask().blockingGet());
        Task localTask = getTaskLocal();
        if (task.version > localTask.version) {
            saveTaskLocal(task);
            return task;
        } else {
            return localTask;
        }
    }

    public void createTask(int bookId) throws ApiException {
        Post(apiHelper.doCreateTask(bookId).blockingGet());
    }

    public List<Word> getTaskWordListLocal(int bookId) throws ApiException {
        Set<String> ws = pref.getTaskWordList();
        if (ws.size() == 0) {
            ws = new HashSet<String>(getTaskWordList(bookId));
            pref.saveTaskWordList(ws);
        }

        SimpleSQLiteQuery sql = new SimpleSQLiteQuery("select * from Word where id=? and word in (?)",
                new Object[]{bookId, ws});
        List<Word> wordList = db.wordDao().queryAll(sql).blockingGet();
        if (ws.size() == 0) {
            //加载单词书的WordList
            getBookWordListLocal(bookId);
            //重新查询
            wordList = db.wordDao().queryAll(sql).blockingGet();
        }
        return wordList;
    }

    public List<Word> getTaskWordListAddDefLocal(int bookId) throws ApiException {
        Set<String> ws = pref.getTaskWordList();
        if (ws.size() == 0) {
            ws = new HashSet<String>(getTaskWordList(bookId));
            pref.saveTaskWordList(ws);
        }
        List<Word> wordList = getTaskWordListLocal(bookId);
        //加载Def
        List<Def> defList = getDefByWordsLocal(bookId, new ArrayList<>(ws));
        Map<String, Word> wordMap = wordList.stream().collect(Collectors.toMap(x -> x.word, x -> x));
        Map<String, Def> defMap = defList.stream().collect(Collectors.toMap(x -> x.word, x -> x));
        defMap.forEach((k, v) -> wordMap.get(k).def = v);

        return wordList;
    }

    public List<String> getTaskWordList(int bookId) throws ApiException {
        return (List<String>) QueryList(apiHelper.doGetTaskWordList(bookId).blockingGet());
    }

    /*************Book*************/
    public Book getBookLocal(int bookId) throws ApiException {
        Book book = pref.getBook();
        if (book == null) {
            book = getBook(bookId);
            saveBookLocal(book);
        }
        return book;
    }

    public void saveBookLocal(Book book) {
        saveBookLocal(book);
    }

    public Book getBook(int bookId) throws ApiException {
        Book book = (Book) Query(apiHelper.doGetBook(bookId).blockingGet());
        book.doneCount = getBookDoneCache();
        saveBookLocal(book);
        return book;
    }

    public List<Book> getBookList() throws ApiException {
        return (List<Book>) QueryList(apiHelper.doGetBookList().blockingGet());
    }

    public void addBook(int bookId, int plan) throws ApiException {
        Post(apiHelper.doAddBook(bookId, plan).blockingGet());
    }

    public List<Word> getBookWordListLocal(int bookId) throws ApiException {
        List<Word> wordList = (List<Word>) QueryList(apiHelper.doGetBookWordList(bookId).blockingGet());
        if (wordList.size() == 0) {
            wordList = getBookWordList(bookId);
            wordList.forEach(w -> db.wordDao().insert(w));
        }
        return wordList;
    }

    public List<Word> getBookWordList(int bookId) throws ApiException {
        return (List<Word>) QueryList(apiHelper.doGetBookWordList(bookId).blockingGet());
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
        return (List<Def>) QueryList(apiHelper.doGetDefListByWords(bookId, words).blockingGet());
    }

    public List<Def> getDefByWordsLocal(int bookId, List<String> words) throws ApiException {
        return (List<Def>) QueryList(apiHelper.doGetDefListByWords(bookId, words).blockingGet());
    }

}
