package com.milansamardzic.ohmymeal;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by ms on 1/14/15.
 */
public class Recept implements Serializable {
    private static final long serialVersionUID = -8959832007991513854L;
    private String title;
    private String content;
    private String image;
  //  private Integer rId;
    private String rId;
    private double score;

    public static Recept fromJson(JSONObject jsonObject) {
        Recept b = new Recept();
        try {
            b.title = jsonObject.getString("title");
            b.content = jsonObject.getString("publisher");
            b.image = jsonObject.getString("image_url");

            b.score = jsonObject.getDouble("social_rank");
                DecimalFormat df = new DecimalFormat("#.##");
                b.score = Double.valueOf(df.format(b.score));
            b.rId = jsonObject.getString("recipe_id");
           // b.rId = jsonObject.getInt("recipe_id");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return b;
    }

    // Decodes array of box office movie json results into business model objects
    public static ArrayList<Recept> fromJson(JSONArray jsonArray) {
        ArrayList<Recept> businesses = new ArrayList<Recept>(jsonArray.length());
        // Process each result in json array, decode and convert to business
        // object
        Log.d("sort", String.valueOf(jsonArray));
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject businessJson = null;
            try {
                businessJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Recept business = Recept.fromJson(businessJson);
            if (business != null) {
                businesses.add(business);
                Log.d("sort", String.valueOf(business));
            }
        }
        return businesses;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImage() {
        return image;
    }

    public String getrId() {
        return rId;
    }

    public double getScore() {
        return score;
    }
}