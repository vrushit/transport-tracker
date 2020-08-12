package com.example.transporttracker_new;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

     EditText email;
     EditText password;
    private Button btn;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;


    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText)findViewById(R.id.loginText);
        password = (EditText)findViewById(R.id.password);
        btn = (Button)findViewById(R.id.btn1);
        progressBar = (ProgressBar)findViewById(R.id.pB);
        mAuth = FirebaseAuth.getInstance();

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public  void  onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        };


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            String mEmail = email.getText().toString().trim();
            String mPassword = password.getText().toString().trim();

            if(TextUtils.isEmpty(mEmail))
            {
                Toast.makeText(LoginActivity.this,"Email is Empty",Toast.LENGTH_SHORT).show();
            }
            if(TextUtils.isEmpty(mPassword))
            {
                Toast.makeText(LoginActivity.this,"Password is Empty",Toast.LENGTH_SHORT).show();
            }

            progressBar.setVisibility(View.VISIBLE);

            //Authentication
            mAuth.signInWithEmailAndPassword(mEmail, mPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Email and Password Pair incorrect",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });

            }
        });


    }
}