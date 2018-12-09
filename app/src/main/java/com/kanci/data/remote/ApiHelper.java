package com.kanci.data.remote;

import com.kanci.data.model.api.BookListResponse;
import com.kanci.data.model.api.BookResponse;
import com.kanci.data.model.api.WordListResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiHelper {
    @GET("/bookList.json")
    Single<BookListResponse> doGetBookList(@Query("uid") int uid);

    @GET("/book.json")
    Single<BookResponse> doGetCurrentBook(@Query("uid") int uid);

    @GET("/wordList.json")
    Single<WordListResponse> doGetBookWordList(@Query("bid") int bid, @Query("page") int page, @Query("size") int size);
}
