package com.example.zehra.pawclub;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Switch sw = findViewById(R.id.MainActivity_Switch);
        final TextView TVkayit = findViewById(R.id.MainActivity_Switch);
        final TextView TVgiris = findViewById(R.id.MainActivity_Switch);

        getFragmentManager().beginTransaction().replace(R.id.frame, new KayitolFragment()).commit();

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    TVkayit.setVisibility(View.INVISIBLE);
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.frame,new GirisYapFragment());
                    ft.commit();
                    TVgiris.setVisibility(View.VISIBLE);


                }else{
                    TVgiris.setVisibility(View.INVISIBLE);
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.frame,new KayitolFragment());
                    ft.commit();
                    TVkayit.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
