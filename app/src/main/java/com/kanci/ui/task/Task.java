package com.kanci.ui.task;

import com.kanci.data.AppDataManager;
import com.kanci.data.model.db.BookState;
import com.kanci.data.model.db.BookWordDef;
import com.kanci.data.model.db.TaskWord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class Task {
    Map<String, Item> data = new TreeMap<>();
    Queue<Item> reviewData = new LinkedList<>();
    Queue<Item> newData = new LinkedList<>();
    boolean learnNew = false;

    public static class Item {
        public String word;
        public TaskWord taskWord;
        public BookWordDef wordDef;
    }

    public void addItem(Item item) {
        data.put(item.word, item);
    }

    public void createPlan() {
        reviewData.clear();
        newData.clear();


        //去除已砍单词，并分组
        List<Item> reviewArr = new ArrayList<>();
        List<Item> newArr = new ArrayList<>();
        data.forEach((k, v) -> {
            if (v.taskWord.tag == 1) {
                //已砍
                data.remove(k);
                return;
            }
            if (v.taskWord.rightCount > 3) {
                //学习次数大于N次，学习完成
                data.remove(k);
                return;
            }
            if (v.taskWord.isNew) {
                newArr.add(v);
            } else {
                reviewArr.add(v);
            }
        });

        //打乱各组顺序
        Collections.shuffle(reviewArr);
        Collections.shuffle(newArr);
        learnNew = !(reviewArr.size() > 0);
    }

    public Item next() {
        if (reviewData.size() > 0) {
            return reviewData.poll();
        }
        if (learnNew && newData.size() > 0) {
            return newData.poll();
        }
        createPlan();
        if (!hasNext()) {
            //计划学习完成
            return null;
        } else {
            return next();
        }
    }

    boolean hasNext() {
        if (reviewData.size() == 0 && newData.size() == 0) {
            return false;
        }
        return true;
    }
}
