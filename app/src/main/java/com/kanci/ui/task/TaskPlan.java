package com.kanci.ui.task;

import com.kanci.data.model.db.Def;
import com.kanci.data.model.db.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class TaskPlan {
    Map<String, Word> data = new TreeMap<>();
    Queue<Word> reviewData = new LinkedList<>();
    Queue<Word> newData = new LinkedList<>();
    boolean learnNew = false;

    public TaskPlan(List<Word> wordList) {
        data = wordList.stream().collect(Collectors.toMap(x -> x.word, x -> x));
        createPlan();
    }

    public int getCount() {
        return data.size();
    }

    public int getNewCount() {
        return newData.size();
    }

    public int getReviewCount() {
        return reviewData.size();
    }

    protected void createPlan() {
        reviewData.clear();
        newData.clear();


        //去除已砍单词，并分组
        List<Word> reviewArr = new ArrayList<>();
        List<Word> newArr = new ArrayList<>();
        Iterator<Map.Entry<String, Word>> iter = data.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Word> entry = iter.next();
            Word v = entry.getValue();
            if (v.tag == 1) {
                //已砍
                iter.remove();
                continue;
            }
            if (v.rightCount > 3) {
                //学习次数大于N次，学习完成
                iter.remove();
                continue;
            }
            if (v.isNew()) {
                newArr.add(v);
            } else {
                reviewArr.add(v);
            }
        }

        //打乱各组顺序
        Collections.shuffle(reviewArr);
        Collections.shuffle(newArr);
        reviewData.addAll(reviewArr);
        newData.addAll(newArr);
    }

    public Word next() {
        if (reviewData.size() > 0) {
            return reviewData.poll();
        }
        learnNew = !(reviewData.size() > 0);
        if (learnNew && newData.size() > 0) {
            return newData.poll();
        }
        if (!hasNext()) {
            //计划学习完成
            return null;
        } else {
            return next();
        }
    }

    public boolean hasNext() {
        if (reviewData.size() == 0 && newData.size() == 0) {
            createPlan();
        }
        if (reviewData.size() == 0 && newData.size() == 0) {
            return false;
        }
        return true;
    }
}
