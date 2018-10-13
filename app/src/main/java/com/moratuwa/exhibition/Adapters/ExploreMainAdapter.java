package com.moratuwa.exhibition.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.moratuwa.exhibition.Entities.DepartmentEvents;
import com.moratuwa.exhibition.R;
import com.moratuwa.exhibition.UnitDepartmentActivity;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;

public class ExploreMainAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> departmentName;
    private final ArrayList<Integer> departmentKey;
    private final ArrayList<Integer> departmentImage;
    private final ArrayList<ArrayList<DepartmentEvents>> depContent;

    public ExploreMainAdapter(Activity context, ArrayList<String> departmentName, ArrayList<Integer> departmentImage, ArrayList<ArrayList<DepartmentEvents>> depContent, ArrayList<Integer> departmentKey) {
        super(context, R.layout.row_explore_main, departmentName);
        this.context = context;
        this.departmentName = departmentName;
        this.depContent = depContent;
        this.departmentImage = departmentImage;
        this.departmentKey=departmentKey;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.row_explore_main, null, true);
        TextView timeTXT = (TextView) rowView.findViewById(R.id.txt_dep_name);
        ImageView depImg = (ImageView) rowView.findViewById(R.id.dep_img);
        RelativeLayout button = (RelativeLayout) rowView.findViewById(R.id.more_info_layout);

        timeTXT.setText(departmentName.get(position));
        depImg.setImageResource(departmentImage.get(position));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent d=new Intent(context,UnitDepartmentActivity.class);
                d.putExtra("DepNum",departmentKey.get(position)+"");
                context.startActivity(d);
            }
        });

        Log.e("ExploreMainAdapter",departmentName.get(position));
        HorListExploreAdapter aItems = new HorListExploreAdapter(context,depContent.get(position));
        TwoWayView lvTest = (TwoWayView) rowView.findViewById(R.id.lvItems);
        lvTest.setAdapter(aItems);



        aItems.notifyDataSetChanged();



        return rowView;
    }
}
