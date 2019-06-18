package com.example.zehra.pawclub;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class DonateListVH extends RecyclerView.ViewHolder {
    TextView Donate_Username;
    TextView Donate_Date;
    TextView Donate_Shelter_Name;
    TextView Donate_Pay;
    CardView Donate;
    TextView DonateShelterTV;
    TextView DonateUserNameTV;

    public DonateListVH(@NonNull View itemView) {
        super(itemView);
        this.Donate_Date = itemView.findViewById(R.id.DLI_DonateDate);
        this.Donate_Pay = itemView.findViewById(R.id.DLI_DonateValue);
        this.Donate_Shelter_Name = itemView.findViewById(R.id.DLI_DonateShelter);
        this.Donate = itemView.findViewById(R.id.Donate_List_Item);
        this.Donate_Username = itemView.findViewById(R.id.DLI_DonateUsername);
        this.DonateShelterTV = itemView.findViewById(R.id.DLI_ShelterTV);
        this.DonateUserNameTV = itemView.findViewById(R.id.DLI_DonateUsernameTV);
    }
}
