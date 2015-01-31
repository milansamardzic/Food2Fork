package com.milansamardzic.food2fork;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.andexert.library.RippleView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.melnykov.fab.FloatingActionButton;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ms on 1/22/15.
 */
public class Have extends android.support.v4.app.Fragment {
    private ListView lvMovies;
    private ReceptiAdapter adapterMovies;
    private Fork2FoodClient client;
    public static final String MOVIE_DETAIL_KEY = "recipes";
    EditText et1, et2, et3;
    public int helpper=0;
    // ImageButton fabS;

    FloatingActionButton fabBtn;
    FloatingActionButton fabSbtn;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.have, container, false);
        lvMovies = (ListView) rootView.findViewById(R.id.lvRecepti);

<<<<<<< HEAD
        fabBtn = (FloatingActionButton) rootView.findViewById(R.id.fabBtn);
        fabSbtn = (FloatingActionButton) rootView.findViewById(R.id.fabbuttonSearch);

=======
        final RippleView rippleView = (RippleView) rootView.findViewById(R.id.addRipple);
        Button fabB = (Button) rootView.findViewById(R.id.fabbutton);
        fabS = (ImageButton) rootView.findViewById(R.id.fabbuttonSearch);
>>>>>>> 483dd003a65394db344d80c10a3f2794c8441e13

        et1 = (EditText) rootView.findViewById(R.id.etFirst);
        et2 = (EditText) rootView.findViewById(R.id.etSecond);
        et3 = (EditText) rootView.findViewById(R.id.etThird);

<<<<<<< HEAD
=======
        Outline mOutlineCircle;
        int shapeSize = getResources().getDimensionPixelSize(R.dimen.button_elevation);
        mOutlineCircle = new Outline();
        mOutlineCircle.setRoundRect(0, 0, shapeSize, shapeSize, shapeSize / 2);

        fabB.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                int size = getResources().getDimensionPixelSize(R.dimen.fab_size);
                outline.setOval(0, 0, size, size);
            }
        });
        rootView.findViewById(R.id.fabbutton).setClipToOutline(true);

        fabS.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                int size = getResources().getDimensionPixelSize(R.dimen.fab_size);
                outline.setOval(0, 0, size, size);
            }
        });
        rootView.findViewById(R.id.fabbuttonSearch).setClipToOutline(true);

        rippleView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                helpper++;
                show(helpper);
            }
        });

>>>>>>> 483dd003a65394db344d80c10a3f2794c8441e13

        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpper++;
                show(helpper);
            }
        });

        fabSbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String q1=et1.getText().toString();
                String q2=et2.getText().toString();
                String q3=et3.getText().toString();
                if(q1!="" || q2!="" || q3!="") {

                    ArrayList<Recept> aMovies = new ArrayList<Recept>();
                    adapterMovies = new ReceptiAdapter(getActivity(), aMovies);
                    lvMovies.setAdapter(adapterMovies);
                    fetchBoxOfficeMovies(q1 + " " + q2 + " " + q3);

                }
            }
        });

        setupMovieSelectedListener();
        return rootView;
    }

    private void show(int pom) {

        switch (pom){
            case 1:
                et1.setVisibility(View.VISIBLE);
                break;
            case 2:
                et2.setVisibility(View.VISIBLE);
                break;
            case 3:
                et3.setVisibility(View.VISIBLE);
                break;
            default:
                //do-nothing
        }
        fabSbtn.setVisibility(View.VISIBLE);
    }


    public void fetchBoxOfficeMovies(String link) {

        adapterMovies.clear();
        client = new Fork2FoodClient();
        client.getRecept(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject body) {
                JSONArray items = null;
                try {
                    // Get the movies json array
                    items = body.getJSONArray("recipes");
                    // Parse json array into array of model objects
                    ArrayList<Recept> movies = Recept.fromJson(items);
                    Log.d("sve", String.valueOf(movies));
                    // Load model objects into the adapter which displays them
                    adapterMovies.addAll(movies);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, link);

    }

    public void setupMovieSelectedListener() {

        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {

                Intent i = new Intent(getActivity(), DetailAct.class);
                // i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
                i.putExtra("POSITION_RID", adapterMovies.getItem(position).getrId());
                i.putExtra("POSITION", position);
                startActivity(i);
            }
        });
    }

}
