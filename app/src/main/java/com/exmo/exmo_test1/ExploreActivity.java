package com.exmo.exmo_test1;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.exmo.exmo_test1.Adapters.ExploreMainAdapter;
import com.exmo.exmo_test1.Entities.DepartmentEvents;
import com.exmo.exmo_test1.Parent.NavBar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExploreActivity  extends NavBar {

    ArrayList<String> departmentNames;
    ArrayList<String> eventKeys;
    ArrayList<ArrayList<DepartmentEvents>> allDepartmentEvents;

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
        departmentNames =new ArrayList<>();
        eventKeys = new ArrayList<>();


        final ExploreMainAdapter exploreMainAdapter=new ExploreMainAdapter(ExploreActivity.this, departmentNames, allDepartmentEvents);
        ListView listView=(ListView)findViewById(R.id.explore_main_list);
        listView.setAdapter(exploreMainAdapter);


        FirebaseDatabase.getInstance().getReference().child("departmentEvents").
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                            departmentNames.add(snapshot.getKey());
                            ArrayList<DepartmentEvents> departmentEvents = new ArrayList<>();
                            for (DataSnapshot event: snapshot.getChildren()){
                                DepartmentEvents temp = event.getValue(DepartmentEvents.class);
                                temp.setKey(event.getKey());
                                departmentEvents.add(temp);
                                Log.e("departmentEvent",departmentEvents.get(0).getName());

                                exploreMainAdapter.notifyDataSetChanged();
                            }
                            allDepartmentEvents.add(departmentEvents);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




    }
}
