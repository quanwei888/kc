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

import com.kanci.data.model.db.Def;
import com.kanci.data.model.db.Word;

import java.util.List;

import io.reactivex.Single;

public interface AppDao {

    @Dao
    public interface WordDao {
        @RawQuery
        Single<Word> query(SupportSQLiteQuery query);

        @RawQuery
        Single<List<Word>> queryAll(SupportSQLiteQuery query);

        @Query("select * from Word where bookId=:bookId and word=:word")
        Single<Word> findByPk(int bookId, String word);

        @Query("select * from Word")
        Single<List<Word>> findAll();

        @Query("select * from Word where bookId=:bookId and word in (:words)")
        Single<List<Word>> findTaskAll(int bookId, List<String> words);

        @Query("select * from Word where bookId=:bookId")
        Single<List<Word>> findAll(int bookId);

        @Query("select count(1) from Word where bookId=:bookId and tag=1")
        Single<Integer> doneCount(int bookId);

        @Query("select count(1) from Word where bookId=:bookId and tag=1 and word in (:words)")
        Single<Integer> taskDoneCount(int bookId, List<String> words);

        @Query("select count(1) from Word where bookId=:bookId and (rightCount>0 or errorCount>0) and  word in (:words)")
        Single<Integer> taskNewCount(int bookId, List<String> words);

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insert(Word entity);

        @Update
        void update(Word entity);

        @Query("delete from Word where bookId=:bookId")
        void delete(int bookId);
    }

    @Dao
    public interface DefDao {
        @RawQuery
        Single<Def> query(SupportSQLiteQuery query);

        @Query("select * from Def where bookId=:bookId and word=:word")
        Single<Def> findByPk(int bookId, String word);

        @Query("select * from Def")
        Single<List<Def>> findAll();

        @Query("select * from Def where bookId=:bookId and word in (:words)")
        Single<List<Def>> findAllByWords(int bookId, List<String> words);

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insert(Def entity);

        @Update
        void update(Def entity);
    }
}
