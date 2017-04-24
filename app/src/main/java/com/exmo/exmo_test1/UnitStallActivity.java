package com.exmo.exmo_test1;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.exmo.exmo_test1.Adapters.HorListExploreAdapter;
import com.exmo.exmo_test1.Adapters.HorListUnitStallAdapter;
import com.exmo.exmo_test1.Entities.Events;
import com.exmo.exmo_test1.Parent.NavBar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;

public class UnitStallActivity   extends NavBar {

    ImageView mainImage;
//    RatingBar ratingBar;
    TextView stallTitle;
    TextView stallDesc;


    HorListUnitStallAdapter aItems;
    ArrayList<String> stallPics;


    ArrayList<String> allDeps;
    ArrayList<Integer> allDepsImg;

    private DatabaseReference dbSCheduleRef;
    private Events event;

    Double lat,lang;
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
//        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        stallTitle=(TextView) findViewById(R.id.stallName);
        stallDesc=(TextView) findViewById(R.id.stallDesc);




        stallPics=new ArrayList<>();
        aItems = new HorListUnitStallAdapter(UnitStallActivity.this,stallPics);
        TwoWayView lvTest = (TwoWayView) findViewById(R.id.lvItems);
        lvTest.setAdapter(aItems);


        allDeps=new ArrayList<>();
        allDeps.add("Bio Medical Engineering");
        allDeps.add("Chemical Engineering");
        allDeps.add("Civil Engineering");
        allDeps.add("Computer Science Engineering");
        allDeps.add("Earth Reasource Engineering");
        allDeps.add("Electrical Engineering");
        allDeps.add("Electronics and Telecommunication Engineering");
        allDeps.add("Material Science Engineering");
        allDeps.add("Mechanical Engineering");
        allDeps.add("Textile Engineering");





        allDeps.add("Transport and Logistic");

        allDepsImg=new ArrayList<>();
        allDepsImg.add(R.drawable.bme);
        allDepsImg.add(R.drawable.ch);
        allDepsImg.add(R.drawable.ce);
        allDepsImg.add(R.drawable.cse);
        allDepsImg.add(R.drawable.em);
        allDepsImg.add(R.drawable.ee);
        allDepsImg.add(R.drawable.entc);
        allDepsImg.add(R.drawable.mt);
        allDepsImg.add(R.drawable.me);
        allDepsImg.add(R.drawable.tm);
        allDepsImg.add(R.drawable.tlm);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent d=new Intent(UnitStallActivity.this,MapActivity.class);
                startActivity(d);
            }
        });

    }

    private void populateAll() {
        //get key from intent
        String key=getIntent().getStringExtra("tag");

        Log.e("key",key);
        //connect db
        dbSCheduleRef = FirebaseDatabase.getInstance().getReference().child("events").child(key);

        dbSCheduleRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                event = dataSnapshot.getValue(Events.class);

                Integer img=allDepsImg.get(Integer.parseInt(event.getDepartment()));

                lat=event.getLat();
                lang=event.getLang();


                float rating;
                try {
                    rating = event.getRate() / event.getNumRate();
                }catch (Exception e){
                    rating= (float) 3.5;
                }

                mainImage.setImageResource(img);
                //Picasso.with(getApplicationContext()).load(event.getImageUrl()).into(mainImage);
//                ratingBar.setRating(rating);
                stallTitle.setText(allDeps.get(Integer.parseInt(event.getDepartment()))+" | "+event.getTitle());
                stallDesc.setText(event.getDescription());

                stallPics=new ArrayList<>();
//                stallPics.add("Add images to this");
                aItems.notifyDataSetChanged();

//                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//                    public void onRatingChanged(RatingBar ratingBar, float rating,
//                                                boolean fromUser) {
//                        int newNumRate=event.getNumRate()+1;
//                        int newRate=event.getRate()+Integer.parseInt(String.valueOf(rating));
//
//                        dbSCheduleRef.child("rate").setValue(newRate);
//                        dbSCheduleRef.child("numRate").setValue(newNumRate);
//
//
//                    }
//                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        populateAll();
    }
}