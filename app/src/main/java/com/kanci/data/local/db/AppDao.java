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

import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;
import android.arch.persistence.room.Update;

import com.kanci.data.model.db.Book;
import com.kanci.data.model.db.BookState;
import com.kanci.data.model.db.BookTask;

import java.util.List;

import io.reactivex.Single;

public interface AppDao {
    @Dao
    public interface BookStateDao {
        @RawQuery
        Single<BookState> query(SupportSQLiteQuery query);

        @Query("select * from BookState where bookId=:bookId")
        Single<BookState> findByPk(int bookId);

        @Query("select * from BookState")
        Single<List<BookState>> findAll();

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insert(BookState entity);

        @Update
        void update(BookState entity);
    }

    @Dao
    public interface BookTaskDao {
        @RawQuery
        Single<BookTask> query(SupportSQLiteQuery query);

        @Query("select * from BookTask where bookId=:bookId")
        Single<BookTask> findByPk(int bookId);

        @Query("select * from BookTask")
        Single<List<BookTask>> findAll();

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insert(BookTask entity);

        @Update
        void update(BookTask entity);
    }
}
