package com.moratuwa.exhibition.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.moratuwa.exhibition.R;
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
        final ImageView imageView = (ImageView) rowView.findViewById(R.id.imageViewUnit);
        Log.e("came here","hi");
        Log.e("came in image",stallPic.get(position));
        Picasso.with(context).load(stallPic.get(position)).into(imageView);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog builder = new Dialog(context);
                builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                builder.getWindow().setBackgroundDrawable(
                        new ColorDrawable(android.graphics.Color.TRANSPARENT));
                builder.setCancelable(true);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        //nothing;
                    }
                });

                ImageView imageViewPop = new ImageView(context);

                imageViewPop.setImageDrawable(imageView.getDrawable());
                builder.addContentView(imageViewPop, new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));

                android.view.ViewGroup.LayoutParams layoutParams = imageViewPop.getLayoutParams();
                layoutParams.width = 1000;
                layoutParams.height = 1200;
                imageViewPop.setLayoutParams(layoutParams);
                builder.show();

            }
        });

        return rowView;
    }
}