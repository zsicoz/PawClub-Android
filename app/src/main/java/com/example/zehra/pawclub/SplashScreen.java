package com.example.zehra.pawclub;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.internal.Objects;
import com.google.gson.Gson;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ImageView gif = findViewById(R.id.SplashScreenGif);
        Glide.with(this).asGif().load(R.drawable.splash).into(gif);
        isInternetCheck();
    }

    public void isInternetCheck(){
        if (isInternetOn()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    SharedPreferences sharedPreferences = getSharedPreferences("AppInfo",MODE_PRIVATE);

                    if (sharedPreferences.getBoolean("RememberMe",false)){

                        Gson gson = new Gson();
                        String UserInfo = sharedPreferences.getString("UserInfo","Null");
                        UserClass userClass = gson.fromJson(UserInfo,UserClass.class);
                        switch (userClass.getUserType()){
                            case "U":
                                Intent user = new Intent(SplashScreen.this,MainPage_User.class);
                                startActivity(user);
                                break;
                            case "S":
                                Intent shelter = new Intent(SplashScreen.this,MainPage_Shelter.class);
                                startActivity(shelter);
                                break;
                            case "A":
                                Intent admin = new Intent(SplashScreen.this,MainPage_Admin.class);
                                startActivity(admin);
                                break;
                            default:
                                Toast
                                        .makeText(
                                                SplashScreen.this,
                                                "Kullanıcı Türü Hatası Uygulamayı Tekrardan Yükleyin",
                                                Toast.LENGTH_SHORT)
                                        .show();
                                break;

                        }
                    }else{
                        Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                        startActivity(intent);
                    }

                }
            },1000);
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreen.this);
            builder
                    .setTitle("Olamaz!")
                    .setMessage("İnternet bağlantısı olmayan bir cihaz mı kullanıyorsun ? Çok şey kaçırıyorsun dostum :)")
                    .setCancelable(false)
                    .setNegativeButton("Bana 1 Saniye Ver Hemen Halledicem", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    isInternetCheck();
                                }
                            },1000);
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
    }

    public final boolean isInternetOn() {

        ConnectivityManager connec =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if ( connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED )
        {
            return true;
        } else if ( connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED  )
        {

            return false;
        }
        return false;
    }
}
