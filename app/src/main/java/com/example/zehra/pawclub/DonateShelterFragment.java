package com.example.zehra.pawclub;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class DonateShelterFragment extends Fragment {

    String[] il = {
            "İl Seçiniz","Istanbul", "Ankara",
            "Izmir", "Adana", "Adıyaman", "AfyonKarahisar", "Ağrı", "Aksaray",
            "Amasya", "Antalya", "Ardahan", "Artvin", "Aydın", "Balıkesir",
            "Bartın", "Batman", "Bayburt", "Bilecik", "Bingöl", "Bitlis",
            "Bolu", "Burdur", "Bursa", "Çanakkale", "Çankırı", "Çorum",
            "Denizli", "Diyarbakır", "Düzce", "Edirne", "Elazığ", "Erzincan",
            "Erzurum", "Eskişehir", "Gaziantep", "Giresun", "Gümüşhane",
            "Hakkari", "Hatay", "Iğdır", "Isparta", "Kahramanmaraş", "Karabük",
            "Karaman", "Kars", "Kastamonu", "Kayseri", "Kırıkkale",
            "Kırklareli", "Kırşehir", "Kilis", "Kocaeli", "Konya", "Kütahya",
            "Malatya", "Manisa", "Mardin", "Mersin", "Muğla", "Muş",
            "Nevşehir", "Niğde", "Ordu", "Osmaniye", "Rize", "Sakarya",
            "Samsun", "Siirt", "Sinop", "Sivas", "Şırnak", "Tekirdağ", "Tokat",
            "Trabzon", "Tunceli", "Şanlıurfa", "Uşak", "Van", "Yalova",
            "Yozgat", "Zonguldak"
    };
    Gson gson;
    SpinnerDataClass spinnerClass;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.donate_shelter_fragment,container,false);
        Spinner spinner = view.findViewById(R.id.Donate_Shelter_Spinner);
        spinnerClass = new SpinnerDataClass();
        String value = Get("https://pawclub.appspot.com/ShelterListCountry");
        gson = new Gson();
        spinnerClass = gson.fromJson(value,SpinnerDataClass.class);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),R.layout.donate_shelter_item,R.id.Donate_Shelter_TV,il);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("il",il[position]);


                if(position == 0){
                    

                }else{

                    CountryListFragment fragment = new CountryListFragment();
                    fragment.setArguments(bundle);
                    getActivity().getFragmentManager().beginTransaction().replace(R.id.Donate_Shelter_fragment,fragment).commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
