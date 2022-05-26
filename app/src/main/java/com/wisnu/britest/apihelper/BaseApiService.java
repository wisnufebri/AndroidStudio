package com.wisnu.britest.apihelper;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BaseApiService {

    @FormUrlEncoded
    @POST("/YVjpleBzrQukdx7j60k0")
    Call<ResponseBody> postMessage(@FieldMap HashMap<String, String> params);
}
