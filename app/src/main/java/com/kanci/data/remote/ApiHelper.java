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
    @GET("/doGetBookState")
    Single<EntityResponse<Task>> doGetTask();

    @GET("/doGetBook")
    Single<EntityResponse<Book>> doGetBook(int bookId);

    @GET("/doGetBookWordList")
    Single<EntityListResponse<Word>> doGetBookWordList(@Query("id") int bookId);

    @GET("/doGetBookList")
    Single<EntityListResponse<Book>> doGetBookList();

    @POST("/doCreateTask")
    Single<CommonResponse> doCreateTask(@Query("id") int bookId);

    @POST("/doAddBook")
    Single<CommonResponse> doAddBook(@Query("id") int bookId, @Query("plan") int plan);

    @GET("/doGetTaskWordList")
    Single<EntityListResponse<String>> doGetTaskWordList(@Query("id") int bookId);

    @GET("/doGetDefListByWords")
    Single<EntityListResponse<Def>> doGetDefListByWords(@Query("id") int bookId, @Query("word[]") List<String> words);

    @POST("/doSetWordTag")
    Single<CommonResponse> doSetWordTag(@Query("id") int bookId, @Query("word") String word, @Query("tag") int tag);

}
