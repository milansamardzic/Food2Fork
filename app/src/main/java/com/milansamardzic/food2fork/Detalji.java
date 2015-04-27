package com.milansamardzic.food2fork;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by ms on 1/18/15.
 */
public class Detalji implements Serializable {
    private static final long serialVersionUID = -8959832007991513854L;
    private String
            title = null,
            publisher = null,
            f2fUrl = null,
            image = null,
            publisherUrl = null,
            rId = null,
            ingredientsString=null;
    private Double socialRank = null;
    private JSONArray ingredientsJArray=null;
    private ArrayList<String> ingredientsArrayList = new ArrayList<String>();

    public static Detalji fetchDetalje(JSONObject json){
        Detalji d = new Detalji();

        Log.d("rid5", String.valueOf(json));
        try {
            d.title = json.getString("title");

            d.publisher = json.getString("publisher");
            d.publisherUrl = json.getString("publisher_url");
            d.f2fUrl = json.getString("f2f_url");
            d.rId = json.getString("recipe_id");
            d.image = json.getString("image_url");
            d.socialRank = json.getDouble("social_rank");
            DecimalFormat df = new DecimalFormat("#.##");
            d.socialRank = Double.valueOf(df.format(d.socialRank));

            d.ingredientsJArray = json.getJSONArray("ingredients");

            for (int i=0; i < d.ingredientsJArray.length(); i++){
                d.ingredientsArrayList.add(d.ingredientsJArray.get(i).toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return d;

    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getF2fUrl() {
        return f2fUrl;
    }

    public String getImage() {
        return image;
    }

    public String getPublisherUrl() {
        return publisherUrl;
    }

    public ArrayList<String> getIngredientsArrayList() {
        return ingredientsArrayList;
    }

    public Double getSocialRank() {
        return socialRank;
    }

    public String getrId() {
        return rId;
    }
}
