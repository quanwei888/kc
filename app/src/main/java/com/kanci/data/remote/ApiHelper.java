package com.kanci.data.remote;


import com.kanci.data.model.api.Response.CommonResponse;
import com.kanci.data.model.api.Response.EntityResponse;
import com.kanci.data.model.api.Response.EntityListResponse;
import com.kanci.data.model.bean.Book;
import com.kanci.data.model.db.BookState;
import com.kanci.data.model.db.BookWord;
import com.kanci.data.model.db.BookWordDef;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiHelper {
    @GET("/bookState.json")
    Single<EntityResponse<BookState>> doGetBookState();

    @GET("/bookWordList.json")
    Single<EntityListResponse<BookWord>> doGetBookWordList(int bookId);

    @GET("/bookWordDef.json")
    Single<EntityResponse<BookWordDef>> doGetBookWordDef(int bookId, String word);

    @GET("/bookWordDefList.json")
    Single<EntityListResponse<BookWordDef>> doGetBookWordDefList(int bookId);

    @GET("/bookList.json")
    Single<EntityListResponse<Book>> doGetBookList();

}
