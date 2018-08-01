package com.example.kon_boot.laundryapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class NewOrder1Activity extends AppCompatActivity {

    private EditText mUsername;
    private EditText mDate;
    private EditText mSlot;
    private EditText mService;
    private Button mAddress;
    private Button placeOrder;
    private ImageButton previous,next;
    private TextView selected_address;
    private order newOrder;
    ArrayList<SaveAddress> mlist =new ArrayList<>();
    String add,name;
    ArrayList<User> muser = new ArrayList<>();


    private Spinner mTimeSlotSpinner;
    private String timeSlot;

    private Spinner mQuantitySpinner;
    private String quantity;
    private SwipeRefreshLayout swipeRefreshLayout;

    private TextView dateCalendar;
    private ImageView calendar;
    Calendar mCurrentDate;
    int day, month, year;
    private String date;

    private String machine_select = "unselect";
    private String iron_select = "unselect";

    static int i=0;
    private ImageView machine;
    private ImageView iron;
    private String service;

    private int deliveryDate, deliveryMonth, deliveryYear;

    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mMessagesDatabaseReference,mData,mname;
    private FirebaseUser curUser;
    private FirebaseAuth mAutm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order1);

        ///mUsername = (EditText)findViewById(R.id.username);

        //mDate = (EditText)findViewById(R.id.date);
        mAddress = findViewById(R.id.address);
        previous = findViewById(R.id.prev);
        next = findViewById(R.id.forward);
        selected_address =findViewById(R.id.seletedaddress);
        swipeRefreshLayout=findViewById(R.id.swipe);
        //mSlot = (EditText)findViewById(R.id.time_slot);
        //mService = (EditText)findViewById(R.id.service);

        placeOrder = (Button)findViewById(R.id.place_order);
        newOrder = new order();

        mTimeSlotSpinner = (Spinner)findViewById(R.id.spinner_time_slot);

        mQuantitySpinner = (Spinner)findViewById(R.id.spinner_quantity);

        machine = (ImageView) findViewById(R.id.machine);
        iron = (ImageView)findViewById(R.id.iron);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("order");
        mData = FirebaseDatabase.getInstance().getReference();
        mname= FirebaseDatabase.getInstance().getReference();
        calendar = (ImageView)findViewById(R.id.calendar);
        dateCalendar = (TextView) findViewById(R.id.date_calendar);
        mCurrentDate = Calendar.getInstance();

        mAutm=FirebaseAuth.getInstance();
        curUser=mAutm.getCurrentUser();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);


        month = month+1;
        dateCalendar.setText(day+"/"+month+"/"+year);
        getDataFromDatabase();
                mAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewOrder1Activity.this,Address.class);
                startActivityForResult(intent,2020);
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mlist.isEmpty()) {

                }
                else {
                    if (i == 0) {
                        add = String.valueOf(mlist.get(i).getHome()+","+mlist.get(i).getHouseno()+","+mlist.get(i).getLandmark()+","+mlist.get(i).getAddress()+","+mlist.get(i).getCity()+","+mlist.get(i).getArea()+","+mlist.get(i).getSubarea());
                        selected_address.setText(add);
                        return;
                    }
                    if (i != 0 && i < mlist.size()) {
                      add =   String.valueOf(mlist.get(i).getHome()+","+mlist.get(i).getHouseno()+","+mlist.get(i).getLandmark()+","+mlist.get(i).getAddress()+","+mlist.get(i).getCity()+","+mlist.get(i).getArea()+","+mlist.get(i).getSubarea());
                        selected_address.setText(add);
                        i--;
                    }
                }

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mlist.isEmpty())
                {
                    selected_address.setText("Add an Address first");

                }
                else
                {
                    if(i==0)
                    {
                         add = String.valueOf(mlist.get(i).getHome()+","+mlist.get(i).getHouseno()+","+mlist.get(i).getLandmark()+","+mlist.get(i).getAddress()+","+mlist.get(i).getCity()+","+mlist.get(i).getArea()+","+mlist.get(i).getSubarea());

                        selected_address.setText(add);
                        i++;
                    }

                    if (i<(mlist.size())) {
                        add = String.valueOf(mlist.get(i).getHome()+","+mlist.get(i).getHouseno()+","+mlist.get(i).getLandmark()+","+mlist.get(i).getAddress()+","+mlist.get(i).getCity()+","+mlist.get(i).getArea()+","+mlist.get(i).getSubarea());

                        selected_address.setText(add);
                        name =muser.get(0).getName();
                        Toast.makeText(NewOrder1Activity.this, name, Toast.LENGTH_SHORT).show();
                        i--;
                        return;
                    }
                }


            }
        });
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewOrder1Activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int months, int dayOfMonth) {
                        months = months+1;
                        dateCalendar.setText(dayOfMonth+"/"+months+"/"+year);
                        day = dayOfMonth;
                        month = months;


                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        //date = calculateDeliveryDate();
        //Log.e("after calculatedelivery",date);

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (service == null ){
                    Toast.makeText(NewOrder1Activity.this,"Kindly choose service",Toast.LENGTH_SHORT).show();
                }
                else
                    if(selected_address.getText().toString() == "Add an Address first"){
                       Toast.makeText(NewOrder1Activity.this,"Kindly Select an Address",Toast.LENGTH_LONG).show();
                    }
                else  {
                    place_order();

                    //finish();
                    //date = calculateDeliveryDate();
                    Intent i = new Intent(NewOrder1Activity.this,OrderSummaryActivity.class);
                    i.putExtra("Order Summary",newOrder);
                    i.putExtra("name",name);
                    startActivity(i);
                }

            }
        });

        machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (machine_select.equals("unselect")){
                    machine.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    service = "machine";
                    machine_select = "select";
                }else if (machine_select.equals("select")){
                    machine.setBackgroundColor(Color.TRANSPARENT);
                    service = null;
                    machine_select = "unselect";
                }

            }
        });

        iron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iron_select.equals("unselect")){
                    iron.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    service = "iron";
                    iron_select = "select";
                }else if (iron_select.equals("select")){
                    iron.setBackgroundColor(Color.TRANSPARENT);
                    service = null;
                    iron_select = "unselect";
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromDatabase();
            }
        });

        setupTimeSlotSpinner();
        setupQuantitySpinner();
        String key = UUID.randomUUID().toString();
        Log.e("key uuid",key);

    }

    private void getDataFromDatabase()
    {
        swipeRefreshLayout.setRefreshing(true);
        mData.child("profile").child(curUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mlist.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    SaveAddress saveAddress=snapshot.getValue(SaveAddress.class);
                    mlist.add(saveAddress);
                }
                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mname.child("USER").child(curUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User current=dataSnapshot.getValue(User.class);
                muser.add(current);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void place_order(){
        newOrder.setQuantity(quantity);
        newOrder.setAddress(add);
        newOrder.setDate(dateCalendar.getText().toString());
        newOrder.setService(service);
        newOrder.setSlot(timeSlot);
        String key = UUID.randomUUID().toString();
        Log.e("key uuid",key);
        key = key.substring(0, Math.min(key.length(), 8));
        Log.e("trunc key uuid",key);
        newOrder.setKey(key);
        date = calculateDeliveryDate();
        newOrder.setDeliveryDate(date);
        Log.e("delivery slot","dummy value");
        newOrder.setDeliverySlot("time slot");
        mMessagesDatabaseReference.push().setValue(newOrder);

    }

    private void setupTimeSlotSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter timeSlotSpinnerAdapter = ArrayAdapter.createFromResource(this,R.array.time_slots_options,
                android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        timeSlotSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mTimeSlotSpinner.setAdapter(timeSlotSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mTimeSlotSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);

                if (selection.equals("10-12")) {
                    timeSlot = "10-12";
                } else if (selection.equals("1-3")) {
                    timeSlot = "1-3";
                } else {
                    timeSlot ="5-7" ;
                }

            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupQuantitySpinner() {
        String key = UUID.randomUUID().toString();
        Log.e("key uuid",key);
        Log.e("quantity spinner","called");
        quantity = "1-20";
        Log.e("quantity assign before",quantity);
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter quantitySpinnerAdapter = ArrayAdapter.createFromResource(this,R.array.quantity_options,
                android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        quantitySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mQuantitySpinner.setAdapter(quantitySpinnerAdapter);

        // Set the integer mSelected to the constant values
        mQuantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);

                if (selection.equals("1-20")) {

                    quantity = "1-20";
                    Log.e("quantity assign",quantity);
                } else if (selection.equals("20-40")) {
                    quantity = "20-40";
                } else {
                    quantity ="40+" ;
                }

            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                quantity = "1-20";
                Log.e("quantity assign",quantity);

            }
        });
    }

    private String calculateDeliveryDate(){
        String delivery;
        if (quantity == null){
            Log.e("inside method","quantity null");
        }
        //Log.e("inside calculate",quantity);
        Log.e("inside day is",String.valueOf(day));
        if(quantity != null){
            Log.e("inside method","quantity not null");
            if (quantity.equals("1-20") ){
                //2 days
                if (day==31){
                    deliveryDate = 2;
                }
                else if (day == 29 ){
                    deliveryDate = 1;
                }
                else{
                    deliveryDate = day +2;
                }
            }
            if (quantity.equals("20-40") ){
                //3 days
                if (day==31){
                    deliveryDate = 3;
                }
                else if (day == 29 ){
                    deliveryDate = 2;
                }
                else{
                    deliveryDate = day +3;
                }
            }
            if (quantity.equals("40+") ){
                //4 days
                if (day==31){
                    deliveryDate = 4;
                }
                else if (day == 29 ){
                    deliveryDate = 3;
                }
                else{
                    deliveryDate = day +4;
                }
            }
        }


        deliveryMonth = month;
        deliveryYear = year;

        delivery = String.valueOf(deliveryDate) + "/" +String.valueOf(deliveryMonth) + "/" + String.valueOf(deliveryYear);
        return delivery;
    }


}

