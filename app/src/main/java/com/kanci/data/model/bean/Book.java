package com.kanci.data.model.bean;

import java.io.Serializable;

/**
 * Created by qw on 18-12-7.
 */

public class Book implements Serializable {
    public int id;
    public String name;
    public int count;
    public int doneCount;
    public boolean isFavor;
    public boolean isStudying;
    public int plan;
    public String tag;

    public int getRemainWord() {
        return count - doneCount;
    }

    public int getRemainDays() {
        return getRemainWord() / plan + Math.min(getRemainWord() % plan, 1);
    }
}
