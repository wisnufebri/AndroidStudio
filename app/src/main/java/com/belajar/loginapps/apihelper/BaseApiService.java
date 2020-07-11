package com.belajar.loginapps.apihelper;

import com.belajar.loginapps.LoginActivity;
import com.belajar.loginapps.model.LoginBody;
import com.belajar.loginapps.model.LoginResult;
import com.belajar.loginapps.model.RegisterBody;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BaseApiService {

    @POST("login")
    Call<LoginResult> loginRequest(@Body LoginBody loginBody);

    @POST("api/user")
    Call<ResponseBody> registerRequest(@Body RegisterBody registerBody);

}
