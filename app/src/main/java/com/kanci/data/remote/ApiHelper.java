package com.kanci.data.remote;


import com.kanci.data.model.api.ApiResponse.CommonResponse;
import com.kanci.data.model.api.ApiResponse.EntityListResponse;
import com.kanci.data.model.api.ApiResponse.EntityResponse;
import com.kanci.data.model.bean.Book;
import com.kanci.data.model.bean.User;
import com.kanci.data.model.db.Def;
import com.kanci.data.model.bean.Task;
import com.kanci.data.model.db.Word;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiHelper {
    @GET("/task/view")
    Single<EntityResponse<Task>> doGetTask();

    @GET("/task/list")
    Single<EntityListResponse<String>> doGetTaskWordList(@Query("bookId") int bookId);

    @GET("/book/view")
    Single<EntityResponse<Book>> doGetBook(@Query("bookId") int bookId);

    @GET("/book-words/list")
    Single<EntityListResponse<Word>> doGetBookWordList(@Query("bookId") int bookId);

    @GET("/doGetBookList")
    Single<EntityListResponse<Book>> doGetBookList();

    @POST("/doCreateTask")
    Single<CommonResponse> doCreateTask(@Query("bookId") int bookId);

    @POST("/doAddBook")
    Single<CommonResponse> doAddBook(@Query("bookId") int bookId, @Query("plan") int plan);

    @GET("/doGetDefListByWords")
    Single<EntityListResponse<Def>> doGetDefListByWords(@Query("bookId") int bookId, @Query("word[]") List<String> words);

    @POST("/doSetWordTag")
    Single<CommonResponse> doSetWordTag(@Query("bookId") int bookId, @Query("word") String word, @Query("tag") int tag);

}
