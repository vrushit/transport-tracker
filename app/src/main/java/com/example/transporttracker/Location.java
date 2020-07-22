package com.example.transporttracker;

import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Location extends RecyclerView.ViewHolder {

    public TextView nameLocation;
    public TextView address;
    public TextView phoneNo;


    public Location(@NonNull View itemView) {
        super(itemView);

        nameLocation = (TextView)itemView.findViewById(R.id.LocationName);
        address = (TextView)itemView.findViewById(R.id.address);
        phoneNo = (TextView)itemView.findViewById(R.id.phoneNumber);


    }
}
