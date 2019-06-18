package com.example.zehra.pawclub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;


public class FragmentA extends Fragment {
    CircleImageView ProfileImage;
    Button SendBtn;
    private StorageReference mStorageRef;
    EditText NameAndSurNameET;
    EditText UserNameET;
    EditText EmailET;
    EditText PasswordET;
    Uri ProfileUri;
    UserClass userClass;
    String response;
    SharedPreferences sharedPreferences;
    FirebaseAuth auth;
    Gson gson;
    String OldUserName;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragmenta, container, false);

        ProfileImage = view.findViewById(R.id.ProfileImage);
        SendBtn = view.findViewById(R.id.SendBtn);
        NameAndSurNameET = view.findViewById(R.id.NameAndSurnameET);
        UserNameET = view.findViewById(R.id.UserNameET);
        EmailET = view.findViewById(R.id.EmailET);
        PasswordET = view.findViewById(R.id.PassET);
        userClass = new UserClass();
        gson = new Gson();
        auth = FirebaseAuth.getInstance();
        progressBar = view.findViewById(R.id.UploadProgress);

        auth.signInAnonymously().isSuccessful();

        sharedPreferences = getActivity().getSharedPreferences("AppInfo", Context.MODE_PRIVATE);
        String value = sharedPreferences.getString("UserInfo","Null");
        userClass = gson.fromJson(value,UserClass.class);
        OldUserName = userClass.getUsername();
        mStorageRef = FirebaseStorage.getInstance().getReference("ProfilePhoto").child(userClass.getUsername());


        Picasso
                .get()
                .load(userClass.getProfilePhoto())
                .placeholder(R.drawable.ic_person_black_24dp)
                .into(ProfileImage);
        NameAndSurNameET.setText(userClass.getNameSurname().replace("-"," "));
        UserNameET.setText(userClass.getUsername());
        EmailET.setText(userClass.getEmail());
        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Bir Fotoğraf Seçin"), 1);
            }
        });

        SendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClass.setNameSurname(NameAndSurNameET.getText().toString());
                userClass.setUsername(UserNameET.getText().toString());
                userClass.setEmail(EmailET.getText().toString());
                if(!PasswordET.getText().toString().equals("")){
                    userClass.setPassword(PasswordET.getText().toString());
                }
                if(ProfileUri != null){
                    progressBar.setProgress(0);
                    SendBtn.setVisibility(View.INVISIBLE);
                    mStorageRef.putFile(ProfileUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    mStorageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            Uri uri = task.getResult();
                                            userClass.setProfilePhoto(uri.toString());
                                            Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.INVISIBLE);
                                            Log.wtf("UploadImageError", response);

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.wtf("UploadImageError", e.getMessage());
                                        }
                                    });
                                }
                            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.VISIBLE);
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    });
                    response = Post("https://pawclub.appspot.com/UserUpdateInfo", userClass);
                    Toast.makeText(getActivity(),"Güncellendi",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    SendBtn.setVisibility(View.VISIBLE);
                }else {
                    progressBar.setProgress(0);
                    SendBtn.setVisibility(View.INVISIBLE);
                    response = Post("https://pawclub.appspot.com/UserUpdateInfo", userClass);
                    Toast.makeText(getActivity(),"Güncellendi",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    SendBtn.setVisibility(View.VISIBLE);
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
            Picasso.get().load(data.getData()).placeholder(R.drawable.ic_person_black_24dp).into(ProfileImage);
        }

    }

    String Post(String url, UserClass user) {
        Response response;
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("name_surname", user.getNameSurname().replace(" ","-"))
                .add("email", user.getEmail())
                .add("password", user.getPassword())
                .add("username", user.getUsername())
                .add("oldusername",OldUserName)
                .add("profilephoto",user.getProfilePhoto())
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