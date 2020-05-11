package com.example.transporttracker;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.function.DoubleUnaryOperator;

public class TrackerActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private String email;

    DatabaseReference locations;

    Double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Ref to Firebase
        locations = FirebaseDatabase.getInstance().getReference("Locations");



        //Get Intent
        if(getIntent() != null)
        {
            email = getIntent().getStringExtra("email");
            lat = getIntent().getDoubleExtra("lat",0);
            lng = getIntent().getDoubleExtra("lng",0);


        }
        if(!TextUtils.isEmpty(email))
        {
            loadLocationForThisUser(email);
        }

    }

    private void loadLocationForThisUser(String email) {
        Query user_location = locations.orderByChild("email").equalTo(email);
        user_location.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapShot: dataSnapshot.getChildren())
                {
                    Tracking tracking = postSnapShot.getValue(Tracking.class);

                    //Add Marker for car location

                    LatLng carLocation = new LatLng(Double.parseDouble(tracking.getLat()),
                            Double.parseDouble(tracking.getLng()));

                    Location currUser = new Location("");
                    currUser.setLatitude(lat);
                    currUser.setLongitude(lng);

                    Location car = new Location("");
                    car.setLatitude(Double.parseDouble(tracking.getLat()));
                    car.setLongitude(Double.parseDouble(tracking.getLng()));

                    //Function for calculating distance between locations
                    distance(currUser, car);

                    //Clear all old markers
                    mMap.clear();


                    //Add Car marker on Map
                    mMap.addMarker(new MarkerOptions()
                    .position(carLocation)
                    .title(tracking.getEmail())
                    .snippet("Distance "+new DecimalFormat("#.#").format((currUser.distanceTo(car))/ 1000)+ " km")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),12.0f));


                }

                //Marker for Current User

                LatLng current = new LatLng(lat,lng);
                mMap.addMarker(new MarkerOptions().position(current).title(FirebaseAuth.getInstance().getCurrentUser().getEmail()));



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private double distance(Location currentUser, Location car)
    {
        double theta = currentUser.getLongitude() - car.getLongitude();
        double dist = Math.sin(deg2rad(currentUser.getLatitude()))
                        * Math.sin(deg2rad(car.getLatitude()))
                        *Math.cos(deg2rad(currentUser.getLatitude()))
                        *Math.cos(deg2rad(car.getLatitude()))
                        *Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return(dist);
    }

    private double rad2deg(double rad) {
        return(rad * 180 / Math.PI);
    }

    private double deg2rad(double deg) {
        return (deg*Math.PI / 180.0);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
