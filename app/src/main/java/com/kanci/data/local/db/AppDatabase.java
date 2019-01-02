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

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.kanci.data.model.db.Def;
import com.kanci.data.model.bean.Task;
import com.kanci.data.model.db.Word;
import com.kanci.utils.AppConstants;


@Database(entities = {
        Word.class,
        Def.class,
}, version = 25, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract AppDao.WordDao wordDao();

    public abstract AppDao.DefDao defDao();

    public static AppDatabase instance(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, AppConstants.DB_NAME).fallbackToDestructiveMigration().build();
    }

}
