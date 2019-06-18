package com.example.zehra.pawclub;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AdminDonateListAdapter extends RecyclerView.Adapter<DonateListVH> {

    private ArrayList<DonateClass> arrayList;
    private Context context;
    DonateClass donateClass;


    public AdminDonateListAdapter(Context context, ArrayList arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public DonateListVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user_donate_item,viewGroup,false);
        DonateListVH donateListVH = new DonateListVH(view);
        return donateListVH;
    }

    @Override
    public void onBindViewHolder(@NonNull DonateListVH donateListVH, int i) {
        donateClass = arrayList.get(i);

        donateListVH.Donate_Username.setText(donateClass.NameAndSurname);
        donateListVH.Donate_Date.setText(donateClass.Donate_Date.replace("T00:00:00.000Z",""));
        donateListVH.Donate_Shelter_Name.setText(donateClass.Shelter_Name);
        donateListVH.Donate_Pay.setText(Integer.toString(donateClass.Donate_Pay));
        donateListVH.Donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Kurumun Açıklaması");
                dialog.setMessage(donateClass.Invoice);
                dialog.setNegativeButton("Onaylama", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value = Post("https://pawclub.appspot.com/AdminDonateInfo/",donateClass.id,"false");
                        Toast.makeText(context,"İşlem  : "+value,Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog.setPositiveButton("Onayla ve Dekont Gönder", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value = Post("https://pawclub.appspot.com/AdminDonateInfo/",donateClass.id,"true");
                        Toast.makeText(context,"İşlem  : "+value,Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    String Post(String url, int id , String value) {
        Response response;
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("id",String.valueOf(id))
                .add("success",value)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
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
