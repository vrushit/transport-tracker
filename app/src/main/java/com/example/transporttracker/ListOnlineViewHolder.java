package com.example.transporttracker;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListOnlineViewHolder extends RecyclerView.ViewHolder {

    public TextView textEmail;

    public ListOnlineViewHolder(@NonNull View itemView) {
        super(itemView);

        textEmail = (TextView)itemView.findViewById(R.id.text_email);
    }
}
