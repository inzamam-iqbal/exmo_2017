package com.exmo.exmo_test1.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.exmo.exmo_test1.Entities.DepartmentEvents;
import com.exmo.exmo_test1.R;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;

public class ExploreMainAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> departmentName;
    private final ArrayList<ArrayList<DepartmentEvents>> depContent;

    public ExploreMainAdapter(Activity context, ArrayList<String> departmentName, ArrayList<ArrayList<DepartmentEvents>> depContent) {
        super(context, R.layout.row_explore_main, departmentName);
        this.context = context;
        this.departmentName = departmentName;
        this.depContent = depContent;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.row_explore_main, null, true);
        TextView timeTXT = (TextView) rowView.findViewById(R.id.txt_dep_name);

        timeTXT.setText(departmentName.get(position));


        HorListExploreAdapter aItems = new HorListExploreAdapter(context,depContent.get(position));
        TwoWayView lvTest = (TwoWayView) rowView.findViewById(R.id.lvItems);
        lvTest.setAdapter(aItems);



        aItems.notifyDataSetChanged();



        return rowView;
    }
}
