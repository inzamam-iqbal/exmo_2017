package com.moratuwa.exhibition;

import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.moratuwa.exhibition.Adapters.ExploreMainAdapter;
import com.moratuwa.exhibition.Entities.DepartmentEvents;
import com.moratuwa.exhibition.Parent.NavBar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExploreActivity  extends NavBar {

    ArrayList<Integer> departmentImages,departmentKey;
    ArrayList<String> departmentNames;
    ArrayList<String> eventKeys;
    ArrayList<ArrayList<DepartmentEvents>> allDepartmentEvents;
    ExploreMainAdapter exploreMainAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        setTitle("Explore");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        allDepartmentEvents =new ArrayList<>();
        departmentKey =new ArrayList<>();
        departmentNames =new ArrayList<>();
        departmentImages =new ArrayList<>();
        eventKeys = new ArrayList<>();

        final ArrayList<String> allDeps=new ArrayList<>();
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



        final ArrayList<Integer> allDepsImg=new ArrayList<>();
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




        exploreMainAdapter=new ExploreMainAdapter(ExploreActivity.this, departmentNames,departmentImages, allDepartmentEvents,departmentKey);
        ListView listView=(ListView)findViewById(R.id.explore_main_list);
        listView.setAdapter(exploreMainAdapter);

        FirebaseDatabase.getInstance().getReference().child("departmentEvents").
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        departmentKey.clear();
                        departmentNames.clear();
                        departmentImages.clear();
                        allDepartmentEvents.clear();
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                            departmentKey.add(Integer.parseInt(snapshot.getKey()));
                            departmentNames.add(allDeps.get(Integer.parseInt(snapshot.getKey())));
                            departmentImages.add(allDepsImg.get(Integer.parseInt(snapshot.getKey())));
                            Log.e("departmentNames",allDeps.get(Integer.parseInt(snapshot.getKey())));
                            ArrayList<DepartmentEvents> departmentEvents = new ArrayList<>();
                            for (DataSnapshot event: snapshot.getChildren()){
                                DepartmentEvents temp = event.getValue(DepartmentEvents.class);
                                temp.setKey(event.getKey());
                                departmentEvents.add(temp);
                            }
                            allDepartmentEvents.add(departmentEvents);
                            exploreMainAdapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            finish();
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }
}
