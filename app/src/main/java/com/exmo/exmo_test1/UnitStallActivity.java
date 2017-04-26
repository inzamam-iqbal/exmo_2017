package com.exmo.exmo_test1;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.exmo.exmo_test1.Adapters.HorListUnitStallAdapter;
import com.exmo.exmo_test1.Entities.Events;
import com.exmo.exmo_test1.Entities.Rating;
import com.exmo.exmo_test1.Parent.NavBar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;

public class UnitStallActivity   extends NavBar {

    ImageView mainImage;
    FloatingActionButton feedBackButton;
    TextView stallTitle;
    TextView stallDesc;
    String departmentId;


    HorListUnitStallAdapter aItems;
    ArrayList<String> stallPics;


    ArrayList<String> allDeps;
    ArrayList<Integer> allDepsImg;

    private DatabaseReference dbSCheduleRef;
    private Events event;


    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_stall);

        setTitle("Stall Details");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //your code here
        fab=(FloatingActionButton)findViewById(R.id.fab);
        mainImage=(ImageView)findViewById(R.id.mainImage);
        feedBackButton = (FloatingActionButton) findViewById(R.id.feedBack_button);
        stallTitle=(TextView) findViewById(R.id.stallName);
        stallDesc=(TextView) findViewById(R.id.stallDesc);





        stallPics=new ArrayList<>();
        aItems = new HorListUnitStallAdapter(UnitStallActivity.this,stallPics);
        TwoWayView lvTest = (TwoWayView) findViewById(R.id.lvItems);
        lvTest.setAdapter(aItems);


        allDeps=new ArrayList<>();
        allDeps.add("Chemical & Process Engineering");
        allDeps.add("Civil Engineering");
        allDeps.add("Computer Science & Engineering");
        allDeps.add("Earth Resource Engineering");
        allDeps.add("Electrical Engineering");
        allDeps.add("Electronics & Telecommunication Engineering");
        allDeps.add("Fashion Design & Product Development");
        allDeps.add("Material Science & Engineering");
        allDeps.add("Mechanical Engineering");
        allDeps.add("Textile & Clothing Technology");
        allDeps.add("Transport & Logistic Management");

        allDepsImg=new ArrayList<>();
        allDepsImg.add(R.drawable.ch);
        allDepsImg.add(R.drawable.ce);
        allDepsImg.add(R.drawable.cse);
        allDepsImg.add(R.drawable.em);
        allDepsImg.add(R.drawable.ee);
        allDepsImg.add(R.drawable.entc);
        allDepsImg.add(R.drawable.fd);
        allDepsImg.add(R.drawable.mt);
        allDepsImg.add(R.drawable.me);
        allDepsImg.add(R.drawable.tm);
        allDepsImg.add(R.drawable.tlm);

        populateAll();
    }

    private void populateAll() {
        //get key from intent
        final String key=getIntent().getStringExtra("tag");

        String nameStall=getIntent().getStringExtra("stallName");
        setTitle(nameStall);
        //connect db
        dbSCheduleRef = FirebaseDatabase.getInstance().getReference().child("events").child(key);

        dbSCheduleRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                event = dataSnapshot.getValue(Events.class);

                Integer img=allDepsImg.get(Integer.parseInt(event.getDepartment()));



                departmentId = event.getDepartment();
                mainImage.setImageResource(img);

                final String stallT=event.getTitle();
                stallTitle.setText("");
                stallDesc.setText(event.getDescription());

                if (event.getImageUrl() != null)
                    stallPics.add(event.getImageUrl());
                Log.e("picUnit",event.getImageUrl()+"hh");
                if (event.getImageUrl1() != null)
                    stallPics.add(event.getImageUrl1());
                Log.e("picUnit",event.getImageUrl1()+"hh");
                if (event.getImageUrl2() != null)
                    stallPics.add(event.getImageUrl2());
                if (event.getImageUrl3() != null)
                    stallPics.add(event.getImageUrl3());
                if (event.getImageUrl4() != null)
                    stallPics.add(event.getImageUrl4());

                aItems.notifyDataSetChanged();

                feedBackButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //pop up to ask all details
                        LayoutInflater layoutInflater = LayoutInflater.from(UnitStallActivity.this);
                        View promptView = layoutInflater.inflate(R.layout.feedback_stall_popup, null);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UnitStallActivity.this);
                        alertDialogBuilder.setView(promptView);

                        final EditText emailEditText=(EditText)promptView.findViewById(R.id.editText_email);
                        final EditText feedbackEditText=(EditText)promptView.findViewById(R.id.editText_feedback);
                        final RatingBar ratingNew=(RatingBar)promptView.findViewById(R.id.ratingBar2);

                        alertDialogBuilder.setCancelable(true)
                                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        if(!emailEditText.getText().toString().contains("@")){
                                            Toast.makeText(UnitStallActivity.this, "Incorrect Email Address", Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        String emailAddress=emailEditText.getText().toString();
                                        String feedback=feedbackEditText.getText().toString();
                                        float ratingValue=ratingNew.getRating();

                                        Rating rating = new Rating(emailAddress,feedback,ratingValue);

                                        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().
                                                child("stallRating").child(departmentId).child(stallT);
                                        String pushKey = dbRef.push().getKey();
                                        dbRef.child(pushKey).setValue(rating);


                                        Toast.makeText(UnitStallActivity.this, "Thank You", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("Back",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                        AlertDialog alert = alertDialogBuilder.create();
                        alert.show();
                    }
                });

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent d=new Intent(UnitStallActivity.this,MapActivity.class);
                        d.putExtra("KeyStall",key);
                        startActivity(d);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}