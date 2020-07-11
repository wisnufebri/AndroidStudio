package com.belajar.loginapps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

    EditText username, name, email, password;
    Button daftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.etUsername);
        name = (EditText) findViewById(R.id.etName);
        email = (EditText) findViewById(R.id.etEmail);
        password = (EditText) findViewById(R.id.etPassword);
        daftar = (Button) findViewById(R.id.btnRegister);

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().length()==0){
                    username.setError("Username harus diisi");
                } else if(name.getText().toString().length()==0){
                    name.setError("Nama tidak boleh kosong");
                } else if (email.getText().toString().length()==0){
                    email.setError("Email masih kosong");
                } else if (password.getText().toString().length()==0){
                    password.setError("Password harus diisi");
                } else {
                    Toast.makeText(getApplicationContext(), "Registrasi" +
                            "berhasil", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}