package com.wisnu.britest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.wisnu.britest.apihelper.BaseApiService;
import com.wisnu.britest.apihelper.RetrofitClient;
import com.wisnu.britest.apihelper.Utility;
import com.wisnu.britest.dummy.DialogUtility;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends Activity {

    EditText nid, officeId;
    Button login;
    private Retrofit retrofit;
    private AlertDialog dlg;

    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Utility.askPermission(this);
        retrofit = RetrofitClient.getClient();

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        nid = findViewById(R.id.etNid);
        officeId = findViewById(R.id.etOfficeId);

        addValidationToViews();

        login = findViewById(R.id.btnLogin);
        login.setOnClickListener(view -> {
            if (nid.getText().toString().length() == 0) {
                nid.setError("Username harus diisi");
            } else if (officeId.getText().toString().length() == 0) {
                officeId.setError("Password harus diisi");
            } else {
                LoginSubmit(nid.getText().toString(), officeId.getText().toString());
            }
        });
    }

    private void addValidationToViews() {
        awesomeValidation.addValidation(this, R.id.etNid, RegexTemplate.NOT_EMPTY, R.string.invalid_username);
        String regexPassword = ".{8,}";
        awesomeValidation.addValidation(this, R.id.etOfficeId, regexPassword, R.string.invalid_password);
    }

    private void LoginSubmit(String nid, String officeId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("nid", nid);
        params.put("officeId", officeId);

        BaseApiService apiService = retrofit.create(BaseApiService.class);

        Call<ResponseBody> result = apiService.postMessage(params);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body() != null)
                        Toast.makeText(LoginActivity.this, "response message" + response.body().string(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });


        DialogUtility.showDialog(R.raw.stay_save, "Loading : Login", LoginActivity.this);


    }


}