package com.example.petsitting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextView verifyEmailMsg;
    Button verifyEmailbtn;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        Button btnlogout = findViewById(R.id.btnlogout);
        verifyEmailMsg = findViewById(R.id.verifyEmailMsg);
        verifyEmailbtn = findViewById(R.id.verifyEmailbtn);

        if(!auth.getCurrentUser().isEmailVerified()){
            verifyEmailbtn.setVisibility(View.VISIBLE);
            verifyEmailMsg.setVisibility(View.VISIBLE);
        }

        verifyEmailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send verification message
                auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Verification Email Sent", Toast.LENGTH_SHORT).show();
                        verifyEmailbtn.setVisibility(View.GONE);
                        verifyEmailMsg.setVisibility(View.GONE);
                    }
                });
            }
        });


        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }
}