package com.example.kon_boot.laundryapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Details extends AppCompatActivity {
    EditText e3,e4,e5,e6,e7;
    ProgressDialog dialog;
    private DatabaseReference reference;
    private FirebaseAuth auth;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        reference=FirebaseDatabase.getInstance().getReference();

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();


            e3= findViewById( R.id.editText3 );
            e4= findViewById( R.id.editText4 );
            e5= findViewById( R.id.editText5 );
            e6= findViewById( R.id.editText6 );
            e7= findViewById( R.id.editText7 );
            dialog= new ProgressDialog( this );
        }
        public void signUpUser(View v)
        {
            dialog.setMessage( "Signing up.Please wait." );
            dialog.show();
            String name= e3.getText().toString();
            String phone= e4.getText().toString();
            String email=e5.getText().toString();
            String password= e6.getText().toString();
            String referral = e7.getText().toString();


            if(name.equals( "" )|| phone.equals("")||password.equals( "" )|| email.equals("")){
                Toast.makeText( getApplicationContext(),"Fields cannot be empty",Toast.LENGTH_SHORT ).show();
            }
            else {
                User user1 = new User(e3.getText().toString(), e4.getText().toString(), e5.getText().toString(), e6.getText().toString());

                reference.child("USER").child(user.getUid()).setValue(user1);
                dialog.dismiss();
                Intent intent =new Intent(Details.this,MainActivity.class);
                intent.putExtra("name",name);
                startActivity(intent);
                finish();

            }




        }

    @Override
    protected void onStart() {
        if(auth.getCurrentUser()==null)
        {

        }
         else
        {
            Intent intent =new Intent(Details.this,MainActivity.class);
            startActivity(intent);
        }
        super.onStart();
    }
}

