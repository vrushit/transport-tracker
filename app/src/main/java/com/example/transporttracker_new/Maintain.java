package com.example.transporttracker_new;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class Maintain extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;

    ImageView carImage;

    CheckBox cb1;
    CheckBox cb2;
    CheckBox cb3;
    CheckBox cb4;
    CheckBox cb5;
    CheckBox cb6;
    CheckBox cb7;
    CheckBox cb8;
    CheckBox cbF;

    Button btnCost;

    TextView text1;
    EditText VinNo;

    int c1,c2,c3,c4,c5,c6,c7,c8,c9;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain);

        btnCost = (Button)findViewById(R.id.btn3);

        cb1 = (CheckBox)findViewById(R.id.checkBox1);
        cb2 = (CheckBox)findViewById(R.id.checkBox2);
        cb3 = (CheckBox)findViewById(R.id.checkBox3);
        cb4 = (CheckBox)findViewById(R.id.checkBox4);
        cb5 = (CheckBox)findViewById(R.id.checkBox5);
        cb6 = (CheckBox)findViewById(R.id.checkBox6);
        cb7 = (CheckBox)findViewById(R.id.checkBox7);
        cb8 = (CheckBox)findViewById(R.id.checkBox8);
        cbF = (CheckBox)findViewById(R.id.checkBoxFull);

        firebaseFirestore = FirebaseFirestore.getInstance();

        carImage = (ImageView)findViewById(R.id.carImage);

        VinNo = (EditText)findViewById(R.id.VINNo);

        text1 = (TextView)findViewById(R.id.textCost);


        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        //Toast.makeText(SetupActivity.this,"Data Exists", Toast.LENGTH_LONG).show();
                        String vno = task.getResult().getString("VINNo");

                        VinNo.setText(vno);


                    } else {
                        Toast.makeText(Maintain.this, "Please Enter VIN No", Toast.LENGTH_LONG).show();

                    }

                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(Maintain.this, "FIRESTORE Retrieve Error : " + error, Toast.LENGTH_LONG).show();
                }

            }
        });




        firebaseAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public  void  onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user == null){
                    Intent intent = new Intent(Maintain.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        };

        btnCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            String vinNo = VinNo.getText().toString();

            Long tsLong = System.currentTimeMillis()/1000;
            String ts = tsLong.toString();

            Map<String, String> userMap = new HashMap<>();
            userMap.put("VINNo", vinNo);
            userMap.put("TimeStamp", ts);

            firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful())
                    {
                        Toast.makeText(Maintain.this, "Updated VinNo", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String error = task.getException().getMessage();
                        Toast.makeText(Maintain.this, "FIRESTORE Error : " + error, Toast.LENGTH_LONG).show();
                    }


                }
            });

            calulateCost();

            }
        });


    }

    private void calulateCost() {

        if(cb1.isChecked())
        {
            c1 = 5;
        }
        else{
            c1 = 0;
        }
        if(cb2.isChecked())
        {
            c2 = 10;
        }
        else{
            c2 = 0;
        }

        if(cb3.isChecked())
        {
            c3 = 5;
        }
        else{
            c3 = 0;
        }

        if(cb4.isChecked())
        {
            c4 = 10;
        }
        else{
            c4 = 0;
        }

        if(cb5.isChecked())
        {
            c5 = 20;
        }
        else{
            c5 = 0;
        }

        if(cb6.isChecked())
        {
            c6 = 15;
        }
        else{
            c6 = 0;
        }

        if(cb7.isChecked())
        {
            c7 = 30;
        }
        else{
            c7 = 0;
        }

        if(cb8.isChecked())
        {
            c8 = 20;
        }
        else{
            c8 = 0;
        }

        if(cbF.isChecked())
        {
            c9 = 20;
        }
        else{
            c9 = 0;
        }



        int Cost = c1+c2+c3+c4+c5+c6+c7+c8+c9;

        text1.setText("Rs: " + Cost);

        text1.setVisibility(View.VISIBLE);

    }
}