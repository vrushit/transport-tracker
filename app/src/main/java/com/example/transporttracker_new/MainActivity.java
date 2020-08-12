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

public class MainActivity extends AppCompatActivity {
    Button btnTrack;
    Button btnMaintain;
    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

//    private final static int LOGIN_PERMISSION=1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTrack = (Button)findViewById(R.id.btnTrack);
        btnMaintain = (Button)findViewById(R.id.btn2);

        firebaseAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public  void  onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user == null){
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    //Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                }
            }
        };

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar1);
        toolbar.setTitle("Select Your Choice");
        setSupportActionBar(toolbar);

        btnTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListOnLine.class);
                startActivity(intent);
            }
        });

        btnMaintain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Home.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

//    public void logout(View view)
//    {
//        firebaseAuth.getInstance().signOut();
//        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//        startActivity(intent);
//        finish();
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_signout:
                firebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(mAuthListener);
    }


}


//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == LOGIN_PERMISSION)
//        {
//            startNewActivity(resultCode, data);
//        }
//    }
//
//    private void startNewActivity(int resultCode, Intent data)
//    {
//        if(resultCode == RESULT_OK)
//        {
//            Intent intent = new Intent(MainActivity.this, ListOnLine.class);
//            startActivity(intent);
//            finish();
//        }
//        else{
//            Toast.makeText(this, "Login Failed",Toast.LENGTH_SHORT).show();
//        }
//    }


//        btnTrack.setOnClickListener(new View.OnClickListener() {
//
//            List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().setAllowNewAccounts(true).build());
//
//            @Override
//            public void onClick(View v) {
//                startActivityForResult(
//                        AuthUI.getInstance().createSignInIntentBuilder()
//                        .setAvailableProviders(providers).build(), LOGIN_PERMISSION
//                );
//            }
//        });
