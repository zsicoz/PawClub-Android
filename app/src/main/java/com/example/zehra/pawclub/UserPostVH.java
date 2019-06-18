package com.example.zehra.pawclub;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserPostVH extends RecyclerView.ViewHolder {
    CircleImageView UserPostImage;
    TextView PostText;
    TextView PostHeader;
    TextView PostUserName;
    ImageView PostImage;
    public UserPostVH(@NonNull View itemView) {
        super(itemView);
        this.PostImage = itemView.findViewById(R.id.user_post_item_postImage);
        this.UserPostImage = itemView.findViewById(R.id.user_post_item_userImage);
        this.PostText = itemView.findViewById(R.id.user_post_item_postText);
        this.PostUserName = itemView.findViewById(R.id.user_post_item_userName);
        this.PostHeader = itemView.findViewById(R.id.user_post_item_postHeader);

    }
}
