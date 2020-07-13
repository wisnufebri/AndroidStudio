package com.belajar.loginapps.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.ValidationStyle;
import com.belajar.loginapps.BookActivity;
import com.belajar.loginapps.LoadingDialog;
import com.belajar.loginapps.MainActivity;
import com.belajar.loginapps.R;
import com.belajar.loginapps.RegisterActivity;
import com.belajar.loginapps.apihelper.BookApiService;
import com.belajar.loginapps.apihelper.RetrofitClient;
import com.belajar.loginapps.apihelper.Utility;
import com.belajar.loginapps.model.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsertHomeFragment extends Fragment {

    EditText tahun, judul, harga, penulis, penerbit;
    ImageButton btnSend;

    private View view;

    private Retrofit retrofit;

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "judul";
//    private static final String ARG_PARAM2 = "penulis";
//    private static final String ARG_PARAM3 = "penerbit";
//    private static final String ARG_PARAM4 = "harga";
//    private static final String ARG_PARAM5 = "tahun";
//
//    // TODO: Rename and change types of parameters
//    private String atjudul;
//    private String atpenulis;
//    private String atpenerbit;
//    private String atharga;
//    private String attahun;
//
//    public InsertHomeFragment() {
//        // Required empty public constructor
//    }


//    // TODO: Rename and change types and number of parameters
//    public static BlankFragment newInstance(String atjudul, String atpenerbit, String atpenulis, String atharga, String attahun) {
//        BlankFragment fragment = new BlankFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, atjudul);
//        args.putString(ARG_PARAM2, atpenerbit);
//        args.putString(ARG_PARAM3, atpenulis);
//        args.putString(ARG_PARAM4, atharga);
//        args.putString(ARG_PARAM5, attahun);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            atjudul = getArguments().getString(ARG_PARAM1);
//            atpenerbit = getArguments().getString(ARG_PARAM2);
//            atpenulis = getArguments().getString(ARG_PARAM3);
//            atharga = getArguments().getString(ARG_PARAM4);

            penerbit = getView().findViewById(R.id.edPenerbit);
            penulis = getView().findViewById(R.id.edPenulis);
            harga = getView().findViewById(R.id.edHarga);
            judul = getView().findViewById(R.id.edJudul);
            tahun = getView().findViewById(R.id.edTahun);
            btnSend = getView().findViewById(R.id.buttonSend);

            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (judul.getText().toString().length()==0){
                        judul.setError("Harus disi");
                    } else if (penerbit.getText().toString().length()==0){
                        penerbit.setError("Masukkan Penerbit");
                    } else if (penulis.getText().toString().length()==0){
                        penulis.setError("Masukkan nama penulis");
                    } else if (harga.getText().toString().length()==0){
                        harga.setError("Masukkan Harga Buku");
                    } else if (tahun.getText().toString().length()==0){
                        tahun.setError("Masukkan Tahun Terbit");
                    } else {
                        Toast.makeText(getActivity(), "Input data berhasil", Toast.LENGTH_SHORT).show();
                        InsertBook(judul.getText().toString(),
                                penulis.getText().toString(),
                                penerbit.getText().toString(),
                                harga.getText().toString(),
                                tahun.getText().toString());
                    }
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.insert_home_fragment, container, false);
        return view;
    }

    private void InsertBook(String judul, String penulis, String penerbit,
                            String harga, String tahun){
        Book bookInput = new Book(judul, penulis, penerbit, harga, tahun);

        BookApiService bookApiService = retrofit.create(BookApiService.class);

        Call<ResponseBody> result = bookApiService.insertBook(bookInput);

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try { Map<String, Object> retMap = new Gson().fromJson(response.body().string(),
                        new TypeToken<HashMap<String, Object>>(){
                        }.getType());
                    boolean success = (boolean) retMap.get("Success");
                    if (success){
                        Log.e(TAG, "onResponse: success" + response.body().toString());
                        Intent insertIntent = new Intent(getActivity(), MainActivity.class);
                        startActivity(insertIntent);
                    } else {
                        Log.e(TAG, "onResponse: Gagal" + response.body().toString() );
                        Toast.makeText(getActivity(), "Gagal Menambah data", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}