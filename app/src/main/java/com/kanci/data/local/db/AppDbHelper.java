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

import android.arch.persistence.db.SimpleSQLiteQuery;

import com.kanci.data.model.db.Book;
import com.kanci.data.model.db.BookState;
import com.kanci.data.model.db.BookTask;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Single;

public class AppDbHelper {

    private final AppDatabase db;

    public AppDbHelper(AppDatabase db) {
        this.db = db;
    }

    public Single<BookState> getCurrentBookState() {
        SimpleSQLiteQuery query = new SimpleSQLiteQuery("SELECT * FROM BookState WHERE isStudying = 1");
        return db.bookStateDao().query(query);
    }

    public Single<BookTask> getBookTask(int bookId) {
        return db.bookTaskDao().findByPk(bookId);
    }

}
