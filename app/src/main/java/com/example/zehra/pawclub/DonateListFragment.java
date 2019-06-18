package com.example.zehra.pawclub;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class DonateListFragment extends Fragment {
    Gson gson;
    DonateList donateList;
    DonateListAdapter adapter;
    SharedPreferences sharedPreferences;
    UserClass userClass;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.user_donatelist,container,false);

                recyclerView = view.findViewById(R.id.Donate_list_Recyclerview);
                gson = new Gson();
                donateList = new DonateList();
                sharedPreferences = getActivity().getSharedPreferences("AppInfo", Context.MODE_PRIVATE);
                userClass = new UserClass();
                userClass = gson.fromJson(sharedPreferences.getString("UserInfo","Null"),UserClass.class);
                String value = Get("https://pawclub.appspot.com/UserDonateList/"+userClass.getId());
                donateList = gson.fromJson(value,DonateList.class);
                adapter = new DonateListAdapter(getActivity(),donateList.DonateArrayList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);

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
