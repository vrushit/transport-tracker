package com.example.transporttracker_new;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Location extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView nameLocation;
    public TextView address;
    public TextView phoneNo;

    ItemClickListener itemClickListener;

    public Location(@NonNull View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);

        nameLocation = (TextView)itemView.findViewById(R.id.LocationName);
        address = (TextView)itemView.findViewById(R.id.address);
        phoneNo = (TextView)itemView.findViewById(R.id.phoneNumber);


    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition());
    }
}
