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

package com.kanci.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.kanci.data.model.bean.Book;
import com.kanci.data.model.bean.Task;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by amitshekhar on 07/07/17.
 */

public class AppPref {

    public static final String PREF_KEY_USER_ID = "PREF_KEY_USER_ID";
    public static final String PREF_KEY_USER_NAME = "PREF_KEY_USER_NAME";
    public static final String PREF_KEY_USER_PIC = "PREF_KEY_USER_PIC";
    public static final String PREF_KEY_COOKIE_ID = "PREF_KEY_COOKIE_ID";
    public static final String PREF_KEY_TASK = "PREF_KEY_TASK";
    public static final String PREF_KEY_BOOK = "PREF_KEY_BOOK";
    public static final String PREF_KEY_TASK_LIST = "PREF_KEY_TASK_LIST";

    public final SharedPreferences prefs;

    public AppPref(Context context, String prefFileName) {
        prefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    public Task getTask() {
        String json = prefs.getString(PREF_KEY_TASK, "");
        if (json == null) {
            return null;
        }
        return new Gson().fromJson(json, Task.class);

    }

    public void saveTask(Task task) {
        prefs.edit().putString(PREF_KEY_TASK, new Gson().toJson(task));
        prefs.edit().apply();
    }

    public Book getBook() {
        String json = prefs.getString(PREF_KEY_BOOK, "");
        if (json == null) {
            return null;
        }
        return new Gson().fromJson(json, Book.class);

    }

    public void saveBook(Book book) {
        prefs.edit().putString(PREF_KEY_BOOK, new Gson().toJson(book));
        prefs.edit().apply();
    }

    public void saveTaskWordList(Set<String> wordList) {
        prefs.edit().putStringSet(PREF_KEY_TASK_LIST, wordList);
    }

    public Set<String> getTaskWordList() {
        return prefs.getStringSet(PREF_KEY_TASK_LIST, new HashSet<String>());
    }
}
