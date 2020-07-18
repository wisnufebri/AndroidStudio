package com.belajar.loginapps.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.belajar.loginapps.R;
import com.belajar.loginapps.apihelper.ApiResponse;
import com.belajar.loginapps.apihelper.AppService;
import com.belajar.loginapps.apihelper.BookApiService;
import com.belajar.loginapps.apihelper.RetrofitClient;
import com.belajar.loginapps.dummy.DialogUtility;
import com.belajar.loginapps.model.Book;
import com.belajar.loginapps.model.BookResult;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.belajar.loginapps.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ViewHomeFragment extends Fragment {

    private View view;
    private Retrofit retrofit;
    private String TAG = "viewfragment";

    private ImageView imageThumb;
    private TextInputEditText inputJudul, inputPenerbit, inputTahun, inputPenulis, inputHarga;
    private MaterialButton btnUpdate, btnDelete, btnSave, btnCancel, btnUpload;
    private AlertDialog dlg;
    private LinearLayout layoutAction, layoutUpdate;
    public static final int PICK_IMAGE = 1;
    private String base64Image = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_book_fragment, container, false);

        getData();
        initView();

        return view;
    }

    private void initView() {
        inputJudul = view.findViewById(R.id.inputJudul);
        inputPenerbit = view.findViewById(R.id.inputPenerbit);
        inputTahun = view.findViewById(R.id.inputTahun);
        inputPenulis = view.findViewById(R.id.inputPenulis);
        inputHarga = view.findViewById(R.id.inputHarga);
        imageThumb = view.findViewById(R.id.imageThumb);

        btnDelete = view.findViewById(R.id.btnDelete);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnSave = view.findViewById(R.id.btnSave);
        btnUpload = view.findViewById(R.id.btnUpload);

        layoutAction = view.findViewById(R.id.layoutAction);
        layoutUpdate = view.findViewById(R.id.layoutUpdate);


        btnUpdate.setOnClickListener(view1 -> {
            setFormDisable(true);
            layoutUpdate.setVisibility(View.VISIBLE);
            layoutAction.setVisibility(View.GONE);
            btnUpload.setVisibility(View.VISIBLE);
        });

        btnDelete.setOnClickListener(view1 -> {
            shoConfirmDialog(R.raw.stay_save, "Delete Data Buku Ini..?", getContext());
        });

        btnSave.setOnClickListener(view1 -> {
            Log.e(TAG, "save ");
            sendData(inputJudul.getText().toString(), inputPenulis.getText().toString(), inputPenerbit.getText().toString(), inputTahun.getText().toString(), inputHarga.getText().toString());
        });

        btnCancel.setOnClickListener(view1 -> {
            setFormDisable(false);
            layoutUpdate.setVisibility(View.GONE);
            layoutAction.setVisibility(View.VISIBLE);
            btnUpload.setVisibility(View.GONE);
        });

        btnUpload.setOnClickListener(view1 -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        });
    }

    private void getData() {

        DialogUtility.showDialog(R.raw.stay_save, "Loading : Get Data Buku", getContext());
        retrofit = RetrofitClient.getClient();

        BookApiService apiService = retrofit.create(BookApiService.class);
        Call<BookResult> result = apiService.getBookById(AppService.getToken(), AppService.getIdBuku());
        result.enqueue(new Callback<BookResult>() {
            @Override
            public void onResponse(Call<BookResult> call, Response<BookResult> response) {

                DialogUtility.closeAllDialog();
                if (response.body().getSuccess()) {
                    String judul = response.body().getRecord().getJudul() != null ? response.body().getRecord().getJudul() : "";
                    String penulis = response.body().getRecord().getPenulis() != null ? response.body().getRecord().getPenulis() : "";
                    String penerbit = response.body().getRecord().getPenerbit() != null ? response.body().getRecord().getPenerbit() : "";
                    String tahun = response.body().getRecord().getTahun() != null ? response.body().getRecord().getTahun() : "";
                    String harga = response.body().getRecord().getHarga() > 0 ? String.valueOf(response.body().getRecord().getHarga()) : "";

                    Log.e(TAG, "judul: " + judul);
                    Log.e(TAG, "penulis: " + penulis);
                    Log.e(TAG, "penerbit: " + penerbit);
                    Log.e(TAG, "tahun: " + tahun);
                    Log.e(TAG, "harga: " + harga);

                    inputJudul.setText(judul);
                    inputPenulis.setText(penulis);
                    inputPenerbit.setText(penerbit);
                    inputTahun.setText(tahun);
                    inputHarga.setText(harga);

                    setImageThumb(response.body().getRecord().getThumb());
                } else {
                    DialogUtility.showCustomDialog(R.raw.error, "Error : " + response.body().getMessage(), getContext());
                }
            }

            @Override
            public void onFailure(Call<BookResult> call, Throwable t) {
                t.printStackTrace();
                DialogUtility.closeAllDialog();
                DialogUtility.showCustomDialog(R.raw.error, "Error : " + t.getMessage(), getContext());
            }
        });
    }

    private void setFormDisable(boolean value) {
        inputJudul.setEnabled(value);
        inputTahun.setEnabled(value);
        inputHarga.setEnabled(value);
        inputPenerbit.setEnabled(value);
        inputPenulis.setEnabled(value);
    }

    private Bitmap setImageThumb(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageThumb.setImageBitmap(decodedByte);
        return decodedByte;
    }

    public void shoConfirmDialog(int id, String message, Context context) {
        LayoutInflater factory = LayoutInflater.from(context);
        final View viewDialog = factory.inflate(R.layout.dialog_confirmation_custom, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        dlg = alertDialog.create();

        TextView txtPromptMessage = viewDialog.findViewById(R.id.txtPromptMessage);
        MaterialButton btnPromptOk = viewDialog.findViewById(R.id.btnPromptOk);
        MaterialButton btnCancel = viewDialog.findViewById(R.id.btnCancel);
        LottieAnimationView lottieAnimationView = viewDialog.findViewById(R.id.animation_view);

        lottieAnimationView.setAnimation(id);
        txtPromptMessage.setText(message);

        btnPromptOk.setOnClickListener(view1 -> {
            Log.e("TAG", "delete");
            deleteBuku();
            dlg.dismiss();
        });

        btnCancel.setOnClickListener(view1 -> {
            Log.e("TAG", "cancel: ");
            dlg.dismiss();
        });

        dlg.setView(viewDialog);
        dlg.setCanceledOnTouchOutside(false);
        dlg.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dlg.show();

    }

    private void deleteBuku() {
        BookApiService apiService = retrofit.create(BookApiService.class);
        Call<ApiResponse> result = apiService.deleteBook(AppService.getToken(), AppService.getIdBuku());
        result.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Log.e(TAG, "onResponse: " + response.body().toString());
                dlg.dismiss();

                if (response.body().isSuccess()) {
                    showConfirmResult(R.raw.success, "Success Delete Data", getContext());
                    layoutUpdate.setVisibility(View.GONE);
                    layoutAction.setVisibility(View.VISIBLE);
                    btnUpload.setVisibility(View.GONE);
                } else {
                    DialogUtility.showCustomDialog(R.raw.error, "Error :" + response.body().getMessage(), getContext());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                t.printStackTrace();
                DialogUtility.closeAllDialog();
                DialogUtility.showCustomDialog(R.raw.error, "Error : " + t.getMessage(), getContext());
            }
        });
    }

    public void showConfirmResult(int id, String message, Context context) {
        LayoutInflater factory = LayoutInflater.from(context);
        final View viewDialog = factory.inflate(R.layout.dialog_custom, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        dlg = alertDialog.create();

        TextView txtPromptMessage = viewDialog.findViewById(R.id.txtPromptMessage);
        Button btnPromptOk = viewDialog.findViewById(R.id.btnPromptOk);
        LottieAnimationView lottieAnimationView = viewDialog.findViewById(R.id.animation_view);

        lottieAnimationView.setAnimation(id);
        txtPromptMessage.setText(message);

        btnPromptOk.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openHomeFragment();
            dlg.dismiss();
        });

        dlg.setView(viewDialog);
        dlg.setCanceledOnTouchOutside(false);
        dlg.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dlg.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            Uri uri = data.getData();
            imageThumb.setImageURI(uri);
            InputStream imageStream;
            String encodedImage = "";

            imageThumb.getLayoutParams().height = 400;
            imageThumb.getLayoutParams().width = 300;

            try {
                imageStream = getContext().getContentResolver().openInputStream(uri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                encodedImage = encodedImage + encodeImage(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            base64Image = encodedImage;
        }
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }

    private void sendData(String judul, String penulis, String penerbit, String tahun, String harga) {

        DialogUtility.showDialog(R.raw.paperplane, "Loading", getContext());

        Book book = new Book();
        book.setHarga(Integer.valueOf(harga));
        book.setId(AppService.getIdBuku());
        book.setJudul(judul);
        book.setPenulis(penulis);
        book.setPenerbit(penerbit);
        book.setTahun(tahun);
        book.setThumb(base64Image);

        Log.e(TAG, "sendData: " + book.toString());

        BookApiService apiService = retrofit.create(BookApiService.class);
        Call<ApiResponse> result = apiService.updateBook(AppService.getToken(), book);
        result.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                Log.e(TAG, "onResponse: " + response.body().toString());

                DialogUtility.closeAllDialog();

                if (response.body().isSuccess()) {
                    showConfirmResult(R.raw.success, "Success Edit Data", getContext());
                } else {
                    DialogUtility.showCustomDialog(R.raw.error, "Error :" + response.body().getMessage(), getContext());
                }

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                t.printStackTrace();
                DialogUtility.closeAllDialog();
                DialogUtility.showCustomDialog(R.raw.error, "Error : " + t.getMessage(), getContext());
            }
        });
    }


}