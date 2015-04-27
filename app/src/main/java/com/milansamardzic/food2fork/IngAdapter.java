package com.milansamardzic.food2fork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ms on 1/21/15.
 */
public class IngAdapter extends ArrayAdapter<String> {


    public IngAdapter(Context context, ArrayList<String> ingredientsArray) {
        super(context, 0, ingredientsArray);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       String ing = getItem(position);
       RelativeLayout rlIngRL;

        if (convertView == null) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.one_recept_ing, null);
    }

         TextView tvTitle = (TextView) convertView.findViewById(R.id.tvIng);
        rlIngRL = (RelativeLayout) convertView.findViewById(R.id.rlIngRL);
        tvTitle.setText(position  +1 +" " + ing);

        if( (position%2)==0 ){

        rlIngRL.setBackgroundColor(R.color.md_amber_600);}
        else{
            rlIngRL.setBackgroundColor(R.color.primary);}

        return  convertView;
    }

}
