package com.example.zehra.pawclub;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class KayitolFragment extends Fragment{
    TextView IsimSoyTV,UserNameTV,EmailTV,ParolaTV,ParolaTekrarTV;
    RadioGroup KayitRB;
    RadioButton KayitUser , KayitShelter;
    String URL = "https://pawclub.appspot.com";
    Gson gson;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.kayitfragment,container,false);
        IsimSoyTV = view.findViewById(R.id.KayitName_Surname);
        UserNameTV = view.findViewById(R.id.KayitUserName);
        EmailTV = view.findViewById(R.id.KayitEmail);
        ParolaTV = view.findViewById(R.id.KayitParola);
        ParolaTekrarTV = view.findViewById(R.id.KayitParolaTekrar);
        KayitRB = view.findViewById(R.id.KayitRadioBtn);
        KayitUser = view.findViewById(R.id.KayitUserRB);
        KayitShelter = view.findViewById(R.id.KayitShelterRB);
        gson = new Gson();
        editor = getActivity().getSharedPreferences("AppInfo", Context.MODE_PRIVATE).edit();


        view.findViewById(R.id.KayitButton).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                int secilenRadio = KayitRB.getCheckedRadioButtonId();
                String Type;

                switch(secilenRadio)
                {
                    case R.id.KayitUserRB:
                    {
                        Type = "U";
                        break;
                    }
                    case R.id.KayitShelterRB:
                    {
                        Type = "S";
                        break;
                    }
                    default:
                        Type = "N";
                        Toast.makeText(getActivity(),"Radio Button Hatası",Toast.LENGTH_SHORT).show();
                }
                String IsimSoyisim = IsimSoyTV.getText().toString();
                String UserName = UserNameTV.getText().toString();
                String Email = EmailTV.getText().toString();
                String Parola = ParolaTV.getText().toString();
                String ParolaTekrar = ParolaTekrarTV.getText().toString();
                if (IsimSoyisim.equals("") ||
                        UserName.equals("") ||
                        Email.equals("") ||
                        Parola.equals("") ||
                        ParolaTekrar.equals("") ||
                        Type.equals("N")
                ){
                    Toast.makeText(getActivity(),"Alanların Doldurulması Zorunludur!",Toast.LENGTH_SHORT).show();
                }else if (!Parola.equals(ParolaTekrar)){
                    Toast.makeText(getActivity(),"Parola Uyuşmuyor!",Toast.LENGTH_SHORT).show();
                }else{
                    UserClass user = new UserClass();
                    user.setNameSurname(IsimSoyisim);
                    user.setEmail(Email);
                    user.setUsername(UserName);
                    user.setPassword(Parola);
                    user.setUserType(Type);
                    String response = post(URL+"/"+"UserAdd",user);
                    Toast.makeText(getActivity(),response,Toast.LENGTH_SHORT).show();
                    String userString = gson.toJson(user);
                    editor.putString("UserInfo",userString).commit();

                    switch (user.getUserType()){
                        case "S":
                            Intent ShelterIntent = new Intent(getActivity(),MainPage_Shelter.class);
                            startActivity(ShelterIntent);
                            break;
                        case "U":
                            Intent UserIntent = new Intent(getActivity(),MainPage_User.class);
                            startActivity(UserIntent);
                            break;
                        case "A":
                            Intent AdminIntent = new Intent(getActivity(),MainPage_Admin.class);
                            startActivity(AdminIntent);
                            break;
                        default:
                            Intent intent = new Intent(getActivity(),getActivity().getClass());
                            startActivity(intent);
                            break;
                    }

                }


            }

        });




        return view;
    }


    String post(String url, UserClass user) {
        Response response;
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("name_surname", user.getNameSurname().replace(" ","-"))
                .add("email", user.getEmail())
                .add("password", user.getPassword())
                .add("user_type", user.getUserType())
                .add("username", user.getUsername())
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
