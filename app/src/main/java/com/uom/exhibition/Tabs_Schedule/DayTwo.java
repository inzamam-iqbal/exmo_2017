package com.uom.exhibition.Tabs_Schedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.uom.exhibition.Adapters.ScheduleListAdapter;
import com.uom.exhibition.Entities.Schedule;
import com.uom.exhibition.R;
import com.uom.exhibition.UnitStallActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DayTwo extends Fragment {

    ArrayList<Schedule> scheduleItems;
    ArrayList<String> scheduleItemsKeys;
    ScheduleListAdapter scheduleListAdapter;
    DatabaseReference dbDay2Ref;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scheduleItems = new ArrayList<>();
        scheduleItemsKeys = new ArrayList<>();
        dbDay2Ref = FirebaseDatabase.getInstance().getReference().child("schedule").child("day2");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.day_two, container, false);

        populateArray();

        scheduleListAdapter=new ScheduleListAdapter(getActivity(),scheduleItems,scheduleItemsKeys);
        ListView listView=(ListView)rootView.findViewById(R.id.list_day_two);
        listView.setAdapter(scheduleListAdapter);

        dbDay2Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    scheduleItems.add(snapshot.getValue(Schedule.class));
                    scheduleItemsKeys.add(snapshot.getKey());
                    scheduleListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent d=new Intent(getActivity().getApplicationContext(),UnitStallActivity.class);
                d.putExtra("stallName",scheduleItems.get(i).getTitle());
                d.putExtra("tag",scheduleItemsKeys.get(i));
                startActivity(d);
            }
        });

        return rootView;
    }

    private void populateArray() {

    }
}