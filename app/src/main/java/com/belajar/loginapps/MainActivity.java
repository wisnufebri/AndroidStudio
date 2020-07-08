package com.belajar.loginapps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    EditText username, name, email, password;
    Button daftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.txtUsername);
        name = (EditText) findViewById(R.id.txtName);
        email = (EditText) findViewById(R.id.txtEmail);
        password = (EditText) findViewById(R.id.txtPassword);
        daftar = (Button) findViewById(R.id.btn_register);

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