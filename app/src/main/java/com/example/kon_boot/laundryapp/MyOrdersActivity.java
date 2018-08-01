package com.example.kon_boot.laundryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class MyOrdersActivity extends AppCompatActivity {

    private ListView listView;
    ArrayList<order> orderList;
   ImageView img1;
   TextView txt1;
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mMessagesDatabaseReference;
    FirebaseAuth mauth;
    FirebaseUser current;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        img1=findViewById(R.id.imagelogout);
        txt1=findViewById(R.id.txtlogin);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mauth=FirebaseAuth.getInstance();
        current = mauth.getCurrentUser();
        if(current==null){
            img1.setVisibility(View.VISIBLE);
            txt1.setVisibility(View.VISIBLE);
        }
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("order");

        listView = (ListView) findViewById(R.id.list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                order currentOrder = orderList.get(i);

                Intent intent = new Intent(MyOrdersActivity.this, OrderStatusActivity.class);
                intent.putExtra("Detail_Order", currentOrder);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("order");
        mMessagesDatabaseReference.keepSynced(true);

        mMessagesDatabaseReference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        orderList = new ArrayList<order>();
                        for (DataSnapshot snap: dataSnapshot.getChildren()) {

                            orderList.add(snap.getValue(order.class));
                            Collections.reverse(orderList);
                            OrderAdapter adapter = new OrderAdapter(MyOrdersActivity.this,orderList);

                            listView.setStackFromBottom(false);
                            listView.setAdapter(adapter);

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }
}

