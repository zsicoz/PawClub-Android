package com.example.zehra.pawclub;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserPostFragment extends Fragment {
    ArrayList<UserPostItemClass> arrayList;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    UserPostAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Post");
        View view = inflater.inflate(R.layout.user_post_fragment,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.user_post_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        arrayList = getPost();
        adapter = new UserPostAdapter(getActivity(),arrayList);
        recyclerView.setAdapter(adapter);
        return view;
    }
    public ArrayList getPost(){
        ArrayList arrayList = new ArrayList();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    UserPostItemClass postItemClass = data.getValue(UserPostItemClass.class);
                    arrayList.add(postItemClass);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return  arrayList;
    }
}
