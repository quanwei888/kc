package com.kanci.data.remote;


import com.kanci.data.model.api.ApiResponse.CommonResponse;
import com.kanci.data.model.api.ApiResponse.EntityResponse;
import com.kanci.data.model.api.ApiResponse.EntityListResponse;
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
    Single<EntityListResponse<BookWord>> doGetBookWordList(int bookId);

    @GET("/doGetTaskWordList")
    Single<EntityListResponse<TaskWord>> doGetTaskWordList(int bookId);

    @GET("/bookWordDef.json")
    Single<EntityResponse<BookWordDef>> doGetBookWordDef(int bookId, String word);

    @GET("/bookWordDefList.json")
    Single<EntityListResponse<BookWordDef>> doGetBookWordDefList(int bookId);

    @GET("/doGetBookWordDefListByWords")
    Single<EntityListResponse<BookWordDef>> doGetBookWordDefListByWords(int bookId, @Query("word[]") List<String> ids);

    @GET("/doGetBookList")
    Single<EntityListResponse<Book>> doGetBookList();

    @POST("/login.php")
    Single<EntityResponse<User>> doLogin(String userName, String password);

    @GET("/getUserInfo.json")
    Single<EntityResponse<User>> doGetUserInfo();

    @GET("/logout.json")
    Single<CommonResponse> doLogout();

    @GET("/setBookWordTag.json")
    Single<CommonResponse> doUpdateBookWord(int tag, int studyCount);

    @GET("/switchBook.json")
    Single<CommonResponse> doSwitchBook(int bookId);

    @GET("/addBook.json")
    Single<CommonResponse> doAddBook(int bookId, int plan);

    @POST("/deleteBook.json")
    Single<CommonResponse> doDeleteBook(int bookId);

    @GET("/doCreateTask.json")
    Single<CommonResponse> doCreateTask(int bookId);

}
