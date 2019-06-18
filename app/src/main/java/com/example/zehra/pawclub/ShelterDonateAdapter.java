package com.example.zehra.pawclub;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ShelterDonateAdapter extends RecyclerView.Adapter<DonateListVH> {

    private ArrayList<DonateClass> arrayList;
    private Context context;
    private Dialog dialog;
    private TextView DonateInfoTV;
    private EditText DonateInfoET;

    public ShelterDonateAdapter(Context context, ArrayList arrayList){
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
        DonateClass donateClass = arrayList.get(i);

        donateListVH.Donate_Shelter_Name.setVisibility(View.INVISIBLE);
        donateListVH.DonateShelterTV.setVisibility(View.INVISIBLE);
        donateListVH.Donate_Date.setText(donateClass.Donate_Date.replace("T00:00:00.000Z",""));
        donateListVH.Donate_Shelter_Name.setText(donateClass.Shelter_Name);
        donateListVH.Donate_Pay.setText(Integer.toString(donateClass.Donate_Pay));
        donateListVH.Donate_Username.setText(donateClass.NameAndSurname);
        donateListVH.Donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(context);
                dialog.setContentView(R.layout.shelter_donate_info);
                DonateInfoET = dialog.findViewById(R.id.Donate_Info_ET);
                DonateInfoTV = dialog.findViewById(R.id.Donate_Info_TV);
                DonateInfoTV.setText(donateClass.NameAndSurname + "  Adlı Kullanıcının Bağışı");
                dialog.findViewById(R.id.DonateInfoBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Info = DonateInfoET.getText().toString();
                        String value = Post("https://pawclub.appspot.com/ShelterDonateInfo",String.valueOf(donateClass.id),Info);
                        Toast.makeText(context,"İşlem : "+value,Toast.LENGTH_SHORT).show();
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

    String Post(String url, String id , String Info) {
        Response response;
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("id",id)
                .add("Info", Info)
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
