package com.kanci.ui.word;

import com.kanci.data.model.db.BookWordDef;
import com.kanci.data.model.db.TaskWord;

import java.util.List;

public class WordStratege {
    List<TaskWord> taskWordList;
    List<BookWordDef> wordDefList;

    public WordStratege(List<TaskWord> taskWordList, List<BookWordDef> wordDefList) {
        this.taskWordList = taskWordList;
        this.wordDefList = wordDefList;
    }
}
