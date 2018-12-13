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

package com.kanci.data.model.db;

import android.arch.persistence.room.Entity;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * 单词书单词的详细注释，延迟加载
 */
@Entity(tableName = "BookWordDef", primaryKeys = {"bookId", "word"})
public class BookWordDef implements Serializable {
    @NonNull
    public int bookId;
    @NonNull
    public String word;
    public String def;
}
