package com.example.zehra.pawclub;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CountryListFragment extends Fragment {
    private CountryListSpinnerClass spinnerClass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.country_list_fragment,container,false);

        ArrayList<CountryListItem> arrayList = new ArrayList<>();
        String il = getArguments().getString("il");
        String value = Get("https://pawclub.appspot.com/ShelterList/"+il);
        Gson gson = new Gson();
        spinnerClass = new CountryListSpinnerClass();
        spinnerClass = gson.fromJson(value,CountryListSpinnerClass.class);
        RecyclerView recyclerView = view.findViewById(R.id.Country_List_Recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        CountryListAdapter countryListAdapter = new CountryListAdapter(getActivity(),spinnerClass.ShelterList);
        recyclerView.setAdapter(countryListAdapter);

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
