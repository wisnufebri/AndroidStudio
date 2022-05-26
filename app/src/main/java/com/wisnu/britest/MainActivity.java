package com.wisnu.britest;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.wisnu.britest.apihelper.BaseApiService;
import com.wisnu.britest.apihelper.RetrofitClient;
import com.wisnu.britest.apihelper.Utility;
import com.wisnu.britest.dummy.DialogUtility;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    EditText name, address, email;
    Button btnSend;

    private AwesomeValidation awesomeValidation;

    private Retrofit retrofit;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utility.askPermission(this);
        retrofit = RetrofitClient.getClient();

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        name = findViewById(R.id.etName);
        address = findViewById(R.id.etAddress);
        email = findViewById(R.id.etEmail);

        addValidationToViews();

        btnSend = findViewById(R.id.btnLogin);
        btnSend.setOnClickListener(view -> {
            if (name.getText().toString().length() == 0) {
                name.setError("Nama harus diisi");
            } else if (address.getText().toString().length() == 0) {
                address.setError("Alamat harus diisi");
            } else if (email.getText().toString().length() == 0 && emailValidator(email.toString())) {
                email.setError("Email harus diisi");
            }
            else {
                AddData(name.getText().toString(), address.getText().toString(), email.getText().toString());
            }
        });
    }

    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void addValidationToViews() {
        awesomeValidation.addValidation(this, R.id.etName, RegexTemplate.NOT_EMPTY, R.string.invalid_username);

        String regexPassword = ".{8,}";
        awesomeValidation.addValidation(this, R.id.etAddress, regexPassword, R.string.invalid_password);

        awesomeValidation.addValidation(this, R.id.etEmail, RegexTemplate.NOT_EMPTY, R.string.invalid_username);

    }

    private void AddData(String name, String address, String email) {
        HashMap<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("address", address);
        params.put("email", email);

        BaseApiService apiService = retrofit.create(BaseApiService.class);

        Call<ResponseBody> result = apiService.postMessage(params);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body() != null)
                        Toast.makeText(MainActivity.this, "response message" + response.body().string(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
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

        DialogUtility.showDialog(R.raw.stay_save, "Loading : Login", MainActivity.this);


    }


}

