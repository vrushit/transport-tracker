package com.example.transporttracker_new;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    Button locateBtn;
    Button btnMaintain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        locateBtn = (Button)findViewById(R.id.btnLocate);
        btnMaintain = (Button)findViewById(R.id.btnBook);
        firebaseAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public  void  onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user == null){
                    Intent intent = new Intent(Home.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        };

        Toolbar toolbar = (Toolbar)findViewById(R.id.HomeToolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);


        btnMaintain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Maintain.class);
                startActivity(intent);
            }
        });

        locateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Locate.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.action_signout:
                firebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Home.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.action_track:
                Intent newIntent = new Intent(Home.this, ListOnLine.class);
                startActivity(newIntent);
                break;

        }


        return super.onOptionsItemSelected(item);
    }
}