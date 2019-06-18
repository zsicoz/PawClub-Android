package com.example.zehra.pawclub;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;


public class MainPage_Taslak extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    SharedPreferences sharedPreferences;
    String CurrentUser;
    UserClass user;
    Gson gson;
    TextView UserNameSurname;
    TextView UserInfo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage_taslak);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        LinearLayout navHeader=(LinearLayout) LayoutInflater.from(this).inflate(R.layout.nav_header, null);
        navigationView.addHeaderView(navHeader);

        UserNameSurname = (TextView) navHeader.findViewById(R.id.Nav_Header_UserName);
        UserInfo = (TextView) navHeader.findViewById(R.id.Nav_Header_Info);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.DrawerLayout);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

        gson = new Gson();
        sharedPreferences = getSharedPreferences("AppInfo",MODE_PRIVATE);
        CurrentUser = sharedPreferences.getString("UserInfo","Null");
        user = gson.fromJson(CurrentUser,UserClass.class);
        UserNameSurname.setText(user.getNameSurname().replace("-"," "));
        UserInfo.setText(user.getEmail());

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.MainPageFrame,
                        new FragmentA()).commit();
                break;
            case R.id.nav_donate:
                getSupportFragmentManager().beginTransaction().replace(R.id.MainPageFrame,
                        new FragmentB()).commit();
                break;
            case R.id.nav_settings:
                Toast.makeText(this, "Ayarlar'a basıldı", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_exit:
                Toast.makeText(this, "Çıkış Yapıldı", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainPage_Taslak.this,MainActivity.class);
                startActivity(intent);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
