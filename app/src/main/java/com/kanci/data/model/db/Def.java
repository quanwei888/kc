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
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 单词书单词的详细注释，延迟加载
 */
@Entity(tableName = "Def", primaryKeys = {"bookId", "word"})
public class Def implements Serializable {
    @NonNull
    public int bookId;
    @NonNull
    public String word;
    public String pa;
    public String pic;
    public String paSrc;
    public String mean;

    @TypeConverters(Def.class)
    public List<Map<String, String>> means;
    @TypeConverters(Def.class)
    public List<Map<String, String>> sentences;
    @TypeConverters(Def.class)
    public Map<String, Object> qaEn;

    //@TypeConverters(Def.class)
    //public Object qaEn;

    private static Gson gson = new Gson();

    @TypeConverter
    public static List<Map<String, String>> jsonToList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        return gson.fromJson(data, new TypeToken<List<Map<String, String>>>() {
        }.getType());
    }

    @TypeConverter
    public static String listToJson(List<Map<String, String>> obj) {
        return gson.toJson(obj);
    }

    @TypeConverter
    public static Map<String, Object> jsonToQa(String data) {
        if (data == null) {
            return Collections.emptyMap();
        }
        return gson.fromJson(data, new TypeToken<Map<String, Object>>() {
        }.getType());
    }

    @TypeConverter
    public static String qaToJson(Map<String, Object> obj) {
        return gson.toJson(obj);
    }
}
