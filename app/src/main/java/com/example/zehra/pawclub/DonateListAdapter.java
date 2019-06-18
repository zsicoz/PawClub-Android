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

import java.util.ArrayList;


public class DonateListAdapter extends RecyclerView.Adapter<DonateListVH> {
    private ArrayList<DonateClass> arrayList;
    private Context context;


    public DonateListAdapter(Context context, ArrayList arrayList){
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

        donateListVH.DonateUserNameTV.setVisibility(View.INVISIBLE);
        donateListVH.Donate_Username.setVisibility(View.INVISIBLE);
        donateListVH.Donate_Date.setText(donateClass.Donate_Date.replace("T00:00:00.000Z",""));
        donateListVH.Donate_Shelter_Name.setText(donateClass.Shelter_Name);
        donateListVH.Donate_Pay.setText(Integer.toString(donateClass.Donate_Pay));
        donateListVH.Donate_Username.setText(donateClass.NameAndSurname);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
