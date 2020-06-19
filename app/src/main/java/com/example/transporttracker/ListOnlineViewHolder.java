package com.example.transporttracker;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListOnlineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView textEmail;

    ItemClickListener itemClickListener;


    public ListOnlineViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        textEmail = (TextView)itemView.findViewById(R.id.text_email);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition());
    }
}
