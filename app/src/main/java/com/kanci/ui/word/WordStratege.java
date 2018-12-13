package com.kanci.ui.word;

import com.kanci.data.model.db.BookWordDef;
import com.kanci.data.model.db.TaskWord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class WordStratege {
    List<TaskWord> taskWordList;
    List<BookWordDef> wordDefList;
    Map<String, TaskWord> taskWordMap;
    Map<String, BookWordDef> wordDefMap;
    List<String> words;
    Iterator<String> wordIter;

    public WordStratege(List<TaskWord> taskWordList, List<BookWordDef> wordDefList) {
        this.taskWordList = taskWordList;
        this.wordDefList = wordDefList;

        taskWordMap = new HashMap<>();
        wordDefMap = new HashMap<>();
        words = new ArrayList<>();
        for (TaskWord taskWord : taskWordList) {
            taskWordMap.put(taskWord.word, taskWord);
            words.add(taskWord.word);
        }
        for (BookWordDef wordDef : wordDefList) {
            wordDefMap.put(wordDef.word, wordDef);
        }
        wordIter = words.iterator();
    }

    public boolean hasNext() {
        return wordIter.hasNext();
    }

    public String next() {
        return wordIter.next();
    }

    public TaskWord taskWord(String word) {
        return taskWordMap.get(word);
    }

    public BookWordDef wordDef(String word) {
        return wordDefMap.get(word);
    }
}
