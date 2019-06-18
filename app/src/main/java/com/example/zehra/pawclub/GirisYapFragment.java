package com.example.zehra.pawclub;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GirisYapFragment extends Fragment {
    UserClass user;
    Gson gson;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.girisfragment,container,false);

        final EditText KAdiet = view.findViewById(R.id.girisKadi);
        final EditText Parolaet = view.findViewById(R.id.girisParola);
        final CheckBox Remember =view.findViewById(R.id.rememberMe);
        ProgressBar progressBar = view.findViewById(R.id.girisProgress);
        gson = new Gson();
        sharedPreferences = getActivity().getSharedPreferences("AppInfo", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();



        view.findViewById(R.id.girisButon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                String KAdi = KAdiet.getText().toString();
                String Parola = Parolaet.getText().toString();

                String response = Get("https://pawclub.appspot.com/UserCheck/"+KAdi+"/"+Parola);
                user = gson.fromJson(response,UserClass.class);
                editor.putString("UserInfo",response);

                if(KAdi.equals("") || Parola.equals("")){
                    Toast.makeText(getActivity(),"Kullanıcı Adı Yada Şifre Boş Bırakılamaz",Toast.LENGTH_SHORT).show();
                }else if (response.equals("")){
                    Toast.makeText(getActivity(),"Böyle bir Kullanıcı bulunamamktadır",Toast.LENGTH_SHORT).show();
                }else if(!KAdi.equals(user.getUsername()) || !Parola.equals(user.getPassword())){
                    Toast.makeText(getActivity(),"Yanlış Kullanıcı Adı Veya Şifresi Denemesi",Toast.LENGTH_SHORT).show();
                }else{
                    if(Remember.isChecked()){
                        Toast.makeText(getActivity(),"Beni Hatırla Seçili ve Giriş Yapıldı",Toast.LENGTH_SHORT).show();
                        editor.putBoolean("RememberMe",true);
                    }else {
                        Toast.makeText(getActivity(), "Beni Hatırla Seçili değil Giriş Yapıldı", Toast.LENGTH_SHORT).show();
                        editor.putBoolean("RememberMe",false);
                    }
                    editor.commit();



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

                progressBar.setVisibility(View.INVISIBLE);
            }
        });


        view.findViewById(R.id.girisRecovery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MainPage_Taslak.class);
                startActivity(intent);
            }
        });


        return view;
    }

    String Get(String url) {
        Response response;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
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
