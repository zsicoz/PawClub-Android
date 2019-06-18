package com.example.zehra.pawclub;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class AdminPostFragment extends Fragment {
    EditText PostHeaderET , PostTextET;
    ImageView PostImage;
    Button PostSendBtn;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    Uri ProfileUri;
    UserPostItemClass postItemClass;
    ProgressBar PostUploadProgress;


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.admin_post_page,container,false);

        PostHeaderET = view.findViewById(R.id.PostHeaderText);
        PostTextET = view.findViewById(R.id.PostBodyText);
        PostImage = view.findViewById(R.id.PostImage);
        PostSendBtn = view.findViewById(R.id.PostSendBtn);
        PostUploadProgress = view.findViewById(R.id.PostUploadProgress);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        postItemClass = new UserPostItemClass();

        PostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Bir Fotoğraf Seçin"), 1);
            }
        });

        PostSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostUploadProgress.setProgress(0);
                PostSendBtn.setVisibility(View.INVISIBLE);
                if("".equals(PostHeaderET.getText().toString()) || "".equals(PostTextET.getText().toString())){
                    Toast.makeText(getActivity(),"Paylaşım içerikleri boş geçilemez",Toast.LENGTH_SHORT).show();
                    PostSendBtn.setVisibility(View.VISIBLE);
                }else{
                    postItemClass.PostID = databaseReference.push().getKey();
                    postItemClass.PostHeader = PostHeaderET.getText().toString();
                    postItemClass.postText = PostTextET.getText().toString();
                    if(ProfileUri != null){
                        storageReference.child("Post").child(postItemClass.PostID).putFile(ProfileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Uri> task) {
                                                Uri uri = task.getResult();
                                                postItemClass.PostImage = uri.toString();
                                                databaseReference.child("Post").child(postItemClass.PostID).setValue(postItemClass);
                                                Toast.makeText(getActivity(),"Paylaşım Yapıldı",Toast.LENGTH_SHORT).show();
                                                PostUploadProgress.setVisibility(View.INVISIBLE);
                                            }
                                        });
                                        PostSendBtn.setVisibility(View.VISIBLE);
                                    }

                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                PostUploadProgress.setVisibility(View.VISIBLE);
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                PostUploadProgress.setProgress((int) progress);
                            }
                        });
                    }else{
                        databaseReference.child("Post").child(postItemClass.PostID).setValue(postItemClass);
                        Toast.makeText(getActivity(),"Paylaşım Yapıldı",Toast.LENGTH_SHORT).show();
                        PostSendBtn.setVisibility(View.VISIBLE);
                    }
                }
            }
        });



        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK
                && data != null && data.getData() != null ){
            ProfileUri = data.getData();
            Picasso.get().load(data.getData()).placeholder(R.drawable.ic_person_black_24dp).into(PostImage);
        }

    }
}
