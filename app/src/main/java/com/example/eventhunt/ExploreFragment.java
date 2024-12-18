package com.example.eventhunt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {

    DatabaseReference databaseReference,databaseReference2;
    ValueEventListener eventListener,eventListener2;
    RecyclerView recyclerView_venue,recyclerView_artist;
    List<VenueDataClass> dataList;
    List<ArtistDataClass> dataList2;

    VenueAdapter adapter;
    ArtistAdapter adapter2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_explore, container, false);
        recyclerView_venue =v.findViewById(R.id.recyclerView_venue);
        recyclerView_artist =v.findViewById(R.id.recyclerView_artist);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);

        recyclerView_venue.setLayoutManager(linearLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        dataList = new ArrayList<>();


        adapter = new VenueAdapter(getContext(), dataList);

        recyclerView_venue.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Venue");

        dialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    VenueDataClass dataClass = itemSnapshot.getValue(VenueDataClass.class);
                    dataClass.setKey(itemSnapshot.getKey());
                    dataList.add(dataClass);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });
        artist();


        return v;
    }
    public void artist(){
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView_artist.setLayoutManager(linearLayoutManager2);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        dataList2 = new ArrayList<>();


        adapter2 = new ArtistAdapter(getContext(), dataList2);

        recyclerView_artist.setAdapter(adapter2);

        databaseReference2 = FirebaseDatabase.getInstance().getReference("Artist");

        dialog.show();
        eventListener2 = databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList2.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    ArtistDataClass dataClass = itemSnapshot.getValue(ArtistDataClass.class);
                    dataClass.setKey(itemSnapshot.getKey());
                    dataList2.add(dataClass);
                }
                adapter2.notifyDataSetChanged();
                dialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

    }

}