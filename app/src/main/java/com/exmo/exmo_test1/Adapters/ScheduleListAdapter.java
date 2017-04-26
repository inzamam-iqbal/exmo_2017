package com.exmo.exmo_test1.Adapters;


import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.exmo.exmo_test1.Entities.Schedule;
import com.exmo.exmo_test1.R;

import java.util.ArrayList;

public class ScheduleListAdapter  extends ArrayAdapter<Schedule> {

    private final Activity context;
    private final ArrayList<Schedule> scheduleItems;
    private final ArrayList<String> scheduleItemsKeys;
    ArrayList<String> allDeps;

    public ScheduleListAdapter(Activity context, ArrayList<Schedule> scheduleItems, ArrayList<String> scheduleItemsKeys) {
        super(context, R.layout.row_schedule, scheduleItems);
        this.context = context;
        this.scheduleItems = scheduleItems;
        this.scheduleItemsKeys = scheduleItemsKeys;

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


    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.row_schedule, null, true);
        TextView timeTxt = (TextView) rowView.findViewById(R.id.txt_time);
        TextView headingTxt = (TextView) rowView.findViewById(R.id.txt_detail);

        timeTxt.setText(scheduleItems.get(position).getProperTime());
        headingTxt.setText(scheduleItems.get(position).getTitle()+"\n"+allDeps.get(Integer.parseInt(scheduleItems.get(position).getDepartment())));


        return rowView;
    }
}
