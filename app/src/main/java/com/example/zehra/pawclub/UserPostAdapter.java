package com.example.zehra.pawclub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserPostAdapter extends RecyclerView.Adapter<UserPostVH> {
    ArrayList<UserPostItemClass> arrayList;
    Context context;
    public UserPostAdapter(Context context, ArrayList arrayList){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserPostVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_post_item,viewGroup,false);
        UserPostVH vh = new UserPostVH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull UserPostVH userPostVH, int i) {

        UserPostItemClass item = arrayList.get(i);
        userPostVH.PostUserName.setText("PawClub");
        userPostVH.PostText.setText(item.postText);
        userPostVH.PostHeader.setText(item.PostHeader);
        Picasso
                .get()
                .load(R.drawable.logo)
                .into(userPostVH.UserPostImage);
        Picasso
                .get()
                .load(item.PostImage)
                .placeholder(R.drawable.ic_person_black_24dp)
                .into(userPostVH.PostImage);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
