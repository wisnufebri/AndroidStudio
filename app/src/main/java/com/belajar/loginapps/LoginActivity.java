package com.belajar.loginapps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.belajar.loginapps.apihelper.AppService;
import com.belajar.loginapps.apihelper.BaseApiService;
import com.belajar.loginapps.apihelper.RetrofitClient;
import com.belajar.loginapps.apihelper.Utility;
import com.belajar.loginapps.model.LoginBody;
import com.belajar.loginapps.model.LoginResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends Activity {

    private Retrofit retrofit;

    EditText username, password;
    Button login, register;

    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Utility.askPermission(this);
        retrofit = RetrofitClient.getClient();

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        username = (EditText) findViewById(R.id.etUsername);
        password = (EditText) findViewById(R.id.etPassword);

        addValidationToViews();

        register = findViewById(R.id.btnRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                finish();
            }
        });

        login = findViewById(R.id.btnLogin);
        final LoadingDialog loadingDialog = new LoadingDialog(LoginActivity.this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.startLoadingDialog();
                if (username.getText().toString().length() == 0) {
                    username.setError("Username harus diisi");
                } else if (password.getText().toString().length() == 0) {
                    password.setError("Password harus diisi");
                } else {
                    Toast.makeText(getApplicationContext(), "Login " + "berhasil", Toast.LENGTH_SHORT).show();
                    LoginSubmit(username.getText().toString(), password.getText().toString());
                }
            }
        });
    }

    private void addValidationToViews() {
        awesomeValidation.addValidation(this, R.id.etUsername, RegexTemplate.NOT_EMPTY, R.string.invalid_username);
        String regexPassword = ".{8,}";
        awesomeValidation.addValidation(this, R.id.etPassword, regexPassword, R.string.invalid_password);
    }

    private void LoginSubmit(String userName, String password) {

        LoginBody loginBody = new LoginBody(userName, password);

        BaseApiService apiService = retrofit.create(BaseApiService.class);

        Call<LoginResult> result = apiService.loginRequest(loginBody);

        result.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                try {
                    if (response.body().isSuccess()) {
                        Log.e("TAG", "Login Success" + response.body().toString());
                        AppService.setToken("Bearer " + response.body().getToken());
                        Intent mainIntent = new Intent(LoginActivity.this, BookActivity.class);
                        startActivity(mainIntent);
                        finish();
                    } else {
                        Log.e("TAG", "login gagal" + response.body().toString());
                        Toast.makeText(LoginActivity.this, "Eror login", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}