package com.example.transporttracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnTrack;

    private final static int LOGIN_PERMISSION=1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTrack = (Button)findViewById(R.id.btnTrack);
        btnTrack.setOnClickListener(new View.OnClickListener() {

            List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().setAllowNewAccounts(true).build());

            @Override
            public void onClick(View v) {
                startActivityForResult(
                        AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers).build(), LOGIN_PERMISSION
                );
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LOGIN_PERMISSION)
        {
            startNewActivity(resultCode, data);
        }
    }

    private void startNewActivity(int resultCode, Intent data)
    {
        if(resultCode == RESULT_OK)
        {
            Intent intent = new Intent(MainActivity.this, ListOnLine.class);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(this, "Login Falied",Toast.LENGTH_SHORT).show();
        }
    }
}
