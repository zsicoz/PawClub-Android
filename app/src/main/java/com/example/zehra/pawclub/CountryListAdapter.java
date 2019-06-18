package com.example.zehra.pawclub;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CountryListAdapter extends RecyclerView.Adapter<CountryListVH> {

    Context context;
    ArrayList<CountryListItem> list;
    Dialog dialog;
    Button onay;
    EditText donate_expiry;
    EditText donate_value;
    EditText donate_name;
    EditText donate_surname;
    EditText donate_cardnumber;
    EditText donate_cvv;
    SharedPreferences sharedPreferences;
    UserClass userClass;
    Gson gson;
    DonateClass donateClass;
    public static String URL = "https://pawclub.appspot.com/UserDonateCreate";
    CountryListItem item;



    public CountryListAdapter(Context context, ArrayList<CountryListItem> list){
        this.context = context;
        this.list = list;
        sharedPreferences = context.getSharedPreferences("AppInfo",Context.MODE_PRIVATE);
        userClass = new UserClass();
        gson = new Gson();
        String value = sharedPreferences.getString("UserInfo","Null");
        userClass = gson.fromJson(value,UserClass.class);

    }

    @Override
    public CountryListVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.country_list_item,parent,false);
        CountryListVH countryListVH = new CountryListVH(view);

        return countryListVH;
    }

    @Override
    public void onBindViewHolder(CountryListVH holder, int position) {

        item = list.get(position);

        holder.ShelterName.setText(item.Shelter_Name);
        holder.ShelterInfo.setText(item.Shelter_Phone);
        holder.ShelterAddress.setText(item.Shelter_Address);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                dialog = new Dialog(context);
                dialog.setContentView(R.layout.credit_cart_dialog);
                onay = dialog.findViewById(R.id.OnayButton);
                donate_expiry = dialog.findViewById(R.id.Donate_expiry);
                donate_value = dialog.findViewById(R.id.Donate_value);
                donate_name = dialog.findViewById(R.id.Donate_Name);
                donate_surname = dialog.findViewById(R.id.Donate_Surname);
                donate_cardnumber = dialog.findViewById(R.id.Donate_CardNumber);
                donate_cvv = dialog.findViewById(R.id.Donate_CVV);

                donate_expiry.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (editable.length() > 0 && (editable.length() % 3) == 0) {
                            final char c = editable.charAt(editable.length() - 1);
                            if ('/' == c) {
                                editable.delete(editable.length() - 1, editable.length());
                            }
                        }
                        if (editable.length() > 0 && (editable.length() % 3) == 0) {
                            char c = editable.charAt(editable.length() - 1);
                            if (Character.isDigit(c) && TextUtils.split(editable.toString(), "/").length <= 2) {
                                editable.insert(editable.length() - 1, "/");
                            }
                        }
                    }
                });
                onay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(
                                "".equals(donate_value.getText().toString()) ||
                                        "".equals(donate_name.getText().toString()) ||
                                        "".equals(donate_surname.getText().toString()) ||
                                        "".equals(donate_cardnumber.getText().toString()) ||
                                        "".equals(donate_cvv.getText().toString()) ||
                                        "".equals(donate_expiry.getText().toString())

                        ){
                            Toast
                                    .makeText(
                                            context,
                                            "Alanların Doldurulması Zorunludur",
                                            Toast.LENGTH_SHORT
                                    )
                                    .show();
                        }
                        else
                        {
                            String value = post(URL,list.get(position).id,String.valueOf(userClass.getId()),donate_value.getText().toString());

                            if(value == "Tamamlandı"){
                                Toast.makeText(context,value,Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }else {
                                Log.wtf("Donate_System",value);
                                Toast.makeText(context,value,Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                    }
                });
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    String post(String url ,String ShelterID, String UserID , String DonateValue ) {
        Response response;
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("Shelter_id",ShelterID)
                .add("User_id", UserID)
                .add("Donate_pay",DonateValue)
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
