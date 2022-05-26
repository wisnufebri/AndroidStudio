package com.wisnu.britest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.wisnu.britest.dummy.DialogUtility;

public class SplashActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        DialogUtility.showDialog(R.raw.newflash, "Loading : Get Data Buku", SplashActivity.this);
        Thread thread = new Thread(){
            public void run(){
                try{
                    sleep(4000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
        thread.start();
    }
}