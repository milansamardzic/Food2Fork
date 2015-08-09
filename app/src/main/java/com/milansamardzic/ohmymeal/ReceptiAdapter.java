package com.milansamardzic.ohmymeal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ms on 1/14/15.
 */
public class ReceptiAdapter extends ArrayAdapter<Recept> {

    public ReceptiAdapter(Context context, ArrayList<Recept> aMovies) {
        super(context, 0, aMovies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Recept movie = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.one_recept, null);
        }

        // Lookup view for data population
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvReceptTitle);
        TextView tvPublisher = (TextView) convertView.findViewById(R.id.tvPublisher);
        TextView tvScore = (TextView) convertView.findViewById(R.id.tvScore);
        ImageView ivReceptImage = (ImageView) convertView.findViewById(R.id.ivReceptImage);

        tvTitle.setText(movie.getTitle());
        tvPublisher.setText(movie.getContent());
        tvScore.setText(String.valueOf(movie.getScore()) + " %");
        Picasso.with(getContext()).load(movie.getImage()).into(ivReceptImage);
        return convertView;
    }

}
