package com.belajar.loginapps.apihelper;

import com.belajar.loginapps.LoginActivity;
import com.belajar.loginapps.model.LoginBody;
import com.belajar.loginapps.model.LoginResult;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BaseApiService {

    @POST("login")
    Call<LoginResult> loginRequest(@Body LoginBody loginBody);


//    @FormUrlEncoded
//    @POST("/register")
//    Call<ResponseBody> registerRequest(@Field("nama") String nama,
//                                       @Field("email") String email,
//                                       @Field("password") String password);

}
