package com.belajar.loginapps;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.AwesomeValidation;

import retrofit2.Retrofit;

public class InserHometFragment extends Fragment {


    EditText judulbuku, penulis, penerbit, tahun, harga;

    private Retrofit retrofit;
    private AwesomeValidation awesomeValidation;

    @Override
    public void onCreate(Bundle saveeInstanceState){
        super.onCreate(saveeInstanceState);
        setContentView(R.layout.insert_home_fragment);

    }

    private void setContentView(int insert_home_fragment) {
    }


}