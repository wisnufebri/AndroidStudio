package com.belajar.loginapps.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.ValidationStyle;
import com.belajar.loginapps.BookActivity;
import com.belajar.loginapps.LoadingDialog;
import com.belajar.loginapps.LoginActivity;
import com.belajar.loginapps.MainActivity;
import com.belajar.loginapps.R;
import com.belajar.loginapps.RegisterActivity;
import com.belajar.loginapps.adapter.BookAdapter;
import com.belajar.loginapps.apihelper.ApiResponse;
import com.belajar.loginapps.apihelper.AppService;
import com.belajar.loginapps.apihelper.BookApiService;
import com.belajar.loginapps.apihelper.RetrofitClient;
import com.belajar.loginapps.apihelper.Utility;
import com.belajar.loginapps.model.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;

public class InsertHomeFragment extends Fragment implements View.OnClickListener {

    EditText tahun, judul, harga, penulis, penerbit;
    ImageButton btnSend;
    ImageView imageView;
    Button addImage;
    Uri uri;

    public static final int PICK_IMAGE = 1;
    private String base64Image = "";
    private View view;
    private String TAG = "insertfragment";
    private Retrofit retrofit;

    private void addImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    private void initRetrofit() {
        retrofit = RetrofitClient.getClient();
    }


    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        byte[] b = baos.toByteArray();

        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            Uri uri = data.getData();
            InputStream imageStream;
            String encodeImage = "";
            try {
                imageStream = getContext().getContentResolver().openInputStream(uri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                encodeImage = encodeImage(selectedImage);
                imageView.setImageURI(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            base64Image = encodeImage;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.insert_home_fragment, container, false);
        initRetrofit();

        penerbit = view.findViewById(R.id.edPenerbit);
        penulis = view.findViewById(R.id.edPenulis);
        harga = view.findViewById(R.id.edHarga);
        judul = view.findViewById(R.id.edJudul);
        tahun = view.findViewById(R.id.edTahun);

        btnSend = view.findViewById(R.id.buttonSend);
        imageView = view.findViewById(R.id.gambarbuku);
        addImage = view.findViewById(R.id.btnaddimage);

        addImage.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        return view;
    }

    private void InsertBook(String judul, String penulis, String penerbit,
                            String tahun, String harga) {
        Book book = new Book();
        book.setHarga(Integer.valueOf(harga));
        book.setJudul(judul);
        book.setPenulis(penulis);
        book.setPenerbit(penerbit);
        book.setTahun(Integer.valueOf(tahun));
        book.setThumb(base64Image);

        BookApiService apiService = retrofit.create(BookApiService.class);
        Call<ApiResponse> result = apiService.insertNewBook(AppService.getToken(), book);
        result.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.body().isSuccess()) {
                    Log.e(TAG, "Berhasil Add buku");
                } else {
                    Log.e(TAG, "Gagal add buku" + response.body().toString());
                    Intent mainIntent = new Intent(getActivity(), BookActivity.class);
                    startActivity(mainIntent);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSend:
                if (judul.getText().toString().length() == 0) {
                    judul.setError("Harus disi");
                } else if (penerbit.getText().toString().length() == 0) {
                    penerbit.setError("Masukkan Penerbit");
                } else if (penulis.getText().toString().length() == 0) {
                    penulis.setError("Masukkan nama penulis");
                } else if (harga.getText().toString().length() == 0) {
                    harga.setError("Masukkan Harga Buku");
                } else if (tahun.getText().toString().length() == 0) {
                    tahun.setError("Masukkan Tahun Terbit");
                } else {
                    InsertBook(judul.getText().toString(),
                            penulis.getText().toString(),
                            penerbit.getText().toString(),
                            harga.getText().toString(),
                            tahun.getText().toString());

                    Toast.makeText(getActivity(), "Insert success", Toast.LENGTH_SHORT).show();
                    break;
                }
        }
        switch (view.getId()) {
            case R.id.btnaddimage:
                addImage();
                Toast.makeText(getActivity(), "Success Input Book", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}