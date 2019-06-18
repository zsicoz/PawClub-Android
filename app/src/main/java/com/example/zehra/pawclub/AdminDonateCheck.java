package com.example.zehra.pawclub;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AdminDonateCheck extends Fragment {

    RecyclerView recyclerView;
    DonateList donateList;
    Gson gson;
    AdminDonateListAdapter adminDonateListAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.admin_donate_check,container,false);

        gson = new Gson();
        recyclerView = view.findViewById(R.id.Admin_Donate_list_Recyclerview);
        donateList = new DonateList();
        String value = Get("https://pawclub.appspot.com/AdminDonateList");
        donateList = gson.fromJson(value,DonateList.class);
        adminDonateListAdapter = new AdminDonateListAdapter(getActivity(),donateList.DonateArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adminDonateListAdapter);



        return view;
    }

    String Get(String url) {
        Response response;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            response  = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
