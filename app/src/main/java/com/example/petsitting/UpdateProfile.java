package com.example.petsitting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfile extends AppCompatActivity {

    EditText up_full_name, up_profile_phn_no, up_profile_address, up_profile_nid, up_profile_availability, up_profile_price;
    Button up_update_save_btn;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    DocumentReference documentReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String currentUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentUserId = user.getUid();
        documentReference = db.collection("All users").document(currentUserId);

        up_full_name = findViewById(R.id.up_full_name);
        up_profile_phn_no = findViewById(R.id.up_profile_phn_no);
        up_profile_address = findViewById(R.id.up_profile_address);
        up_profile_nid = findViewById(R.id.up_profile_nid);
        up_profile_availability = findViewById(R.id.up_profile_availability);
        up_profile_price = findViewById(R.id.up_profile_price);
        up_update_save_btn = findViewById(R.id.up_update_save_btn);

        up_update_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateProfile();

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()) {

                            String full_name = task.getResult().getString("full_name");
                            String phone_no = task.getResult().getString("phone_no");
                            String address = task.getResult().getString("address");
                            String availability = task.getResult().getString("availability");
                            String price = task.getResult().getString("price");
                            String nid = task.getResult().getString("nid");
                            //String url = task.getResult().getString("url");
                            //String uid = task.getResult().getString("uid ");

                            //Picasso.get().load(url).into(et_profile_image);
                            up_full_name.setText(full_name);
                            up_profile_phn_no.setText(phone_no);
                            up_profile_address.setText(address);
                            up_profile_availability.setText(availability);
                            up_profile_price.setText(price);
                            up_profile_nid.setText(nid);


                        } else {
                            Toast.makeText(UpdateProfile.this, "No Profile", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateProfile() {

        String full_name = up_full_name.getText().toString();
        String phone_no = up_profile_phn_no.getText().toString();
        String address = up_profile_address.getText().toString();
        String availability = up_profile_availability.getText().toString();
        String price = up_profile_price.getText().toString();
        String nid = up_profile_nid.getText().toString();

        final DocumentReference sDoc = db.collection("All users").document(currentUserId);

        db.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {

                transaction.update(sDoc, "full_name", full_name);
                transaction.update(sDoc, "phone_no", phone_no);
                transaction.update(sDoc, "address", address);
                transaction.update(sDoc, "availability", availability);
                transaction.update(sDoc, "price", price);
                transaction.update(sDoc, "nid", nid);
                transaction.update(sDoc, "uid", currentUserId);

                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(UpdateProfile.this, "Updated", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(UpdateProfile.this, "Failed", Toast.LENGTH_SHORT).show();

            }
        });


        }

    }


