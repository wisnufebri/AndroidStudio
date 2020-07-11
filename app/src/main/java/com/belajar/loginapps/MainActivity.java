package com.belajar.loginapps;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.belajar.loginapps.apihelper.BaseApiService;


public class MainActivity extends Activity {
    TextView tvResultNama;
    String resultNama;
    BaseApiService baseApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initComponents();

        // untuk mendapatkan data dari activity sebelumnya, yaitu activity login.
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            resultNama = extras.getString("result_nama");
        tvResultNama.setText(resultNama);

    }

    private void initComponents() {
            tvResultNama = (TextView) findViewById(R.id.tvResultNama);
        }
}

