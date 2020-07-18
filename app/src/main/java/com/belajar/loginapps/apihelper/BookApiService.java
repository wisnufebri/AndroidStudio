package com.belajar.loginapps.apihelper;

import com.belajar.loginapps.model.Book;
import com.belajar.loginapps.model.BookResult;

import java.util.List;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BookApiService {

    @Headers({"Content-Encoding: gzip"})
    @GET("api/buku")
    Call<List<Book>> getAllBuku(@Header("Authorization") String token);

    @POST("api/buku")
    Call<ApiResponse> insertNewBook(@Header("Authorization") String token, @Body Book body);

    @GET("api/buku/byId/{id}")
    Call<BookResult> getBookById(@Header("Authorization") String token, @Path("id") int id);

    @DELETE("api/buku")
    Call<ApiResponse> deleteBook(@Header("Authorization") String token, @Query("id") int id);

    @PUT("api/buku")
    Call<ApiResponse> updateBook(@Header("Authorization") String token, @Body Book body);
}