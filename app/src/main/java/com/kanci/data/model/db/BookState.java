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
import android.arch.persistence.room.Ignore;

import javax.inject.Inject;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * 当前单词书学习的状态
 */
@Entity(tableName = "BookState", primaryKeys = {"bookId"})
public class BookState implements Serializable {
    @NonNull
    public int bookId;
    public int taskId;
    public String bookName;
    public int wordCount;
    public int wordDone;
    public int plan;
    public int taskNewWord;
    public int taskReviewWord;
    public int taskDoneWord;
    public boolean taskIsCached;//Ignore

    public int getRemainWord() {
        return wordCount - wordDone;
    }

    public int getRemainDays() {
        return getRemainWord() / plan + Math.min(getRemainWord() % plan, 1);
    }
}
