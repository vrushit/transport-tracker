package com.example.transporttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Locate extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    RecyclerView listOnline;
    RecyclerView.LayoutManager layoutManager;

    DatabaseReference storesRef;


    FirebaseRecyclerAdapter<Place, Location> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);

        firebaseAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public  void  onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user == null){
                    Intent intent = new Intent(Locate.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        };

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarNav);
        toolbar.setTitle("Locations");
        setSupportActionBar(toolbar);

        listOnline = (RecyclerView)findViewById(R.id.storesOnline);
        listOnline.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listOnline.setLayoutManager(layoutManager);

        storesRef = FirebaseDatabase.getInstance().getReference().child("Stores/");
        
        setupSystem();
        
        updateList();
        
    }

    private void updateList() {

        FirebaseRecyclerOptions<Place> options =
                new FirebaseRecyclerOptions.Builder<Place>()
                        .setQuery(storesRef,Place.class)
                        .build();
        adapter = new FirebaseRecyclerAdapter<Place, Location>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Location holder, int position, @NonNull Place model) {

                holder.nameLocation.setText(model.getName());
                holder.phoneNo.setText(model.getPhoneNo());
                holder.address.setText(model.getAddress());

            }

            @NonNull
            @Override
            public Location onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.location_layout, parent, false);

                return new Location(view);

            }
        };

        listOnline.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    private void setupSystem() {

        storesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Place Name = postSnapshot.getValue(Place.class);
                    Log.d("Name: ", Name.getName());
                   // Log.d("Address: ", Name.getAddress());
                    Log.d("PhoneNo:", Name.getPhoneNo());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(adapter != null)
        {
            adapter.stopListening();

        }
    }
}