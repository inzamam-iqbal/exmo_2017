package com.exmo.exmo_test1.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.exmo.exmo_test1.Entities.DepartmentEvents;
import com.exmo.exmo_test1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HorListUnitStallAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> stallPic;

    public HorListUnitStallAdapter(Activity context, ArrayList<String> stallPic) {
        super(context, R.layout.row_hor_unit_stall, stallPic);
        this.context = context;
        this.stallPic = stallPic;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.row_hor_unit_stall, null, true);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageViewUnit);

        //Picasso.with(context).load(stallPic.get(position).getImageUrl()).into(imageView);
        imageView.setImageResource(0);


        return rowView;
    }
}