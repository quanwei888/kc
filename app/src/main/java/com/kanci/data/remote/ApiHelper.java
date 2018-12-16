package com.kanci.data.remote;


import com.kanci.data.model.api.ApiResponse.CommonResponse;
import com.kanci.data.model.api.ApiResponse.EntityListResponse;
import com.kanci.data.model.api.ApiResponse.EntityResponse;
import com.kanci.data.model.bean.Book;
import com.kanci.data.model.bean.User;
import com.kanci.data.model.db.BookState;
import com.kanci.data.model.db.BookWord;
import com.kanci.data.model.db.BookWordDef;
import com.kanci.data.model.db.TaskWord;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiHelper {
    @GET("/doGetBookState")
    Single<EntityResponse<BookState>> doGetBookState();

    @GET("/doGetBookWordList")
    Single<EntityListResponse<BookWord>> doGetBookWordList(@Query("bookId") int bookId);

    @GET("/doGetTaskWordList")
    Single<EntityListResponse<TaskWord>> doGetTaskWordList(@Query("bookId") int bookId);

    @GET("/doGetBookWordDef")
    Single<EntityResponse<BookWordDef>> doGetBookWordDef(@Query("bookId") int bookId, @Query("word") String word);

    @GET("/doGetBookWordDefList")
    Single<EntityListResponse<BookWordDef>> doGetBookWordDefList(@Query("bookId") int bookId);

    @GET("/doGetBookWordDefListByWords")
    Single<EntityListResponse<BookWordDef>> doGetBookWordDefListByWords(@Query("bookId") int bookId, @Query("word[]") List<String> ids);

    @GET("/doGetBookList")
    Single<EntityListResponse<Book>> doGetBookList();

    @POST("/doLogin")
    Single<EntityResponse<User>> doLogin(@Query("userName") String userName, @Query("password") String password);

    @GET("/doGetUserInfo")
    Single<EntityResponse<User>> doGetUserInfo();

    @GET("/logout.json")
    Single<CommonResponse> doLogout();

    @GET("/doUpdateBookWord")
    Single<CommonResponse> doUpdateBookWord(@Query("tag") int tag, @Query("studyCount") int studyCount);

    @GET("/doSwitchBook")
    Single<CommonResponse> doSwitchBook(@Query("bookId") int bookId);

    @GET("/doAddBook")
    Single<CommonResponse> doAddBook(@Query("bookId") int bookId, @Query("plan") int plan);

    @POST("/doDeleteBook")
    Single<CommonResponse> doDeleteBook(@Query("bookId") int bookId);

    @GET("/doCreateTask")
    Single<CommonResponse> doCreateTask(@Query("bookId") int bookId);

}
