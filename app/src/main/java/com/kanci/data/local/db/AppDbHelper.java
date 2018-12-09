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

package com.kanci.data.local.db;

import com.kanci.data.model.db.Book;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;

/**
 * Created by amitshekhar on 07/07/17.
 */

public class AppDbHelper implements DbHelper {

    private final AppDatabase mAppDatabase;

    public AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    public Observable<List<Book>> getAllBooks() {
        return Observable.fromCallable(new Callable<List<Book>>() {
            @Override
            public List<Book> call() throws Exception {
                return mAppDatabase.bookDao().loadAll();
            }
        });
    }
}
