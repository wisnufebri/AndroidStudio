package com.belajar.loginapps;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belajar.loginapps.adapter.BookAdapter;
import com.belajar.loginapps.adapter.MemberListAdapter;
import com.belajar.loginapps.apihelper.AppService;
import com.belajar.loginapps.apihelper.BookApiService;
import com.belajar.loginapps.apihelper.RetrofitClient;
import com.belajar.loginapps.model.Book;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BookHomeFragment<view> extends Fragment {

    private View view;
    private Retrofit retrofit;
    private String TAG = "homefragment";
    private RecyclerView listMember;
    private LinearLayoutManager linearLayoutManager;
    private MemberListAdapter memberListAdapter;
    protected Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.book_home_fragment, container, false);
        initRetrofit();
        getAllBookData();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
    }

    private void initRecyclerView() {
        listMember = view.findViewById(R.id.listMember);
        linearLayoutManager = new LinearLayoutManager(context);
        memberListAdapter = new MemberListAdapter();
        listMember.setLayoutManager(linearLayoutManager);
        listMember.setAdapter(memberListAdapter);
    }

    private void initRetrofit() {
        retrofit = RetrofitClient.getClient();
    }

    private void getAllBookData() {
        BookApiService apiService = retrofit.create(BookApiService.class);
        Call<List<Book>> result = apiService.getAllBuku(AppService.getToken());
        result.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<com.belajar.loginapps.model.Book>> call,
                                   Response<List<Book>> response) {
                addData(response.body());
                Log.e(TAG, "onResponse: " + response.body().toString());
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
            }
        });
    }

    private void addData(List<Book> data) {
        List<BookAdapter> bookAdapterList = new ArrayList<>();
        BookAdapter bookAdapter;
        for (Book books : data) {
            bookAdapter = new BookAdapter();
            bookAdapter.setId(books.getId());
            bookAdapter.setJudul(books.getJudul());
            bookAdapter.setPenulis(books.getPenulis());
            bookAdapter.setThumb(books.getThumb());
            bookAdapterList.add(bookAdapter);
        }
        memberListAdapter.addAll(bookAdapterList);
    }
}