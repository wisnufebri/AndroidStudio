package com.belajar.loginapps.apihelper;

import com.belajar.loginapps.model.Book;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface BookApiService {
    @GET("api/buku")
    Call<List<Book>> getAllBuku(@Header("Authorization") String token);

    @POST("api/insert")
    Call<ResponseBody> insertBook(@Body Book book);
}
