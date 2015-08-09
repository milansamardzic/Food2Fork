package com.milansamardzic.ohmymeal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ms on 1/21/15.
 */
public class IngAdapter extends ArrayAdapter<String> {


    public IngAdapter(Context context, List<String> ingList) {
        super(context, 0, ingList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.one_recept_ing, null);
        }

        String ing = getItem(position);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvIng);
        tvTitle.setText(ing);

        return  convertView;
    }

}
