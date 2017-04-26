package com.exmo.exmo_test1.Adapters;

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

import com.exmo.exmo_test1.Entities.DepartmentEvents;
import com.exmo.exmo_test1.R;
import com.exmo.exmo_test1.UnitStallActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HorListExploreAdapter extends ArrayAdapter<DepartmentEvents> {

    private final Activity context;
    private final ArrayList<DepartmentEvents> departmentEvents;
    //private final ArrayList<String> stallPic;

    public HorListExploreAdapter(Activity context, ArrayList<DepartmentEvents> departmentEvents) {
        super(context, R.layout.row_hor_explore, departmentEvents);
        this.context = context;
        this.departmentEvents = departmentEvents;
        //this.stallPic = stallPic;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.row_hor_explore, null, true);
        TextView timeTXT = (TextView) rowView.findViewById(R.id.textViewStallName);



        Log.e("Horizonta1",departmentEvents.get(position).getName() );
        timeTXT.setText(departmentEvents.get(position).getName());

        RelativeLayout rel=(RelativeLayout)rowView.findViewById(R.id.rel);

        if(position%2==0){
            rel.setBackgroundColor(context.getResources().getColor(R.color.even));
        }else {
            rel.setBackgroundColor(context.getResources().getColor(R.color.odd));
        }


        final int final_position=position;
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String key=departmentEvents.get(final_position).getKey();

                Intent d=new Intent(context, UnitStallActivity.class);
                d.putExtra("tag",key);
                d.putExtra("stallName",departmentEvents.get(final_position).getName()+"");
                context.startActivity(d);
            }
        });




        return rowView;
    }
}