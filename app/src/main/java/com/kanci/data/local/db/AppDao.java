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

import com.kanci.data.model.db.BookState;
import com.kanci.data.model.db.BookWord;
import com.kanci.data.model.db.BookWordDef;
import com.kanci.data.model.db.TaskWord;

import java.util.List;

import io.reactivex.Single;

public interface AppDao {
    @Dao
    public interface BookStateDao {
        @RawQuery
        Single<BookState> query(SupportSQLiteQuery query);

        @Query("select * from BookState limit 1")
        Single<BookState> findOne();

        @Query("select * from BookState")
        Single<List<BookState>> findAll();

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insert(BookState entity);

        @Update
        void update(BookState entity);
    }

    @Dao
    public interface BookWordDao {
        @RawQuery
        Single<BookWord> query(SupportSQLiteQuery query);

        @RawQuery
        Single<List<BookWord>> queryAll(SupportSQLiteQuery query);

        @Query("select * from BookWord where bookId=:bookId and word=:word")
        Single<BookWord> findByPk(int bookId, String word);

        @Query("select * from BookWord")
        Single<List<BookWord>> findAll();

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insert(BookWord entity);

        @Update
        void update(BookWord entity);
    }

    @Dao
    public interface BookWordDefDao {
        @RawQuery
        Single<BookWordDef> query(SupportSQLiteQuery query);

        @Query("select * from BookWordDef where bookId=:bookId and word=:word")
        Single<BookWordDef> findByPk(int bookId, String word);

        @Query("select * from BookWordDef")
        Single<List<BookWordDef>> findAll();

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insert(BookWordDef entity);

        @Update
        void update(BookWordDef entity);
    }

    @Dao
    public interface TaskWordDao {
        @RawQuery
        Single<TaskWord> query(SupportSQLiteQuery query);

        @Query("select * from TaskWord where bookId=:bookId and word=:word")
        Single<TaskWord> findByPk(int bookId, String word);

        @Query("select * from TaskWord")
        Single<List<TaskWord>> findAll();

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insert(TaskWord entity);

        @Update
        void update(TaskWord entity);
    }
}
