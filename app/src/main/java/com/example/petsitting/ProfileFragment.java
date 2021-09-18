package com.example.petsitting;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    ImageView et_profile_image;
    TextView et_profile_full_name,et_profile_phn_no,et_profile_address,et_profile_nid,et_profile_availability,et_profile_price;
    Button et_profile_edit_btn;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_profile, container,false);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        et_profile_image = getActivity().findViewById(R.id.et_profile_image);
        et_profile_full_name = getActivity().findViewById(R.id.et_profile_full_name);
        et_profile_phn_no = getActivity().findViewById(R.id.et_profile_phn_no);
        et_profile_address = getActivity().findViewById(R.id.et_profile_address);
        et_profile_nid = getActivity().findViewById(R.id.et_profile_nid);
        et_profile_availability = getActivity().findViewById(R.id.et_profile_availability);
        et_profile_price = getActivity().findViewById(R.id.et_profile_price);
        et_profile_edit_btn= getActivity().findViewById(R.id.et_profile_edit_btn);

        et_profile_edit_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.et_profile_edit_btn:
                Intent intent = new Intent(getActivity(),UpdateProfile.class);
                startActivity(intent);
                break;

        }

    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentid = user.getUid();
        DocumentReference reference;
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        reference = firestore.collection("All users").document(currentid);

        reference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.getResult().exists()){

                            String full_name = task.getResult().getString("full_name");
                            String phone_no = task.getResult().getString("phone_no");
                            String address = task.getResult().getString("address");
                            String availability = task.getResult().getString("availability");
                            String price = task.getResult().getString("price");
                            String nid = task.getResult().getString("nid");
                            String url = task.getResult().getString("url");
                            //String uid = task.getResult().getString("uid ");

                            Picasso.get().load(url).into(et_profile_image);
                            et_profile_full_name.setText(full_name);
                            et_profile_phn_no.setText(phone_no);
                            et_profile_address.setText(address);
                            et_profile_availability.setText(availability);
                            et_profile_price.setText(price);
                            et_profile_nid.setText(nid);


                        }else{
                            Intent intent = new Intent(getActivity(),CreateProfile.class);
                            startActivity(intent);
                        }
                    }
                });

    }
}
