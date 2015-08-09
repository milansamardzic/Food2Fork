package com.milansamardzic.ohmymeal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.melnykov.fab.FloatingActionButton;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ms on 1/28/15.
 */
public class Vegan  extends Fragment {

    private ListView lvMovies;
    private ReceptiAdapter adapterMovies;
    private Fork2FoodClient client;
    public static final String MOVIE_DETAIL_KEY = "recipes";
    private int currentVisibleItemCount;
    private int currentFirstVisibleItem;
    public static int page = 1;
    FloatingActionButton fabMore;
    final String strtext = "vegan";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tranding, container, false);
        lvMovies = (ListView) rootView.findViewById(R.id.lvRecepti);



        //----- listView
        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity().getApplicationContext(), "Refreshing...", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fetchBoxOfficeMovies("&sort=t");
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }

        });

        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);

        ArrayList<Recept> aMovies = new ArrayList<Recept>();
        adapterMovies = new ReceptiAdapter(getActivity(), aMovies);

        StaggeredGridView gridview = (StaggeredGridView) rootView.findViewById(R.id.gridview);
        gridview.setAdapter(adapterMovies);
        fetchBoxOfficeMovies("&sort=t");
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent i = new Intent(getActivity(), DetailAct.class);
                // i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
                i.putExtra("POSITION_RID", adapterMovies.getItem(position).getrId());
                i.putExtra("POSITION", position);
                startActivity(i);
            }
        });

        return rootView;
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