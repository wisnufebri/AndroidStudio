package com.belajar.loginapps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.belajar.loginapps.apihelper.BaseApiService;
import com.belajar.loginapps.apihelper.RetrofitClient;
import com.belajar.loginapps.apihelper.Utility;
import com.belajar.loginapps.model.LoginBody;
import com.belajar.loginapps.model.LoginResult;
import com.belajar.loginapps.model.RegisterBody;
import com.belajar.loginapps.model.RegisterResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends Activity {

    EditText username, name, email, password;
    Button daftar;
    RadioButton aktif, nonaktif;
    Spinner roles;

    private boolean Aaktif;

    private Retrofit retrofit;

    private AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Utility.askPermission(this);
        retrofit = RetrofitClient.getClient();

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        username = findViewById(R.id.etUsername);
        name = findViewById(R.id.etName);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        daftar = findViewById(R.id.btnRegister);
        roles = findViewById(R.id.roles);
        nonaktif = findViewById(R.id.etFalse);
        aktif = findViewById(R.id.etTrue);

        addValidationToViews();

        aktif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((RadioButton) view).isChecked();
                switch (view.getId()){
                    case R.id.etTrue:
                    if (checked){
                        Toast.makeText(getApplicationContext(), "Aktif", Toast.LENGTH_SHORT).show();
                        Aaktif = true;
                    }
                }
            }
        });

        nonaktif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((RadioButton) view).isChecked();
                switch (view.getId()){
                    case R.id.etFalse:
                        if (checked){
                            Toast.makeText(getApplicationContext(), "NonAktif", Toast.LENGTH_SHORT).show();
                            Aaktif = false;
                        }
                }
            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {
                    List<String> roles1 = new ArrayList<>();
                    roles1.add(roles.getSelectedItem().toString());

                    RegisterSubmit(username.getText().toString(), password.getText().toString(),
                            name.getText().toString(), email.getText().toString(), roles1, Aaktif);

                } else {
                    Toast.makeText(getApplicationContext(), "berhasil", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addValidationToViews() {
        awesomeValidation.addValidation(this, R.id.etUsername, RegexTemplate.NOT_EMPTY, R.string.invalid_username);

        awesomeValidation.addValidation(this, R.id.etName, RegexTemplate.NOT_EMPTY, R.string.invalid_name);

        awesomeValidation.addValidation(this, R.id.etEmail, Patterns.EMAIL_ADDRESS, R.string.invalid_email);

        String regexPassword = ".{8,}";
        awesomeValidation.addValidation(this, R.id.etPassword, regexPassword, R.string.invalid_password);
    }

    private void RegisterSubmit(String username, String password, String name, String email, List<String> roles, boolean active) {

        RegisterBody registerBody = new RegisterBody(username, password, name, email, roles, active);

        BaseApiService apiService = retrofit.create(BaseApiService.class);

        Call<ResponseBody> result = apiService.registerRequest(registerBody);

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try { Map<String, Object> retMap = new Gson().fromJson(response.body().string(),
                        new TypeToken<HashMap<String, Object>>() {
                        }.getType());
                    boolean success = (boolean) retMap.get("success");
                    if (success) {
                        Log.e("TAG", "Login Success" + response.body().toString());
                        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                    } else {
                        Log.e("TAG", "login gagal" + response.body().toString());
                        Toast.makeText(RegisterActivity.this, "Eror login", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}