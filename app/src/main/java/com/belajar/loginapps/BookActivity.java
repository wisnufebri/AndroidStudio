package com.belajar.loginapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.belajar.loginapps.fragment.BookHomeFragment;
import com.belajar.loginapps.fragment.InsertHomeFragment;

public class BookActivity extends AppCompatActivity {

    ImageButton add, home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);
        openHomeFragment();

        home = findViewById(R.id.btnHome);
        add = findViewById(R.id.btnAdd);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomeFragment();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertHomeFragment();
            }
        });
    }


    private void openHomeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        BookHomeFragment strCode = new BookHomeFragment();
        fragmentTransaction.replace(R.id.content, strCode, "home fragment");
        fragmentTransaction.commit();
    }

    private void insertHomeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        InsertHomeFragment insertHomeFragment = new InsertHomeFragment();
        fragmentTransaction.replace(R.id.content, insertHomeFragment, "insert fragment");
        fragmentTransaction.commit();
    }
}
