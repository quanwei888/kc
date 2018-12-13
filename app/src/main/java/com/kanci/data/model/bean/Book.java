package com.kanci.data.model.bean;

import java.io.Serializable;

/**
 * Created by qw on 18-12-7.
 */

public class Book implements Serializable {
    public int bookId;
    public String bookName;
    public int wordCount;
    public int wordDone;
    public boolean isFavor;
    public boolean isStudying;
    public int plan;
    public String tag;

    public int getRemainWord() {
        return wordCount - wordDone;
    }

    public int getRemainDays() {
        return getRemainWord() / plan + Math.min(getRemainWord() % plan, 1);
    }
}
