package com.kanci.data.model.bean;

import java.io.Serializable;

/**
 * Created by qw on 18-12-7.
 */

public class Book implements Serializable {
    public int bookId;
    public String bookName;
    public int wordCount;
    public boolean isFavor;
    public boolean isStudying;
    public String tag;
}
