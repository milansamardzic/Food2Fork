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
 * Created by ms on 1/22/15.
 */
public class AdapterSaved extends ArrayAdapter<Detalji> {

    public AdapterSaved(Context context, ArrayList<Detalji> aMovies) {
        super(context, 0, aMovies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Detalji movie = getItem(position);

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
        tvPublisher.setText(movie.getPublisher());
        tvScore.setText(String.valueOf(movie.getSocialRank()) + " %");
        Picasso.with(getContext()).load(movie.getImage()).into(ivReceptImage);
        return convertView;
    }

}
