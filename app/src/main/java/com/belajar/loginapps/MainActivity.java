package com.belajar.loginapps;


import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.belajar.loginapps.fragment.InsertHomeFragment;
import com.belajar.loginapps.fragment.ViewHomeFragment;
import com.belajar.loginapps.fragment.BookHomeFragment;



public class MainActivity extends AppCompatActivity {
    private BottomNavigationView btmNavigation;
    private String TAG = "mainactivity";
    private TextView textJudul;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        openHomeFragment();

    }

    private void initView() {
        btmNavigation = findViewById(R.id.btmNavigation);
        textJudul = findViewById(R.id.textJudul);

        btmNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        final int previousItem = btmNavigation.getSelectedItemId();
                        final int nextItem = item.getItemId();
                        if (previousItem != nextItem) {
                            switch (nextItem) {
                                case R.id.home:
                                    selectedFragment("home");
                                    break;
                                case R.id.add:
                                    selectedFragment("add");
                                    break;
                            }
                        }
                        return true;
                    }
                }
        );

        btmNavigation.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                Log.e(TAG, "onNavigationItemReselected: "+ item );
            }
        });
    }

    private void selectedFragment(String id) {

        Log.e(TAG, "selectedFragment: " + id);
        if (id.equals("home")) {

            Log.e(TAG, "halaman home");
            openHomeFragment();
        } else {
            Log.e(TAG, "halaman add ");
            openAddFragment();
        }

    }

    public void openHomeFragment() {
        textJudul.setVisibility(View.VISIBLE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BookHomeFragment strCode = new BookHomeFragment();
        fragmentTransaction.replace(R.id.content, strCode, "home fragment");
        fragmentTransaction.commit();
    }


    public void openAddFragment() {
        textJudul.setVisibility(View.GONE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        InsertHomeFragment bookAdd = new InsertHomeFragment();
        fragmentTransaction.replace(R.id.content, bookAdd, "book fragment");
        fragmentTransaction.commit();
    }

    public void openViewFragment() {
        textJudul.setVisibility(View.GONE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ViewHomeFragment bookView = new ViewHomeFragment ();
        fragmentTransaction.replace(R.id.content, bookView, "book fragment");
        fragmentTransaction.commit();
    }
}

