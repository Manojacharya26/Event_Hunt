package com.example.eventhunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminOrder extends AppCompatActivity {
    DatabaseReference databaseReference,databaseReference2,databaseReference3;
    ValueEventListener eventListener,eventListener2,eventListener3;
    RecyclerView recyclerView_event,recyclerView_artist,recyclerView_venue;
    List<BookEventDataClass> dataList;
    List<BookArtistDataClass> dataList2;
    List<BookVenueDataClass> dataList3;

    BookEventAdapter adapter;
    BookArtistAdapter adapter2;
    BookVenueAdapter adapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order);
        recyclerView_event =findViewById(R.id.recyclerEvent);
        recyclerView_artist =findViewById(R.id.recyclerArtist);
        recyclerView_venue =findViewById(R.id.recyclerVenue);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,true);
        recyclerView_event.setLayoutManager(linearLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(AdminOrder.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        dataList = new ArrayList<>();
        adapter = new BookEventAdapter(AdminOrder.this, dataList);
        recyclerView_event.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance().getReference("BookedEvent");
        dialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    BookEventDataClass dataClass = itemSnapshot.getValue(BookEventDataClass.class);
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

        venue();
    }
    public void artist(){

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView_artist.setLayoutManager(linearLayoutManager2);

     /*   AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();*/
        dataList2 = new ArrayList<>();


        adapter2 = new BookArtistAdapter(getApplicationContext(), dataList2);

        recyclerView_artist.setAdapter(adapter2);

        databaseReference2 = FirebaseDatabase.getInstance().getReference("BookedArtist");

        //   dialog.show();
        eventListener2 = databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList2.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    BookArtistDataClass dataClass = itemSnapshot.getValue(BookArtistDataClass.class);
                    dataClass.setKey(itemSnapshot.getKey());
                    dataList2.add(dataClass);
                }
                adapter2.notifyDataSetChanged();
                //       dialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //     dialog.dismiss();
            }
        });

    }
    public void venue(){

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView_venue.setLayoutManager(linearLayoutManager3);
     /*   AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();*/
        dataList3 = new ArrayList<>();


        adapter3 = new BookVenueAdapter(getApplicationContext(), dataList3);

        recyclerView_venue.setAdapter(adapter3);

        databaseReference3 = FirebaseDatabase.getInstance().getReference("BookedVenue");

        //  dialog.show();
        eventListener3 = databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList3.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    BookVenueDataClass dataClass = itemSnapshot.getValue(BookVenueDataClass.class);
                    dataClass.setKey(itemSnapshot.getKey());
                    dataList3.add(dataClass);
                }
                adapter3.notifyDataSetChanged();
                //       dialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //       dialog.dismiss();
            }
        });

    }

}
