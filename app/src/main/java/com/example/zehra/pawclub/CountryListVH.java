package com.example.zehra.pawclub;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class CountryListVH extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
    public TextView ShelterName;
    public TextView ShelterInfo;
    public TextView ShelterAddress;
    public CardView ShelterBtn;

    ItemClickListener itemClickListener;

    public CountryListVH(View itemView) {
        super(itemView);
        this.ShelterName = itemView.findViewById(R.id.CLI_ShelterName);
        this.ShelterInfo = itemView.findViewById(R.id.CLI_ShelterInfo);
        this.ShelterAddress = itemView.findViewById(R.id.CLI_ShelterAddress);
        this.ShelterBtn = itemView.findViewById(R.id.Country_List_Item);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),true);
        return true;
    }
}
