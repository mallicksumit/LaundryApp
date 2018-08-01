package com.example.kon_boot.laundryapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    EditText phone;
    static EditText email;
    TextView phone_edit;
    TextView email_edit;
    TextView password;
    ImageView referral;
    ArrayList<User> muser ;
    FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mdata;
    private FirebaseUser curUser;
    private FirebaseAuth mAutm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
         muser = new ArrayList<>();
         mdata=FirebaseDatabase.getInstance().getReference();
         curUser=FirebaseAuth.getInstance().getCurrentUser();
        phone = (EditText) findViewById(R.id.phone_text);
        email = (EditText) findViewById(R.id.email_text);


        password = (TextView) findViewById(R.id.password_edit);

        referral = (ImageView) findViewById(R.id.refer_image);

        phone.setEnabled(false);
        email.setEnabled(false);
        phone.setInputType(InputType.TYPE_NULL);
        email.setInputType(InputType.TYPE_NULL);
        getacct();


        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // intent to change password activity
                Intent intent = new Intent(ProfileActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        referral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ReferActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getacct() {
        mdata.child("USER").child(curUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                muser.clear();
                    User saveuser = dataSnapshot.getValue(User.class);
                    muser.add(saveuser);
                phone.setText(saveuser.getPhn());
                email.setText(saveuser.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}

